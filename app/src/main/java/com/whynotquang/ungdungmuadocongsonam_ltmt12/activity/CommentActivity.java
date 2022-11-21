package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
import java.util.Random;

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
    private RatingBar ratingbar_cmt1;
    private ProgressBar progress5;
    private ProgressBar progress4;
    private ProgressBar progress3;
    private ProgressBar progress2;
    private ProgressBar progress1;
    private TextView tvDemsao1;
    private TextView tvDemsao2;
    private TextView tvDemsao3;
    private TextView tvDemsao4;
    private TextView tvDemsao5;
    List<Comment> productCommentList;
    List<Comment> productCommentList1 = new ArrayList<>();
    List<Comment> productCommentList2 = new ArrayList<>();
    List<Comment> productCommentList3 = new ArrayList<>();
    List<Comment> productCommentList4 = new ArrayList<>();
    List<Comment> productCommentList5 = new ArrayList<>();
    ImageButton btnback_comment;
    @Override
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ratingbar_cmt1 = findViewById(R.id.ratingbar_cmt1);
        rc_comment = findViewById(R.id.rc_comment);
        tv_avg = findViewById(R.id.tv_avg);
        tv_dem_comments = findViewById(R.id.tv_dem_comments);
        btnback_comment = findViewById(R.id.btnback_comment);
        tvDemsao1 = (TextView) findViewById(R.id.tv_demsao1);
        tvDemsao2 = (TextView) findViewById(R.id.tv_demsao2);
        tvDemsao3 = (TextView) findViewById(R.id.tv_demsao3);
        tvDemsao4 = (TextView) findViewById(R.id.tv_demsao4);
        tvDemsao5 = (TextView) findViewById(R.id.tv_demsao5);
        progress5 = (ProgressBar) findViewById(R.id.progress_5);
        progress4 = (ProgressBar) findViewById(R.id.progress_4);
        progress3 = (ProgressBar) findViewById(R.id.progress_3);
        progress2 = (ProgressBar) findViewById(R.id.progress_2);
        progress1 = (ProgressBar) findViewById(R.id.progress_1);
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
                    for (int i = 0; i < productCommentList.size(); i++) {
                        ratingbar_cmt1.setRating((response.body().getAvg()));
                        if (productCommentList.get(i).getRatingStar() == 1) {
                            productCommentList1.add(productCommentList.get(i));
                        } else if (productCommentList.get(i).getRatingStar() == 1) {
                            if (productCommentList.get(i).getRatingStar() == 1) {
                                productCommentList1.add(productCommentList.get(i));
                            }
                        } else if (productCommentList.get(i).getRatingStar() == 2) {
                            if (productCommentList.get(i).getRatingStar() == 2) {
                                productCommentList2.add(productCommentList.get(i));

                            }
                        } else if (productCommentList.get(i).getRatingStar() == 3) {
                            if (productCommentList.get(i).getRatingStar() == 3) {
                                productCommentList3.add(productCommentList.get(i));

                            }
                        } else if (productCommentList.get(i).getRatingStar() == 4) {
                            if (productCommentList.get(i).getRatingStar() == 4) {
                                productCommentList4.add(productCommentList.get(i));

                            }
                        } else if (productCommentList.get(i).getRatingStar() == 5) {
                            if (productCommentList.get(i).getRatingStar() == 5) {
                                productCommentList5.add(productCommentList.get(i));
                            }
                        }
                        progress1.setProgress(productCommentList1.size());
                        progress2.setProgress(productCommentList2.size());
                        progress3.setProgress(productCommentList3.size());
                        progress4.setProgress(productCommentList4.size());
                        progress5.setProgress(productCommentList5.size());
                        tvDemsao5.setText(String.valueOf(productCommentList5.size()));
                        tvDemsao4.setText(String.valueOf(productCommentList4.size()));
                        tvDemsao3.setText(String.valueOf(productCommentList3.size()));
                        tvDemsao2.setText(String.valueOf(productCommentList2.size()));
                        tvDemsao1.setText(String.valueOf(productCommentList1.size()));
                    }
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