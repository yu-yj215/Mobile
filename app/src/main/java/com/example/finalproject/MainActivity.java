package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.ViewModel;


public class MainActivity extends AppCompatActivity implements DustFragment.fragmentCallback,ItemFragment.fragmentCallback {


    private ViewPager viewPager;
    private DustPagerAdapter pagerAdapter;
    private ItemFragment itemFragment = new ItemFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new DustPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new DustFragment());
        viewPager.setAdapter(pagerAdapter);
    }

    public void load() {

    }
    private static class DustPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        public DustPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }
        public void removeFragment(int position) { fragments.remove(position); }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public void addfragment() {
        pagerAdapter.addFragment(new DustFragment());
        pagerAdapter.notifyDataSetChanged();
    }
    @Override
    public void removefragment(int position) {
        pagerAdapter.removeFragment(position);
        pagerAdapter.notifyDataSetChanged();
    }
    @Override
    public void showItemfragment() {
        // Replace the current Fragment with a new one
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