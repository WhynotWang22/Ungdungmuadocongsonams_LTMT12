package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class FotgotPassChangeActivity extends AppCompatActivity {
    String token = null;


    private EditText edFotgotChangepass;
    private Button btnXacnhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotgot_pass_change);

        edFotgotChangepass = (EditText) findViewById(R.id.ed_fotgot_changepass);
        btnXacnhan = (Button) findViewById(R.id.btn_xacnhan);

        doimatkhau();
    }

    private void doimatkhau() {

    }

}