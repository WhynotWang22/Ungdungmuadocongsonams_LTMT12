package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.MainActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewActivity extends AppCompatActivity {
    private List<Product> productList;
    String id;
    private RatingBar ratingBar;
    private ImageView btnbackReview;
    private LinearLayout productReview;
    private ImageView imgProductReview;
    private TextView tvNameProductReview;
    private TextView tvSizeProductReview;
    private TextView tvColorProductReview;
    private TextView tvProductReview;
    private RatingBar ratingbar;
    private TextView tvProductReview3;
    private EditText edCmtReview;
    private Button btnReview;
    private ProgressBar progressBar;
    ImageButton btnback_review;

    ///
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        btnbackReview = (ImageView) findViewById(R.id.btnback_review);
        productReview = (LinearLayout) findViewById(R.id.product_review);
        imgProductReview = (ImageView) findViewById(R.id.img_product_review);
        tvNameProductReview = (TextView) findViewById(R.id.tv_name_product_review);
        tvSizeProductReview = (TextView) findViewById(R.id.tv_size_product_review);
        tvColorProductReview = (TextView) findViewById(R.id.tv_color_product_review);
        tvProductReview = (TextView) findViewById(R.id.tv_product_review);
        ratingbar = (RatingBar) findViewById(R.id.ratingbar);
        btnback_review = findViewById(R.id.btnback_review);
        tvProductReview3 = (TextView) findViewById(R.id.tv_product_review3);
        edCmtReview = (EditText) findViewById(R.id.ed_cmt_review);
        btnReview = (Button) findViewById(R.id.btn_review);
        ratingBar = findViewById(R.id.ratingbar);
        progressBar = findViewById(R.id.spin_kit_comments);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.GONE);

        ///
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getdata();
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentDes = edCmtReview.getText().toString().trim();
                if (commentDes.isEmpty()) {
                    Toast.makeText(ReviewActivity.this, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
//                PostComment();
            }
        });
        btnback_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getdata() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "products/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Product> call = apiService.getDetailProduct(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.body() != null) {
                    tvNameProductReview.setText(response.body().getTitle());
                    tvSizeProductReview.setText("Size:" + response.body().getSizes().get(0) + "|");
                    tvColorProductReview.setText("Color:" + response.body().getColor());
                    Glide.with(getApplicationContext()).load(response.body().getImg())
                            .override(64, 70)
                            .error(R.drawable.mau)
                            .into(imgProductReview);

                } else {
                    Toast.makeText(ReviewActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ReviewActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void PostComment() {
//        String commentDes = edCmtReview.getText().toString().trim();
//        Float ratingBar1 = ratingBar.getRating();
//        SharedPreferences sp = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
//        String token = sp.getString("token", "");
//        productList = new ArrayList<>();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(AppConstain.BASE_URL + "comment/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiService apiService = retrofit.create(ApiService.class);
//        Call<List<Product>> call1 = apiService.postComment(token, id, ratingBar1, commentDes);
//        call1.enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(ReviewActivity.this, "Lỗi Api", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ReviewActivity.this, "Comment không thành công", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//                progressBar.setVisibility(View.VISIBLE);
//                edCmtReview.setText("");
//                Toast.makeText(ReviewActivity.this, "Comment Thành Công", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ReviewActivity.this, OrderActivity.class);
//                startActivity(intent);
//                finishAffinity();
//            }
//        });
//
//    }
}