package com.example.guillemllados.uiiot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;


public class grafiques extends Fragment {

    private List<PointValue> values;
    private LineChartView grafic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_grafiques, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        values = new ArrayList<PointValue>();


    }




}
