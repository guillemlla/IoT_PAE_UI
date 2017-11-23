package com.example.guillemllados.uiiot;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Principal extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton add;
    public static HashMap<String,Item> items;
    private ItemAdapter itemAdapter;
    public static Item itemclicked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.principal);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        items = new HashMap<>();
        items = inicialitzarItems(items);


        itemAdapter = new ItemAdapter(items, new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int whatClick, final Item item) {
                Toast.makeText(getApplicationContext(), "Item +"+item.getNom()+"CLicked", Toast.LENGTH_SHORT).show();
                if(whatClick == 0){
                    itemclicked = item;
                    iniciaDispositiu();
                }else if(whatClick==1){

                    sincronizeDialog(false);




                }else if(whatClick==2){
                    //retry syncrhonize with internet
                    String[]input = {"1"};
                    //AsyncGetAtributesSingleDevice asyncGetAtributes = new AsyncGetAtributesSingleDevice();
                    //asyncGetAtributes.execute(input);
                    //GET DATA FROM INTERNET
                }

            }
        });
        recyclerView.setAdapter(itemAdapter);

        add = (FloatingActionButton) findViewById(R.id.addItem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sincronizeDialog(true);
            }
        });



    }

    public void iniciaDispositiu(){
        Intent i = new Intent(this,DispositiuPage.class);
        startActivity(i);
    }

    public void sincronizeDialog(final Boolean isNew){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogadd);
        dialog.setTitle("Afegeix DispositiuPage");

        // set the custom dialog components - text, image and button
        Button so = (Button) dialog.findViewById(R.id.bttDialogSo);
        Button imatge = (Button) dialog.findViewById(R.id.bttDiaglogImatge);
        final RelativeLayout dialogOptions = (RelativeLayout) dialog.findViewById(R.id.rlDialogOptions);

        so.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Item i = new Item("Item0", "5","null",Double.toString(41.4013690), Double.toString(2.196325));
                CalendarE c = new CalendarE();
                c.setTime(2,3,4);
                Atributs atributs = new Atributs(c,"40", "31");
                i.addAtribute(atributs);
                c = new CalendarE();
                c.setTime(14,5,50);
                atributs = new Atributs(c,"20", "40");

                i.addAtribute(atributs);
                c = new CalendarE();
                c.setTime(5,2,30);
                atributs = new Atributs(c,"70", "20");

                i.addAtribute(atributs);
                c = new CalendarE();
                c.setTime(6,30,10);
                atributs = new Atributs(c,"50", "10");

                i.addAtribute(atributs);

                AsyncUpdateDeviceDB asyncUpdateDeviceDB = new AsyncUpdateDeviceDB();
                asyncUpdateDeviceDB.sendAtrributes(i.getId(),i.getAtributs());



            }
        });

        imatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //captar imatge
                Flash flash = new Flash(getApplicationContext());
                Boolean[] array = {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false};
                flash.sendData(array);
                if(isNew){
                    Item i = new Item("Item"+(items.size()), "12222","null",Double.toString(41.4013690), Double.toString(2.196325));
                    CalendarE c = new CalendarE();
                    Atributs atributs = new Atributs(c,"Humitat = 10%", "Temperatura = 30ºC");
                    i.addAtribute(atributs);
                    c = new CalendarE();
                    atributs = new Atributs(c,"Humitat = 10%", "Temperatura = 30ºC");
                    i.addAtribute(atributs);
                    c = new CalendarE();
                    atributs = new Atributs(c,"Humitat = 10%", "Temperatura = 30ºC");
                    i.addAtribute(atributs);
                    c = new CalendarE();
                    atributs = new Atributs(c,"Humitat = 10%", "Temperatura = 30ºC");
                    i.addAtribute(atributs);
                    items.put(i.getId(),i);
                    itemAdapter.notifyItemInserted(items.size()-1);
                }
                dialog.cancel();



            }
        });

        dialog.show();

    }


    public HashMap<String,Item> inicialitzarItems(HashMap<String,Item> items){

        Item i = new Item("Item0", "0","null",Double.toString(41.423658), Double.toString(2.145692));
        CalendarE c = new CalendarE();
        Atributs atributs = new Atributs(c,"10", "30");
        i.addAtribute(atributs);
        c = new CalendarE();
         atributs = new Atributs(c,"10", "30");

        i.addAtribute(atributs);
        c = new CalendarE();
         atributs = new Atributs(c,"10", "30");

        i.addAtribute(atributs);
        c = new CalendarE();
         atributs = new Atributs(c,"10", "30");

        i.addAtribute(atributs);
        items.put(i.getId(),i);

        i = new Item("Item1", "1","null",Double.toString(41.4013690), Double.toString(2.196325));
        c = new CalendarE();
         atributs = new Atributs(c,"10", "30");

        i.addAtribute(atributs);
        c = new CalendarE();
         atributs = new Atributs(c,"10", "30");

        i.addAtribute(atributs);
        c = new CalendarE();
         atributs = new Atributs(c,"10", "30");

        i.addAtribute(atributs);
        c = new CalendarE();
         atributs = new Atributs(c,"10", "30");

        i.addAtribute(atributs);
        items.put(i.getId(),i);


        return items;



    }
}
