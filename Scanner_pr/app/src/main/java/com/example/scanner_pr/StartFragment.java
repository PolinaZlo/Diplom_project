package com.example.scanner_pr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View inflatedView = inflater.inflate(R.layout.fragment_start, container, false);
           /*Button BackBtn = (Button) inflatedView.findViewById(android.R.id.home);
        BackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
               public void onClick(View v) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Реагирует!", Toast.LENGTH_SHORT);toast.show();
                }
            });*/
            return inflatedView;
        }

}
