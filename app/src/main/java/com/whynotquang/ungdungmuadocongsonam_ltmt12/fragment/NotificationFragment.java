package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.NotificationAdapter;


public class NotificationFragment extends Fragment {

    TabLayout mTableLayout;
    ViewPager2 mViewPager2;


    public NotificationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTableLayout = view.findViewById(R.id.tab_layout);
        mViewPager2 = view.findViewById(R.id.pager);
        NotificationAdapter notificationAdapter = new NotificationAdapter(getActivity());
        mViewPager2.setAdapter(notificationAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mTableLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Transaction");
                        break;

                    case 1:
                        tab.setText("Fromo");
                        break;
                }

            }
        });
        tabLayoutMediator.attach();
    }
}