package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.FromoFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.TransactionFragment;

public class NotificationAdapter extends FragmentStateAdapter {

    public NotificationAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TransactionFragment();
            case 1:
                return new FromoFragment();
            default:
                return new TransactionFragment();

        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
