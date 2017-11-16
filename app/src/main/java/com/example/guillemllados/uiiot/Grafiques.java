package com.example.guillemllados.uiiot;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.*;
import com.github.mikephil.charting.data.*;
import java.util.ArrayList;
import java.util.List;




public class Grafiques extends Fragment {

    CombinedChart combinedChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_grafiques, container, false);

        combinedChart = (CombinedChart) v.findViewById(R.id.chart);

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();

        ArrayList<String> xAxisValues = getXAxisValues();
        CombinedData data = new CombinedData();

        data.setData(barData());
        data.setData(lineData());
        combinedChart.setData(data);

        //estil
        combinedChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        combinedChart.getXAxis().setDrawGridLines(false);
        combinedChart.getAxisLeft().setDrawGridLines(false);
        combinedChart.getAxisRight().setDrawGridLines(false);

        combinedChart.getAxisRight().setAxisMinValue((float)-15);
        combinedChart.getAxisLeft().setAxisMinValue((float)0);
        combinedChart.getAxisLeft().setAxisMaxValue((float)100);

        combinedChart.getXAxis().setAxisLineWidth((float)2);
        combinedChart.getAxisLeft().setAxisLineWidth((float)2);
        combinedChart.getAxisRight().setAxisLineWidth((float)2);

        //combinedChart.setDescription("22/03/2018");
        combinedChart.setDrawGridBackground(true);
        combinedChart.getAxisRight().setTextColor(Color.rgb(0, 155, 0));

    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("00:00");labels.add("01:00");labels.add("02:00");labels.add("03:00");
        labels.add("04:00");labels.add("05:00");labels.add("06:00");labels.add("07:00");
        labels.add("08:00");labels.add("09:00");labels.add("10:00");labels.add("11:00");
        labels.add("12:00");labels.add("13:00");labels.add("14:00");labels.add("15:00");
        labels.add("16:00");labels.add("17:00");labels.add("18:00");labels.add("19:00");
        labels.add("20:00");labels.add("21:00");labels.add("22:00");labels.add("23:00");
        return labels;
    }
    public LineData lineData() {
        ArrayList<Entry> lineEntries = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            lineEntries.add(new Entry(-10,0+3*i));
            lineEntries.add(new Entry(12, 1+3*i));
            lineEntries.add(new Entry(28, 2+3*i));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Atrib1");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineDataSet.setColor(Color.rgb(0, 155, 0));
        lineDataSet.setCircleColor(Color.rgb(0, 155, 0));
        lineDataSet.setCircleRadius(2);
        lineDataSet.setCircleColorHole(Color.rgb(155, 155, 155));
        LineData lineData = new LineData();
        //LineData lineData = new LineData(getXAxisValues(), lineDataSet);
        lineData.setDrawValues(false);
        return lineData;
    }
    public BarData barData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            barEntries.add(new BarEntry(20, 0+3*i));
            barEntries.add(new BarEntry(50, 1+3*i));
            barEntries.add(new BarEntry(80, 2+3*i));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Atrib2");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        BarData barData = new BarData();
        //BarData barData = new BarData(getXAxisValues(), barDataSet);
        barData.setDrawValues(false);
        return barData;
    }




    public void actualitzaGrafiques(List<Atributs> list){

    }




}
