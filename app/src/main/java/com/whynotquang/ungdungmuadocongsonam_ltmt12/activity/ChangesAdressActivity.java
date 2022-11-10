package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class ChangesAdressActivity extends AppCompatActivity {
    private EditText edDiachiMoi;
    private Button btnChangeAdress;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changes_adress);
        edDiachiMoi = (EditText) findViewById(R.id.ed_diachi_moi);
        btnChangeAdress = (Button) findViewById(R.id.btn_change_adress);

    }
    private void updateAdress(){

    }
}