package com.example.banner

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.banner.databinding.Main

class MainActivity : FragmentActivity() {
    private var binding: Main? = null
    private val page = 3
    private var tabpos = 0
    private var viewPager: ViewPager2? = null
    private var indicator: CircleIndicator3? = null
    private val sliderHandler = Handler(Looper.getMainLooper())
    private var sliderRunnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        autoSlider()
        setViewPagerAdapter()
        setIndicator()
        setTabLayout()
        setViewPager()
    }

    // 다음 아이템으로 넘김
    private fun autoSlider() {
        val viewPager = binding!!.viewPager
        sliderRunnable = Runnable { viewPager.currentItem = viewPager.currentItem + 1 }
    }

    // 뷰페이저와 어답터클래스 연결
    private fun setViewPagerAdapter() {
        viewPager = binding!!.viewPager
        val myAdapter = MyAdapter(this, page)
        viewPager!!.adapter = myAdapter
    }

    // 뷰페이저와 인디케이터 연결
    private fun setIndicator() {
        indicator = binding!!.indicator
        indicator!!.setViewPager(viewPager)
        indicator!!.createIndicators(page, 0)
    }

    // 뷰페이저와 탭레이아웃 연결 및 설정
    private fun setTabLayout() {
        val tabLayout = binding!!.tab
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            /*
            * 현재 선택중이지 않은 다른 탭을 선택시 실행되는 메소드
            * 다시 선택한 탭의 위치를 tabBtn 메소드로 넘김.
            * */
            override fun onTabSelected(tab: TabLayout.Tab) {
                tabpos = tab.position
                tabBtn(tabpos)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            /*
            * 현재 선택한 탭을 다시 선택 시 실행되는 메소드
            * 다시 선택한 탭의 위치를 tabBtn 메소드로 넘김.
            * */
            override fun onTabReselected(tab: TabLayout.Tab) {
                tabpos = tab.position
                tabBtn(tabpos)
            }
        })
    }

    /*
    * setTabLayout 에서 넘어옴.
    * onTabSelected, onTabReselected 에게서 매개변수 인자를 받음.
    * tab_pos 현재 배너 번호 객체
    * 다른탭을 누를 시 onTabLayout 메소드가 실행
    * 같은탭을 누를 시 onTabReselected 메소드가 실행
    * */
    private fun tabBtn(tab_pos: Int) {
        val getViewPager = viewPager!!.currentItem
        when (tab_pos) {
            0 -> {
                if (getViewPager % 3 == 1) {
                    viewPager!!.setCurrentItem(getViewPager - 1, false)
                } else if (getViewPager % 3 == 2) {
                    viewPager!!.setCurrentItem(getViewPager - 2, false)
                }
                Log.i("ddddddd", "첫번째")
            }
            1 -> {
                if (getViewPager % 3 == 0) {
                    viewPager!!.setCurrentItem(getViewPager + 1, false)
                } else if (getViewPager % 3 == 2) {
                    viewPager!!.setCurrentItem(getViewPager - 1, false)
                }
                Log.i("ddddddd", "두번째")
            }
            2 -> {
                if (getViewPager % 3 == 1) {
                    viewPager!!.setCurrentItem(getViewPager + 1, false)
                } else if (getViewPager % 3 == 0) {
                    viewPager!!.setCurrentItem(getViewPager + 2, false)
                }
                Log.i("ddddddd", "세번째")
            }
            else -> {}
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
    private fun setViewPager() {
        viewPager!!.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager!!.offscreenPageLimit = 3
        viewPager!!.setCurrentItem(3, false)
        indicator!!.animatePageSelected(0)
        viewPager!!.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0) {
                    viewPager!!.currentItem = position
                    // 3초마다 자동 넘기기
                    sliderHandler.removeCallbacks(sliderRunnable!!)
                    sliderHandler.postDelayed(sliderRunnable!!, 3000)
                }
            }

            // 스크롤이 무한대일 경우 3 -> 1 로 인디케이터 설정
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator!!.animatePageSelected(position % page)
                binding!!.tab.selectTab(binding!!.tab.getTabAt(position % page))
                if (position == 6) {
                    viewPager!!.setCurrentItem(3, false)
                } else if (position == 2) {
                    viewPager!!.setCurrentItem(5, false)
                }
            }
        })
    }
}