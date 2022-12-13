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

    @Override
    public int getItemCount() {
        return 3;
    }
}
