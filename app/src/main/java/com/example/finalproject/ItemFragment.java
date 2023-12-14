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
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import java.util.ArrayList;
import androidx.appcompat.widget.SearchView;



public class ItemFragment extends Fragment {
    public static interface fragmentCallback { // 메인액티비티와 연결
        public void addfragment(String sido);
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

    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    ArrayList<Item> items = new ArrayList<Item>(); // 생성된 프래그먼트들을 아이템으로 보게함
    String sido; // 입력값

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_item_list, container, false);
        SearchView searchView = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recycler_view);
        itemAdapter = new ItemAdapter();
        itemAdapter.setItems(items); // 저장된 정보 다시 인플레이트할때 가져오기
        recyclerView.setAdapter(itemAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // 검색시 지역 이름이 어사인
            @Override
            public boolean onQueryTextSubmit(String query) {
                sido = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        // 아이템을 밀어서 삭제하는 기능을 담기 위한 인스턴스 생성
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override // 스와이프 시 삭제 동작
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch(direction) {
                    case ItemTouchHelper.LEFT:
                        // 삭제 할 아이템 담기
                        if (callback != null)
                        {
                            callback.removefragment(position+1);;
                        }

                        items.remove(position);
                        itemAdapter.removeItem(position);
                        itemAdapter.notifyItemRemoved(position);

                }
            }
            @Override // 삭제 효과
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

        addbutton.setOnClickListener(new View.OnClickListener() { // 미세먼지 탭 추가(프래그먼트)
            public void onClick(View v) {

                if (callback != null)
                {
                    callback.addfragment(sido);;
                }
                Item item = new Item();

                item.setTitle(sido);
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