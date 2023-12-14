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

    @Override // 아이템이 여러개이면 밑으로 드래그 시에 애니메이션 동작
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
    } // 아이템 추가
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

    // 추가한 지역명을 표시
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_view = itemView.findViewById(R.id.title_text);

        }
        public void setItem(Item item){
            title_view.setText(item.getTitle());
        }

    }

}

