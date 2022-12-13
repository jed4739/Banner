package com.example.banner;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.banner.databinding.Main;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends FragmentActivity {
    private Main binding;
    private int page = 3;
    private ViewPager2 viewPager;
    private MyAdapter myAdapter;
    private CircleIndicator3 indicator;
    private final Handler sliderHandler = new Handler();
    Runnable sliderRunnable;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(binding.getRoot());

        autoSlider();
        setViewPagerAdapter();
        setIndicator();
        setTabLayout();
        setViewPager();
    }

    private void autoSlider() {
        sliderRunnable = () -> binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);

    }

    private void setViewPagerAdapter(){
        viewPager = binding.viewPager;
        myAdapter = new MyAdapter(this, page);
        viewPager.setAdapter(myAdapter);
    }

    private void setIndicator() {
        indicator = binding.indicator;
        indicator.setViewPager(viewPager);
        indicator.createIndicators(page, 0);
    }

    private void setTabLayout() {
        tabLayout = binding.tab;
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position%3) {
                case 0:
                    tab.setText("Tab" + 1);
                    break;
                case 1:
                    tab.setText("Tab" + 2);
                    break;
                case 2:
                    tab.setText("Tab" + 3);
                    break;
            }
        }).attach();
    }

    private void setViewPager() {
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    viewPager.setCurrentItem(position);
                    // 자동 넘기기
                    sliderHandler.removeCallbacks(sliderRunnable);
                    sliderHandler.postDelayed(sliderRunnable, 3000);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicator.animatePageSelected(position%page);
            }
        });
    }
}