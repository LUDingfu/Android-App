package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter  extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<Item> items;

    public Adapter(Context ctx, List<Item> items) {
        this.inflater = LayoutInflater.from(ctx);
        this.items = items;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemId,itemListId,itemName;
        public ViewHolder(@NonNull android.view.View itemView) {
            super(itemView);

            itemId = itemView.findViewById(R.id.text_id);
            itemListId = itemView.findViewById(R.id.text_listId);
            itemName = itemView.findViewById(R.id.text_name);
        }
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout, parent, false);
        return new ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemId.setText(String.valueOf(item.getId()));
        holder.itemListId.setText(String.valueOf(item.getListId()));
        holder.itemName.setText(item.getName());
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}
