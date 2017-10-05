package com.example.guillemllados.uiiot;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.principal);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        ArrayList<Item> items = new ArrayList<>();
        items = inicialitzarItems(items);

        ItemAdapter itemAdapter = new ItemAdapter(items, new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                Toast.makeText(getApplicationContext(), "Item +"+item.getNom()+"CLicked", Toast.LENGTH_LONG).show();

            }
        });
        recyclerView.setAdapter(itemAdapter);

        add = (FloatingActionButton) findViewById(R.id.addItem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });

    }

    public void createDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogadd);
        dialog.setTitle("Afegeix Dispositiu");

        // set the custom dialog components - text, image and button
        Button so = (Button) dialog.findViewById(R.id.bttDialogSo);
        Button imatge = (Button) dialog.findViewById(R.id.bttDiaglogImatge);
        final RelativeLayout dialogOptions = (RelativeLayout) dialog.findViewById(R.id.rlDialogOptions);
        final ProgressBar progress = (ProgressBar) dialog.findViewById(R.id.pbProgress);
        progress.setVisibility(View.INVISIBLE);

        so.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //captar so
                dialogOptions.setVisibility(View.INVISIBLE);
                progress.setVisibility(View.VISIBLE);
                progress.setMax(1000);
                progress.setProgress(0);

            }
        });

        imatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //captar imatge
                dialogOptions.setVisibility(View.INVISIBLE);
                progress.setVisibility(View.VISIBLE);
                progress.setMax(1000);
                progress.setProgress(0);
            }
        });

        dialog.show();

    }


    public ArrayList<Item> inicialitzarItems(ArrayList<Item> items){

        Item i = new Item("Item0", "Humitat = 10%", "Temperatura = 30ºC", "Ultima actualització: 3h", 1203432224,"null");
        items.add(i);
        i = new Item("Item1", "Humitat = 10%", "Temperatura = 30ºC", "Ultima actualització: 3h", 124654635,"null");
        items.add(i);
        i = new Item("Item2", "Humitat = 10%", "Temperatura = 123ºC", "Ultima actualització: 3h", 234354632,"null");
        items.add(i);
        i = new Item("Item3", "Humitat = 10%", "Temperatura = 23234ºC", "Ultima actualització: 3h", 23423423,"null");
        items.add(i);
        i = new Item("Item4", "Humitat = 10%", "Temperatura = 54ºC", "Ultima actualització: 3h", 23423,"null");
        items.add(i);
        i = new Item("Item5", "Humitat = 10%", "Temperatura = 333ºC", "Ultima actualització: 3h", 234334,"null");
        items.add(i);
        i = new Item("Item6", "Humitat = 10%", "Temperatura = 20ºC", "Ultima actualització: 3h", 4545332,"null");
        items.add(i);
        i = new Item("Item7", "Humitat = 10%", "Temperatura = 12ºC", "Ultima actualització: 3h", 99999999,"null");
        items.add(i);
        i = new Item("Item8", "Humitat = 10%", "Temperatura = 3240ºC", "Ultima actualització: 3h", 666666,"null");
        items.add(i);
        i = new Item("Item9", "Humitat = 10%", "Temperatura = 2130ºC", "Ultima actualització: 3h", 43563,"null");
        items.add(i);
        i = new Item("Item10", "Humitat = 10%", "Temperatura = 3420ºC", "Ultima actualització: 3h", 44444,"null");
        items.add(i);
        i = new Item("Item11", "Humitat = 10%", "Temperatura = 210ºC", "Ultima actualització: 3h", 345432,"null");
        items.add(i);
        return items;



    }
}
