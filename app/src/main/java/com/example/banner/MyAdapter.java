package com.example.banner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter extends FragmentStateAdapter {
    public int count;

    public MyAdapter(FragmentActivity activity, int count) {
        super(activity);
        this.count = count;
    }
    /*
    * Fragment
    * 원하는 viewpager 위치에 fragment 를 생성
    * */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = position % count;
        if (index == 0) {
            return new fragment_1p();
        } else if (index == 1) {
            return new fragment_2p();
        } else {
            return new fragment_3p();
        }
    }
    // 배너 아이템 갯수
    @Override
    public int getItemCount() {
        return 10000;
    }
}
