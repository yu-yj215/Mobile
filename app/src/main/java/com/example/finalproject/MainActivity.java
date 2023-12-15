package com.example.finalproject;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;



public class MainActivity extends AppCompatActivity implements DustFragment.fragmentCallback,ItemFragment.fragmentCallback {
    String baseUrl = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"; // API
    String serviceKey = "비공개"; // 공개 키
    int numOfRows = 1;
    // API 요청용
    int pageNo = 1;
    // API 요청용

    // 뷰 페이저 연결
    private ViewPager viewPager;
    private DustPagerAdapter pagerAdapter;
    private ItemFragment itemFragment = new ItemFragment();
    static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new DustPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new DustFragment()); // 처음 로딩시에 프래그먼트생성
        viewPager.setAdapter(pagerAdapter);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }
    @Override
    public void makeRequest(String url,PM pm) { // 프래그먼트에서 실행시킬 요청
        String url2 = urlset(url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url2,
                response -> {
                    Log.d("MainActivity", response);
                    processResponse(response, pm);
                },
                error -> {
                    Log.d("MainActivity", error.getMessage());
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // 여기에 필요한 파라미터를 추가
                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }


    public void processResponse(String response, PM pm) {
        pm.parseJson(response); // 받은 json을 파싱해서 분배하는 메소드, 실제 객체내부를 변화시킴
        Log.d("MainActivity", pm.coValue);
    }


    public String urlset(String sidoname) { // url 완성 함수
        String url = baseUrl + "?serviceKey=" + serviceKey
                + "&returnType=json"
                + "&numOfRows=" + numOfRows
                + "&pageNo=" + pageNo
                + "&sidoName=" + sidoname
                + "&ver=1.0";
        return url;
    }

    private static class DustPagerAdapter extends FragmentPagerAdapter { // 페이저 어댑터

        private final List<Fragment> fragments = new ArrayList<>();
        public DustPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public void addFragment(Fragment fragment) {
            fragments.add(fragment);
        } // 프래그먼트 추가
        public void removeFragment(int position) { fragments.remove(position); } // 프래그먼트 삭제
        @Override
        public Fragment getItem(int position) { return fragments.get(position); }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public void addfragment(String sido) { // 아이템프래그먼트에서 사용
        pagerAdapter.addFragment(new DustFragment(sido));
        pagerAdapter.notifyDataSetChanged();
    }
    @Override
    public void removefragment(int position) { // 아이템프래그먼트에서 사
        pagerAdapter.removeFragment(position);
        pagerAdapter.notifyDataSetChanged();
    }
    @Override
    public void showItemfragment() { // 더스트 프래그먼트에서 버튼 클릭시에 아이템프래그먼트 띄기
        // Replace the current Fragment with a new on용
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentcontainer, itemFragment)
                .addToBackStack(null)
                .commit();
        findViewById(R.id.viewPager).setVisibility(View.INVISIBLE);


    }

    @Override
    public void onBackPressed() {
        // Check if there are Fragments in the back stack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // If yes, pop the back stack to remove the top Fragment
            getSupportFragmentManager().popBackStack();
            findViewById(R.id.viewPager).setVisibility(View.VISIBLE);
        } else {
            // If no Fragments in the back stack, proceed with the default back button behavior
            super.onBackPressed();
        }
    }
}
