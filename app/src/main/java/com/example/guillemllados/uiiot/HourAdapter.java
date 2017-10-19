package com.example.guillemllados.uiiot;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by guillemllados on 19/10/17.
 */

public class HourAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private List<HourItem> items;
    public interface OnItemClickListener{
        void onItemClick(Item item);
    }

    private ItemAdapter.OnItemClickListener onItemClickListener;


    public HourAdapter(List<HourItem> items,ItemAdapter.OnItemClickListener listener) {
        this.items = items;
        this.onItemClickListener = listener;

    }

    public void addItem(HourItem i){
        items.add(i);
    }

    public void getItem(int pos){
        items.get(pos);
    }

    @Override public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdispositiu, parent, false);
        return new ItemAdapter.ViewHolder(v);
    }

    @Override public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        final HourItem item = items.get(position);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(item);
            }
        });

        /*holder.itemName.setText(item.getNom());
        holder.itemId.setText("ID: "+item.getId());
        holder.itemAtrib1.setText(item.getAtrib1());
        holder.itemAtrib2.setText(item.getAtrib2());
        holder.itemAtrib3.setText(item.getAtrib3());*/
        //holder.imageItem.setImageResource();

    }

    @Override public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageItem,itemClose;
        private TextView itemName, itemAtrib1, itemAtrib2, itemAtrib3, itemId;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            imageItem = (ImageView) itemView.findViewById(R.id.itemImage);
            itemClose = (ImageView) itemView.findViewById(R.id.itemCLose);
            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemAtrib1 = (TextView) itemView.findViewById(R.id.itemAtrib1);
            itemAtrib2 = (TextView) itemView.findViewById(R.id.itemAtrib2);
            itemAtrib3 = (TextView) itemView.findViewById(R.id.itemAtrib3);
            itemId = (TextView) itemView.findViewById(R.id.itemId);


        }


    }
}