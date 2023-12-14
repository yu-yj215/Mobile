package com.example.finalproject;


import android.content.Context;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;



public class DustFragment extends Fragment {
    private String sidoname; //
    DustFragment() {
        this.sidoname = "서울";
    } // 메인액티비 로딩시에 생성되는 프래그먼트를 위한 디폴트 생성자
    DustFragment(String sidoname) {
        this.sidoname = sidoname;
    } // 아이템프래그먼트에서 실행 시
    public String getname() {
        return sidoname;
    }
    public static interface fragmentCallback { // 메인액티비티와 연결
        public void showItemfragment();
        public void makeRequest(String url, PM pm);
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
    PM pm = new PM(); // 미세먼지 정보를 담을 인스턴스가 필요, 그래야 다른 페이지 갔다와도 유지


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_dust, container, false);

        // 미세먼지 정보를 표시하는 뷰 등을 설정
        SwipeRefreshLayout swipeRefreshLayout = rootview.findViewById(R.id.swiperefresh);
        Button refresh = rootview.findViewById(R.id.refresh);
        Button newView = rootview.findViewById(R.id.newView);
        TextView pm10 = rootview.findViewById(R.id.PM10);
        TextView pm2_5 = rootview.findViewById(R.id.PM2_5);
        TextView NO2 = rootview.findViewById(R.id.NO2);
        TextView O3 = rootview.findViewById(R.id.O3);
        TextView CO = rootview.findViewById(R.id.CO);
        TextView SO2 = rootview.findViewById(R.id.SO2);
        TextView datatime = rootview.findViewById(R.id.datatime);
        TextView sidoname = rootview.findViewById(R.id.sidoname);


        // 프래그먼트 생성 시 동작, 하지만 어째선지 NULL이 처음에 들어옴
        if (callback != null)
        {
            callback.makeRequest(getname(), pm);
            System.out.println(pm);
            pm10.setText("미세먼지 : " + pm.pm10Value + " μg/m³");
            pm2_5.setText("초미세먼지 : " + pm.pm25Value + " μg/m³");
            NO2.setText("이산화질소 : " + pm.no2Value + " ppm");
            O3.setText("오존 : " + pm.o3Value + " ppm");
            CO.setText("일산화탄소 : " + pm.coValue + " ppm");
            SO2.setText("아황산가스 : " + pm.so2Value + " ppm");
            datatime.setText(pm.dataTime);
            sidoname.setText(getname());
        }

        // 밑으로 당겨서 새로고침시에 정보 세팅
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (callback != null)
                {
                    callback.makeRequest(getname(), pm);
                    pm10.setText("미세먼지 : " + pm.pm10Value + " μg/m³");
                    pm2_5.setText("초미세먼지 : " + pm.pm25Value + " μg/m³");
                    NO2.setText("이산화질소 : " + pm.no2Value + " ppm");
                    O3.setText("오존 : " + pm.o3Value + " ppm");
                    CO.setText("일산화탄소 : " + pm.coValue + " ppm");
                    SO2.setText("아황산가스 : " + pm.so2Value + " ppm");
                    datatime.setText(pm.dataTime);
                    sidoname.setText(getname());
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        // 새로고침 버튼
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                {
                    callback.makeRequest(getname(), pm);
                    pm10.setText("미세먼지 : " + pm.pm10Value + " μg/m³");
                    pm2_5.setText("초미세먼지 : " + pm.pm25Value + " μg/m³");
                    NO2.setText("이산화질소 : " + pm.no2Value + " ppm");
                    O3.setText("오존 : " + pm.o3Value + " ppm");
                    CO.setText("일산화탄소 : " + pm.coValue + " ppm");
                    SO2.setText("아황산가스 : " + pm.so2Value + " ppm");
                    datatime.setText(pm.dataTime);
                    sidoname.setText(getname());
                }
            }
        });

        // 버튼 클릭 시 아이템 프래그먼트 대체
        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                {
                    callback.showItemfragment();
                }
            }
        });



        return rootview;
    }


}
