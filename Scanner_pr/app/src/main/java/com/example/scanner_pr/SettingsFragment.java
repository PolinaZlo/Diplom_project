package com.example.scanner_pr;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_settings, container, false);

        final TextView show = (TextView) inflatedView.findViewById(R.id.sav);
        Button button = (Button) inflatedView.findViewById(R.id.Btn_connect);
        final EditText barcode = (EditText) inflatedView.findViewById(R.id.barcode);
        final MainActivity ma = (MainActivity)this.getActivity();
        show.setText(getString(R.string.cur_connect)+ma.loadText());
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ma.saveText(barcode.getText().toString());
                show.setText(getString(R.string.cur_connect)+ma.loadText());
                Toast toast = Toast.makeText(getContext(), getString(R.string.save), Toast.LENGTH_SHORT);toast.show();
            }
        });
return inflatedView;
    }
}
