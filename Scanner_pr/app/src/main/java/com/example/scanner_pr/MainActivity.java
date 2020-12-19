package com.example.scanner_pr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.Result;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ZXingScannerView.ResultHandler {
    private ZXingScannerView zXingScannerView; //переменная для сканера
    public String res; //результат сканирования штрих-кода
    private DrawerLayout drawer;
    boolean scan_run=false; //камера запущена или нет
    Toolbar toolbar;
    Vibrator vibrator; //вибрация при сканировании
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";

    private static final String NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final String METHODNAME = "Find";
    private static final String SOAPACTION = "http://schemas.xmlsoap.org/soap/envelope/Find";
    String result = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StartFrag("Main");
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // кнопка "назад" на фрагменте с результатом
        if (item.getItemId()==android.R.id.home){
            StartFrag("Main");
       }
        return super.onOptionsItemSelected(item);
    }

    public void Scan(View view){
        //сканирование
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            scan_run=true;
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
        }
        else
        {scan_run=false;
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.error_permission), Toast.LENGTH_SHORT);toast.show();}
    }

    public void Search(View view){
        //поиск по введенному в edittext номеру
        EditText barcode = (EditText)findViewById(R.id.barcode);
        if(barcode.getText().toString().trim().length()>0) {
            res=barcode.getText().toString();
            Connect();//Подключение к веб-сервису
            StartFrag("Result");
        }
        else
        {
           Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.error_editext), Toast.LENGTH_SHORT);toast.show();
        }
    }

    @Override
    public void handleResult(Result result) {
        //ловим результат сканирования
        zXingScannerView.getId();
        res=result.getText();
        vibrator.vibrate(300);

        Connect();//Подключение к веб-сервису

        StartFrag("Result");
    }

    public String getRes(){
        return res;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (scan_run){
        zXingScannerView.stopCamera();}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //переход по navigation drawer
        switch (item.getItemId()){
            case R.id.nav_scan:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new StartFragment()).commit();
                break;
            case R.id.nav_result:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Two()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{ super.onBackPressed();}
    }

    private void StartFrag(String Name_frag){
        //метод для загрузки одного из фрагментов
        switch (Name_frag){
            case "Main":
                setContentView(R.layout.activity_main);
                drawer=findViewById(R.id.drawer_layout);//наша мессия, заставившая _реагировать_ toolbar, но это не меняет факта что он перекрыт
                toolbar=findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                NavigationView navigationView=findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);
                ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StartFragment()).commit();
                break;
            case "Result":
                setContentView(R.layout.spec_toolbar);//специальный toolbar чтобы не выходить на костыли navigation bar
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ResultFragment()).commit();
                toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                break;
            case "Error":
                setContentView(R.layout.spec_toolbar);//специальный toolbar чтобы не выходить на костыли navigation bar
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ErrorFragment()).commit();
                toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                break;
        }

    }



    public void Connect(){
        //Подключение к веб-сервису
        AT at = new AT();
        at.execute();
    }





    public void Exit(View view){
        //"Выход"(пока что) из учетной записи
        Intent intent = new Intent(MainActivity.this, Access.class);
        startActivity(intent);
    }


    void saveText(String text) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, text);
        ed.commit();
    }

    String loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        return savedText;
    }

    private class AT extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();//выводит результат когда все сработало
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                SoapObject soapObject = new SoapObject(NAMESPACE, METHODNAME);
                PropertyInfo intA = new PropertyInfo();
                intA.setName("BARCODE");
                intA.setValue(res);
                intA.setType(int.class);
                soapObject.addProperty(intA);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(soapObject);
                HttpTransportSE httpTransportSE = new HttpTransportSE(loadText());//Загружаем сохраненную в настройках URL
                try {
                    httpTransportSE.call(SOAPACTION, envelope);
                    SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
                    result = soapPrimitive.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                return null;
            } catch (Exception e){
                Toast.makeText(MainActivity.this, getString(R.string.error_connect), Toast.LENGTH_SHORT).show();
            }
                return null;
            }
        }
    }
