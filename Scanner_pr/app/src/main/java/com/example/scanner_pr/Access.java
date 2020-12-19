package com.example.scanner_pr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class Access extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 100;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);
        //проверка на разрешение камеры
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(Access.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Access.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }
        }
    }
    public void Access(View view){
        EditText login=(EditText)findViewById(R.id.login);
        EditText password=(EditText)findViewById(R.id.password);
        String log=login.getText().toString().trim();
        String pass=password.getText().toString().trim();
        if(log.equals("AdminUser")&&pass.equals("Maxidom")){
            Intent intent = new Intent(Access.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Не верное значение логина или пароля", Toast.LENGTH_SHORT);toast.show();
       }
    }
}
