package com.example.finalproject;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class DustFragment extends Fragment{
    public static interface fragmentCallback {
        public void showItemfragment();
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_dust, container, false);
        // 미세먼지 정보를 표시하는 뷰 등을 설정합니다.
        Button refresh = rootview.findViewById(R.id.refresh);
        Button newView = rootview.findViewById(R.id.newView);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                {
                    //callback.addfragment();
                    callback.showItemfragment();

                }
            }
        });



        return rootview;
    }

}
