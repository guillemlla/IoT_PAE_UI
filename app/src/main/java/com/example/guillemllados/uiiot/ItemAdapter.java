package com.example.guillemllados.uiiot;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by guillemllados on 5/10/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private HashMap<String,Item> items;
    public interface OnItemClickListener{
        void onItemClick(int whatClick,Item item); //0 for all 1 for sync
    }

    private OnItemClickListener onItemClickListener;


    public ItemAdapter(HashMap<String,Item> items,OnItemClickListener listener) {
        this.items = items;
        this.onItemClickListener = listener;

    }

    public void addItem(Item i){
        items.put(i.getId(),i);
    }

    public void getItem(int pos){
        items.get(pos);
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdispositiu, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item = items.get(items.keySet().toArray()[position]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(0,item);
            }
        });

        holder.itemRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(1,item);
            }
        });

        holder.noInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(2,item);
            }
        });

        holder.noInternet.setVisibility(View.INVISIBLE);
        holder.noInternet.setClickable(false);
        holder.itemName.setText(item.getNom());
        holder.itemId.setText("ID: "+item.getId());
        holder.itemAtrib1.setText(item.getLastAtrib1());
        holder.itemAtrib2.setText(item.getLastAtrib2());
        holder.itemAtrib3.setText(item.lastTimeUpdated());
        //holder.imageItem.setImageResource();

    }

    @Override public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageItem,itemRefresh,noInternet;
        private TextView itemName, itemAtrib1, itemAtrib2, itemAtrib3, itemId;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            imageItem = (ImageView) itemView.findViewById(R.id.itemImage);
            noInternet = (ImageView) itemView.findViewById(R.id.noInternet);
            itemRefresh = (ImageView) itemView.findViewById(R.id.sinchronize);
            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemAtrib1 = (TextView) itemView.findViewById(R.id.itemAtrib1);
            itemAtrib2 = (TextView) itemView.findViewById(R.id.itemAtrib2);
            itemAtrib3 = (TextView) itemView.findViewById(R.id.itemAtrib3);
            itemId = (TextView) itemView.findViewById(R.id.itemId);


        }


    }
}