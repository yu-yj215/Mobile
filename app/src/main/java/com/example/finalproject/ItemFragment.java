package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Canvas;
import android.graphics.Color;
import com.google.android.material.snackbar.Snackbar;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;

public class ItemFragment extends Fragment {
    public static interface fragmentCallback {
        public void addfragment();
        public void removefragment(int position);
    }
    public fragmentCallback callback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 구현 했는지 확인
        if (context instanceof fragmentCallback) {
            callback = (fragmentCallback) context;
        }
    }
    private Itemstorage viewModel;

    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    ArrayList<Item> items = new ArrayList<Item>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_item_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        itemAdapter = new ItemAdapter();
        itemAdapter.setItems(items);
        recyclerView.setAdapter(itemAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch(direction) {
                    case ItemTouchHelper.LEFT:
                        // 삭제 할 아이템 담기
                        Item deleteItem = items.get(position);
                        if (callback != null)
                        {
                            callback.removefragment(position+1);;
                        }

                        items.remove(position);
                        itemAdapter.removeItem(position);
                        itemAdapter.notifyItemRemoved(position);

                        Snackbar.make(recyclerView, deleteItem.getTitle(), Snackbar.LENGTH_LONG)
                                .setAction("복구", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        items.add(position, deleteItem);
                                        itemAdapter.addItem(position, deleteItem);
                                        itemAdapter.notifyItemInserted(position);
                                    }
                                }).show();
                        break;
                }
            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder,
                        dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(Color.RED)
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeLeftLabel("삭제")
                        .setSwipeLeftLabelColor(Color.WHITE)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);

        Button addbutton = view.findViewById(R.id.addbutton);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                {
                    callback.addfragment();;
                }
                Item item = new Item();
                item.setTitle("충주시");
                item.setDescription("PM2.5 = 50");
                items.add(item);
                itemAdapter.addItem(item);
                itemAdapter.notifyDataSetChanged();

                recyclerView.startLayoutAnimation();
            }
        });

        // Set the adapter
        return view;
    }
}