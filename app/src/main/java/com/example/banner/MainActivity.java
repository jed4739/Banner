package com.example.banner;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.banner.databinding.Main;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends FragmentActivity {

    private Main binding;
    private int page = 3;
    private int tab_pos;
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
    // 다음 아이템으로 넘김
    private void autoSlider() {
        sliderRunnable = () -> binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
    }
    // 뷰페이저와 어답터 연결
    private void setViewPagerAdapter(){
        viewPager = binding.viewPager;
        myAdapter = new MyAdapter(this, page);
        viewPager.setAdapter(myAdapter);
    }

    // 뷰페이저와 인디케이터 연결
    private void setIndicator() {
        indicator = binding.indicator;
        indicator.setViewPager(viewPager);
        indicator.createIndicators(page, 0);
    }

    // 뷰페이저와 탭레이아웃 연결 및 설정
    private void setTabLayout() {
        tabLayout = binding.tab;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab_pos = tab.getPosition();
                tabBtn(tab_pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab_pos = tab.getPosition();
                tabBtn(tab_pos);
            }
        });
    }
    /*
    * setTabLayout 에서 넘어옴.
    * onTabSelected, onTabReselected 에게서 매개변수 인자를 받음.
    * tab_pos 현재 배너 번호 객체
    * 다른탭을 누를 시 onTabLayout 메소드가 실행
    * 같은탭을 누를 시 onTabReselected 메소드가 실행
    * */
    private void tabBtn(int tab_pos) {
        switch (tab_pos) {
            case 0:
                if (viewPager.getCurrentItem() % 3 == 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                } else if (viewPager.getCurrentItem() % 3 == 2) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 2);
                }
                Log.i("ddddddd", "첫번째");
                break;
            case 1:
                if (viewPager.getCurrentItem() % 3 == 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else if (viewPager.getCurrentItem() % 3 == 2) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                }
                Log.i("ddddddd", "두번째");
                break;
            case 2:
                if (viewPager.getCurrentItem() % 3 == 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else if (viewPager.getCurrentItem() % 3 == 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 2);
                }
                Log.i("ddddddd", "세번째");
                break;
            default:
        }
    }

    /*
    * setOrientation -> 가로 스크롤
    * setCurrentItem -> 시작 아이템 위치
    * setOffscreenPageLimit -> 페이지 제한
    *
    * onPageScrolled -> 스와이프 되서 다른 페이지로 넘어갈시 값을 바꿈
    * onPageSelected -> 스와이프가 아닌 선택으로 넘겼을때 변함
    * */
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
                    // 3초마다 자동 넘기기
                    sliderHandler.removeCallbacks(sliderRunnable);
                    sliderHandler.postDelayed(sliderRunnable, 3000);
                }
            }

            // 스크롤이 무한대일 경우 3 -> 1 로 인디케이터 설정
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicator.animatePageSelected(position % page);
            }
        });
    }
}