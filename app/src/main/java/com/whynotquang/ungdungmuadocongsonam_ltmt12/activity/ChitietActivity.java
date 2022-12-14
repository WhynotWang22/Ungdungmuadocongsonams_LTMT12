package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ColorAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ImageSliderAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.SizeAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.even.Even;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.interFace.ItemClickListener;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Cart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Comment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Favourites;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ProductComment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    List<Products> prolistyeuthich;
    Product products = new Product();
    SliderView img_product;
    TextView tv_title_product_chitiet, tv_so_luot_review, tv_price_product_chitiet, tv_chitietsanpham;
    Button btn_themsanpham;
    ImageButton btnback_chitiet;
    ImageView btnYeuthich;
    List<Product> productList;
    String id;
    ArrayList<String> list_img;
    ArrayList<String> list_sizes;
    ArrayList<String> list_color;
    CardView cardview_img;
    ProgressBar progressBar;
    RecyclerView recyclerView_size, rc_view_color;
    ItemClickListener itemClickListener;
    SizeAdapter sizeAdapter;
    ColorAdapter colorAdapter;
    String idProduct;
    int tongtien = 0;
    int soluong = 1;
    String color;
    String size;
    RatingBar ratingBar_chitiet;
    private Boolean ischecked = true;
    int stock;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        tv_title_product_chitiet = findViewById(R.id.tv_title_product_chitiet);
        tv_price_product_chitiet = findViewById(R.id.tv_price_product_chitiet);
        tv_so_luot_review = findViewById(R.id.tv_so_luot_review);
        img_product = findViewById(R.id.img_product);
        btnYeuthich = (ImageView) findViewById(R.id.btn_yeuthich);
        cardview_img = findViewById(R.id.cardview_img);
        recyclerView_size = findViewById(R.id.rc_view_size);
        rc_view_color = findViewById(R.id.rc_view_color);
        btn_themsanpham = findViewById(R.id.btn_themsanpham);
        tv_chitietsanpham = findViewById(R.id.tv_chitietsanpham);
        btnback_chitiet = findViewById(R.id.btnback_chitiet);
        ratingBar_chitiet = findViewById(R.id.ratingBar_chitiet);
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
        list_color = new ArrayList<>();
        prolistyeuthich = new ArrayList<Products>();
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
        btnYeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ischecked) {
                    btnYeuthich.setColorFilter(Color.RED);
                    addYeuThich();
                    ischecked = false;
                } else {
                    EventBus.getDefault().postSticky(new Even(2));
                    btnYeuthich.setColorFilter(Color.BLACK);
                    deleleHeart();
                    ischecked = true;
                }
            }
        });
    }

    private void deleleHeart() {
        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String token = sp1.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "favorite/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Products> call = apiService.deleteItemFavorite(token, products.get_id());
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) {
                    btnYeuthich.setColorFilter(Color.BLACK);
                    Toast.makeText(getApplicationContext(), "X??a th??nh c??ng kh???i m???c ???? th??ch!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "X??a kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Vui l??ng ki???m tra l???i m???ng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addYeuThich() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "favorite/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Cart> call = apiService.postAddFavorite(token, products.get_id());
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChitietActivity.this, "Th??m th??nh c??ng v??o m???c ???? th??ch!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChitietActivity.this, "Th??m y??u th??ch kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
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
                recyclerView_size.post(new Runnable() {
                    @Override
                    public void run() {
                        colorAdapter.notifyDataSetChanged();
                    }
                });
                color = s;
            }
        };
    }

    private void postAddCart() {
        if (color == null) {
            showNotiCheck("Vui l??ng ch???n M??u");
//            Toast.makeText(ChitietActivity.this, "Vui l??ng ch???n m??u", Toast.LENGTH_SHORT).show();
            return;
        }
        if (size == null) {
            showNotiCheck("Vui l??ng ch???n Size");
//            Toast.makeText(ChitietActivity.this, "Vui l??ng ch???n size", Toast.LENGTH_SHORT).show();
            return;
        }
        if (stock < 1) {
            showNotiCheck("S???n ph???m ???? h???t h??ng.");
//            Toast.makeText(ChitietActivity.this, "S???n ph???m ???? h???t h??ng.", Toast.LENGTH_SHORT).show();
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
        Call<Products> call = apiService.postCart(token, idProduct, soluong, size, color);
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    showNoti("???? th??m v??o gi???");
//                    Toast.makeText(ChitietActivity.this, "Th??m th??nh c??ng", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ChitietActivity.this, "Th??m kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ChitietActivity.this, "Vui l??ng ki???m tra l???i m???ng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNoti(String text) {
        Dialog dialog = new Dialog(ChitietActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setDimAmount(0.0f);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);
        TextView tv_noti = dialog.findViewById(R.id.tv_dialog_noti);
        tv_noti.setText(text);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
    }

    private void showNotiCheck(String text) {
        Dialog dialog = new Dialog(ChitietActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setDimAmount(0.0f);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_check);
        TextView tv_noti = dialog.findViewById(R.id.tv_dialog_noti_check);
        tv_noti.setText(text);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
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
                    Log.d("", "" + id);
                    progressBar.setVisibility(View.GONE);
                    tv_title_product_chitiet.setText(response.body().getTitle());
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tv_price_product_chitiet.setText(decimalFormat.format(response.body().getPrice()) + "??");
                    tv_chitietsanpham.setText(response.body().getDesc());
                    idProduct = response.body().get_id();
                    tongtien = response.body().getPrice();
                    stock = response.body().getStock();
//                    color= response.body().getColor();
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
                    //color
                    list_color = response.body().getColor();
                    colorAdapter = new ColorAdapter(list_color, itemClickListener);
                    rc_view_color.setAdapter(colorAdapter);
                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(ChitietActivity.this, RecyclerView.HORIZONTAL, false);
                    rc_view_color.setLayoutManager(linearLayoutManager2);
                    products = response.body();
                    ////check trang thai yeu thich
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
                    String token = sp.getString("token", "");
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(AppConstain.BASE_URL + "favorite/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ApiService apiService = retrofit.create(ApiService.class);
                    apiService.getFavotires(token, response.body().get_id()).enqueue(new Callback<Favourites>() {
                        @Override
                        public void onResponse(Call<Favourites> call, Response<Favourites> response) {
                            if (response.isSuccessful()) {
                                if (response.body().isFavorite()) {
                                    btnYeuthich.setColorFilter(Color.RED);
                                    ischecked = false;
                                } else {
                                    btnYeuthich.setColorFilter(Color.BLACK);
                                    ischecked = true;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Favourites> call, Throwable t) {

                        }
                    });
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ChitietActivity.this, "Kh??ng t??m th???y s???n ph???m", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ChitietActivity.this, "Vui l??ng ki???m tra l???i m???ng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getComment() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "comment/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getComments(id).enqueue(new Callback<ProductComment>() {
            @Override
            public void onResponse(Call<ProductComment> call, Response<ProductComment> response) {
                if (response.isSuccessful()) {
                    productCommentList = new ArrayList<>();
                    productCommentList.addAll(response.body().getProductItems());
                    tv_so_luot_review.setText(response.body().getDem() + " ????nh gi??");
                    for (int i = 0; i < productCommentList.size(); i++) {
                        ratingBar_chitiet.setRating(response.body().getAvg());
                    }
                } else {
                    Toast.makeText(ChitietActivity.this, "Kh??ng t??m th???y b??nh lu???n", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ProductComment> call, Throwable t) {

            }
        });
    }
}