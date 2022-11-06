package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class CartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        return view;
    }
}