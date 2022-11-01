package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.NotificationAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Notification;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    List<Notification> notificationList;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = view.findViewById(R.id.rc_view_noti);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        notificationList = new ArrayList<>();
        notificationList.add(new Notification("1","aaa","aaa","https://bucket.nhanh.vn/store/662/ps/20221024/AH0332__22_.jpg","11:22"));
        notificationList.add(new Notification("2","aaa","aaa","https://bucket.nhanh.vn/store/662/ps/20221020/BL0314__7_.jpg","11:22"));
        notificationList.add(new Notification("3","aaa","aaa","https://bucket.nhanh.vn/store/662/ps/20221024/AH0332__22_.jpg","11:22"));
        notificationList.add(new Notification("4","aaa","aaa","https://bucket.nhanh.vn/store/662/ps/20221020/BL0314__7_.jpg","11:22"));
        notificationList.add(new Notification("5","aaa","aaa","https://bucket.nhanh.vn/store/662/ps/20221024/AH0332__22_.jpg","11:22"));

        NotificationAdapter adapter = new NotificationAdapter(getActivity(),notificationList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        return view;
    }
}