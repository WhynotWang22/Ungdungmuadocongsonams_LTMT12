package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.FavoritesAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.even.Even;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Favourites;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YeuThichActivity extends AppCompatActivity {
    private RecyclerView rc_cothebancungthich;
    private ImageButton btnbackYeuthich;
    private FavoritesAdapter favoritesAdapter;
    private ProgressBar spinKitYeuthich;
    private List<Favourites> favouritesList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeu_thich);
        rc_cothebancungthich = findViewById(R.id.rc_cothebancungthich);
        rc_cothebancungthich.setLayoutManager(new GridLayoutManager(this, 2));
        spinKitYeuthich = (SpinKitView) findViewById(R.id.spin_kit_yeuthich);
        Sprite threeBounce = new ThreeBounce();
        spinKitYeuthich.setIndeterminateDrawable(threeBounce);
        getListyeuthichTim();
        spinKitYeuthich.setVisibility(View.VISIBLE);
        btnbackYeuthich = (ImageButton) findViewById(R.id.btnback_yeuthich);
        btnbackYeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void getListyeuthichTim() {
        favouritesList = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "favorite/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getListFavorite(token).enqueue(new Callback<List<Favourites>>() {
            @Override
            public void onResponse(Call<List<Favourites>> call, Response<List<Favourites>> response) {
                if (response.isSuccessful()) {
                    favouritesList.addAll(response.body());
                    spinKitYeuthich.setVisibility(View.GONE);
                    favoritesAdapter = new FavoritesAdapter(YeuThichActivity.this, response.body());
                    rc_cothebancungthich.setAdapter(favoritesAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Favourites>> call, Throwable t) {

            }
        });
    }

    ///tai lai du lieu
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Even event) {
        if (event.getId() == 2) {
            getListyeuthichTim();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}