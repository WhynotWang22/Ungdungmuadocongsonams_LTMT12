package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.RealPathUtil;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.io.File;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {
    EditText ed_hoten_profile, ed_email_profile,ed_diachi_profile,ed_sdt_profile;
    Button btn_change_profile;
    ProgressBar progressBar;
    ImageView img_profile;
    String token;
    Uri uri;
    String linkimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ed_hoten_profile = findViewById(R.id.ed_hoten_profile);
        ed_email_profile = findViewById(R.id.ed_email_profile);
        ed_diachi_profile = findViewById(R.id.ed_diachi_profile);
        ed_sdt_profile = findViewById(R.id.ed_phone_number_profile);
        btn_change_profile = findViewById(R.id.btn_change_profile);
        img_profile = findViewById(R.id.profile_image);


        progressBar = (ProgressBar) findViewById(R.id.spin_kit_change_profile);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.GONE);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        token = sp.getString("token", "");
        getData();

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkQuyen();
            }
        });

        btn_change_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editData();
            }
        });
    }

    private void editData() {
        String full_name = ed_hoten_profile.getText().toString().trim();
        String phone_number = ed_sdt_profile.getText().toString().trim();
        String email = ed_email_profile.getText().toString().trim();
        String diachi = ed_diachi_profile.getText().toString().trim();
        if (email.isEmpty() || full_name.isEmpty() || diachi.isEmpty() || phone_number.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!validateEmail(email)) {
            Toast.makeText(ProfileActivity.this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!validatePhone(phone_number)) {
            Toast.makeText(ProfileActivity.this, "Số điện thoại phải là số", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (phone_number.length()<10) {
            Toast.makeText(ProfileActivity.this, "Số điện thoại phải trên 10 số", Toast.LENGTH_SHORT).show();
            return;
        }
        if (img_profile==null){
            Toast.makeText(ProfileActivity.this, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        String strRealPath = RealPathUtil.getRealPath(ProfileActivity.this,uri);


        File file = new File(strRealPath);


        RequestBody requestBodyName = RequestBody.create(MediaType.parse("multipart/form-data"),full_name);
        RequestBody requestBodyEmail = RequestBody.create(MediaType.parse("multipart/form-data"),email);
        RequestBody requestBodySdt = RequestBody.create(MediaType.parse("multipart/form-data"),phone_number);
        RequestBody requestBodyDiachi = RequestBody.create(MediaType.parse("multipart/form-data"),diachi);
        RequestBody requestBodyImg = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar",file.getName(),requestBodyImg);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.putEditProfile(token,requestBodyName,requestBodyEmail,requestBodySdt,requestBodyDiachi,body);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, "Loi api", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.getProfile(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    progressBar.setVisibility(View.GONE);
                    ed_email_profile.setText(response.body().getEmail());
                    ed_hoten_profile.setText(response.body().getFullName());
                    ed_sdt_profile.setText(Integer.toString(response.body().getPhoneNumber()));
                    ed_diachi_profile.setText(response.body().getAddress());
                    linkimg = response.body().getAvatar();
                    Glide.with(ProfileActivity.this).load(response.body().getAvatar()).into(img_profile);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, "Không lấy được dữ liệu user", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private boolean validatePhone(String phone) {
        Pattern pattern = Patterns.PHONE;
        return pattern.matcher(phone).matches();
    }
    private void checkQuyen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                String [] permisson = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permisson,2);
            }else {
                getImg();
            }
        }else {
            getImg();
        }
    }

    private void getImg() {
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==2 && resultCode == RESULT_OK && data != null){
            uri = data.getData();
            img_profile.setImageURI(uri);
        }else {
                Toast.makeText(ProfileActivity.this, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
        }
    }
}