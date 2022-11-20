package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.CartAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.CommentAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ImageSliderAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.SizeAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Comment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ProductComment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentActivity extends AppCompatActivity {
    String id;
    private RecyclerView rc_comment;
    private TextView tv_avg;
    private TextView tv_dem_comments;
    private CommentAdapter commentAdapter;
    List<Comment> productCommentList;
    ImageButton btnback_comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        rc_comment = findViewById(R.id.rc_comment);
        tv_avg = findViewById(R.id.tv_avg);
        tv_dem_comments = findViewById(R.id.tv_dem_comments);
        btnback_comment = findViewById(R.id.btnback_comment);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getdataComment();
        btnback_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getdataComment() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        rc_comment.setLayoutManager(linearLayoutManager);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "comment/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ProductComment> call = apiService.getComments(id);
        call.enqueue(new Callback<ProductComment>() {
            @Override
            public void onResponse(Call<ProductComment> call, Response<ProductComment> response) {
                if (response.body() != null) {
                    productCommentList = new ArrayList<>();
                    productCommentList.addAll(response.body().getProductItems());
                    tv_avg.setText(response.body().getAvg() + "");
                    tv_dem_comments.setText(response.body().getDem()  + " Bình Luận");
                    CommentAdapter commentAdapter = new CommentAdapter(productCommentList,getApplicationContext());
                    rc_comment.setAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(CommentActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductComment> call, Throwable t) {
                Toast.makeText(CommentActivity.this, "Không có bình luận", Toast.LENGTH_SHORT).show();
            }
        });
    }
}