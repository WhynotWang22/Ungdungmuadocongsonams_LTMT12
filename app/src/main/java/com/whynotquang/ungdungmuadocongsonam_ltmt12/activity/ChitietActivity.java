package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.smarteist.autoimageslider.SliderView;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.interFace.ItemClickListener;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
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

public class ChitietActivity extends AppCompatActivity {
    List<Comment> productCommentList;
    SliderView img_product;
    TextView tv_title_product_chitiet, tv_so_luot_review, tv_price_product_chitiet,tv_chitietsanpham;
    RadioButton radioGroup_color, radioGroup_size;
    Button btn_themsanpham;
    ImageButton btnback_chitiet;
    List<Product> productList;
    String id;
    ArrayList<String> list_img;
    ArrayList<String> list_sizes;
    CardView cardview_img;
    ProgressBar progressBar;
    RecyclerView recyclerView_size;
    ItemClickListener itemClickListener;
    SizeAdapter sizeAdapter;
    String idProduct;
    int tongtien=0;
    int soluong=1;
    String color;
    String size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        tv_title_product_chitiet = findViewById(R.id.tv_title_product_chitiet);
        tv_price_product_chitiet = findViewById(R.id.tv_price_product_chitiet);
        tv_so_luot_review = findViewById(R.id.tv_so_luot_review);
        img_product = findViewById(R.id.img_product);
        cardview_img = findViewById(R.id.cardview_img);
        recyclerView_size = findViewById(R.id.rc_view_size);
        btn_themsanpham = findViewById(R.id.btn_themsanpham);
        tv_chitietsanpham = findViewById(R.id.tv_chitietsanpham);
        btnback_chitiet = findViewById(R.id.btnback_chitiet);

        progressBar = (ProgressBar) findViewById(R.id.spin_kit_chitietsp);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getDataProduct();
        reviews();
        getComment();
        list_img = new ArrayList<>();
        list_sizes = new ArrayList<>();

        selectSize();

        btn_themsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAddCart();
            }
        });
        btnback_chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void reviews() {
        tv_so_luot_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CommentActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });
    }

    private void postAddCart() {
        if (color==null){
            Toast.makeText(ChitietActivity.this, "Vui lòng chọn màu", Toast.LENGTH_SHORT).show();
            return;
        }
        if (size==null){
            Toast.makeText(ChitietActivity.this, "Vui lòng chọn size", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "cart/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Products> call = apiService.postCart(token,idProduct,soluong,size,color);
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ChitietActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ChitietActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ChitietActivity.this, "Lỗi api không thêm được vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectSize() {
        itemClickListener = new ItemClickListener() {
            @Override
            public void onClickSize(String s) {
                recyclerView_size.post(new Runnable() {
                    @Override
                    public void run() {
                        sizeAdapter.notifyDataSetChanged();
                    }
                });
                size = s;
            }

            @Override
            public void onClickColor(String s) {

            }
        };
    }

    public void getDataProduct() {
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
                    progressBar.setVisibility(View.GONE);
                    tv_title_product_chitiet.setText(response.body().getTitle());
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tv_price_product_chitiet.setText(decimalFormat.format(response.body().getPrice()) + "đ");
                    tv_chitietsanpham.setText(response.body().getDesc());
                    idProduct = response.body().get_id();
                    tongtien= response.body().getPrice();
                    color= String.valueOf(response.body().getColor());

                    list_img = response.body().getImg_product();
                    ImageSliderAdapter imageSlider = new ImageSliderAdapter(ChitietActivity.this, list_img);
                    img_product.setSliderAdapter(imageSlider);
                    img_product.startAutoCycle();

                    //size
                    list_sizes = response.body().getSizes();
                    sizeAdapter = new SizeAdapter(list_sizes, itemClickListener);
                    recyclerView_size.setAdapter(sizeAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChitietActivity.this, RecyclerView.HORIZONTAL, false);
                    recyclerView_size.setLayoutManager(linearLayoutManager);

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ChitietActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ChitietActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

   private void getComment(){
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
                   tv_so_luot_review.setText(response.body().getDem()  + " Bình Luận");

               } else {
                   Toast.makeText(ChitietActivity.this, "Không tìm thấy bình luận", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<ProductComment> call, Throwable t) {
               Toast.makeText(ChitietActivity.this, "Không có bình luận", Toast.LENGTH_SHORT).show();
           }
       });
   }
}