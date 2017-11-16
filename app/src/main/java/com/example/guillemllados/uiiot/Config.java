package com.example.guillemllados.uiiot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Config extends Fragment {



    private EditText etNom;
    private Button bttGuardar,bttImatge,bttLoc;
    private View viewFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_config, container, false);
        etNom = (EditText) v.findViewById(R.id.eTNom);
        bttGuardar = (Button) v.findViewById(R.id.bttSave);
        bttImatge = (Button) v.findViewById(R.id.bttImg);
        bttLoc = (Button) v.findViewById(R.id.bttLoc);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        etNom.setText("Hola!");


    }


}
