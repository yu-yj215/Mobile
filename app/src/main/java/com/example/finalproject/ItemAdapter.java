package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.content.Context;
import android.view.animation.Animation;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    ArrayList<Item> items = new ArrayList<Item>();
    int lastPosition = -1;
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.fragment_item, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if(viewHolder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_in_row);
            ((ViewHolder) viewHolder).itemView.startAnimation(animation);
            Item item = items.get(position);
            viewHolder.setItem(item);
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }
    public void addItem(int position, Item item){
        items.add(position, item);
    }
    public void setItems(ArrayList<Item> itemList) {
        items.clear();
        items.addAll(itemList);
        notifyDataSetChanged();
    }
    public void removeAllItem() {
        items.clear();
    }
    public void removeItem(int position) {
        items.remove(position);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_view;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_view = itemView.findViewById(R.id.title_text);
            description = itemView.findViewById(R.id.desc_text);
        }
        public void setItem(Item item){
            title_view.setText(item.getTitle());
            description.setText(item.getDescription());
        }

    }

}

