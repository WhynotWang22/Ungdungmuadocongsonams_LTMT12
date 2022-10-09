package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class AccountFragment extends Fragment {

    TextView tv_name,tv_email;
    RelativeLayout btn_my_order,btn_diachi,btn_phuongthucthanhtoan,btn_setting,btn_privacy_setting;
    Button btn_logout;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        tv_name = view.findViewById(R.id.tv_name);
        tv_email = view.findViewById(R.id.tv_email);
        btn_my_order = view.findViewById(R.id.btn_my_order);
        btn_diachi = view.findViewById(R.id.btn_diachi);
        btn_phuongthucthanhtoan = view.findViewById(R.id.btn_phuongthucthanhtoan);
        btn_setting = view.findViewById(R.id.btn_setting);
        btn_privacy_setting = view.findViewById(R.id.btn_privacy_setting);
        btn_logout = view.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "aaaaa", Toast.LENGTH_SHORT).show();
            }
        });
        return  view;
    }
}