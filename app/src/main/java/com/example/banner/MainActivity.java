package com.example.banner;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

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
//    private TabLayout tabLayout;
    int tabPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(binding.getRoot());

        setViewPagerAdapter();
        setIndicator();
//        setTabLayout();
        setViewPager();
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

//    private void setTabLayout() {
//        tabLayout = binding.tab;
//         new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//
//            }
//        }).attach();
//    }

    private void setViewPager() {
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setCurrentItem(5001);
        viewPager.setOffscreenPageLimit(3);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    viewPager.setCurrentItem(position);
                    tabPosition = position%page;
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