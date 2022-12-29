package com.example.banner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyAdapter(activity: FragmentActivity?, private var count: Int) : FragmentStateAdapter(activity!!) {
    /*
    * Fragment
    * 원하는 viewpager 위치에 fragment 를 생성
    * */
    override fun createFragment(position: Int): Fragment {
        return when (position % count) {
            0 -> Fragment1p()
            1 -> Fragment2P()
            else -> Fragment3p()
        }
    }

    // 배너 아이템 갯수
    override fun getItemCount(): Int {
        return 7
    }
}