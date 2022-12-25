package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.gson.Gson;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.DiaChiAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Address;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.AddressItem;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Cart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.interFace.ItemClickAddressListener;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Order;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.momo.momo_partner.AppMoMoLib;

public class CheckOutActivity extends AppCompatActivity {
    TextView tv_so_sanpham;
    TextView tv_gia_checkout;
    TextView tv_fee_ship_checkout;
    TextView tv_tongtien_checkout, tv_them_dia_chi;
    Button btn_thanhtoan;
    ImageButton btnback_checkout;
    RecyclerView rc_view_diachi;
    ProgressBar progressBar;
    RadioButton btn_radio_nhanthanhtoan, btn_radio_stripe, btn_radio_momo;
    String token;
    List<Products> productsList;
    List<Address> addressList;
    DiaChiAdapter adapter;
    int feeship = 0;
    String id, name, phoneNumber, diachi;
    String idUser = "";
    double amount = 0;
    LinearLayout layout_no_diachi;
    private static final String TAG = "CheckoutActivity";
    private static final String BACKEND_URL = "https://mofshop.shop/api/order";
    private String paymentIntentClientSecret;
    private PaymentSheet paymentSheet;
    ///MOMO
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Thanh toán đơn hàng";
    private String merchantCode = "MOMO0WGP20220901";
    private String merchantNameLabel = "Nguyễn Đình Quang";
    private String description = "Thanh toán dịch vụ mua hàng online";

    @SuppressLint({"CutPasteId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
        tv_so_sanpham = findViewById(R.id.tv_so_sanpham);
        tv_gia_checkout = findViewById(R.id.tv_gia_checkout);
        tv_fee_ship_checkout = findViewById(R.id.tv_fee_ship_checkout);
        tv_tongtien_checkout = findViewById(R.id.tv_tongtien_checkout);
        tv_them_dia_chi = findViewById(R.id.tv_them_dia_chi);
        rc_view_diachi = findViewById(R.id.rc_view_diachi);
        btn_radio_nhanthanhtoan = findViewById(R.id.btn_radio_nhanthanhtoan);
        btn_radio_stripe = findViewById(R.id.btn_radio_stripe);
        btn_radio_momo = findViewById(R.id.btn_radio_momo);
        btnback_checkout = findViewById(R.id.btnback_checkout);
        btn_thanhtoan = findViewById(R.id.btn_thanhtoan);
        layout_no_diachi = findViewById(R.id.layout_no_diachi);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit_checkout);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);
        productsList = new ArrayList<>();
        addressList = new ArrayList<>();

        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        token = sp1.getString("token", "");
        getData();
        getDataAddress();

        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_51M4yABATGyZmN1HXQyaX4CeLlFETzr1Jr74am8MTrcmpMKpv70OmWrS7U1cvKT7b0Z1IoIdpqWuNFHkvM3pqPMrx00yk19m5Wn"
        );

        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name == null || diachi == null || phoneNumber == null) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CheckOutActivity.this, "Vui lòng chọn địa chỉ", Toast.LENGTH_SHORT).show();
                    return;
                }
//                postOrder();
                String typePay = "";
                if (btn_radio_nhanthanhtoan.isChecked()) {
                    postOrder();
                } else if (btn_radio_stripe.isChecked()) {
                    PaymentSheet.Configuration configuration = new PaymentSheet.Configuration("Example, Inc.");
                    paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration);
                }
                if (btn_radio_momo.isChecked()) {
                    requestPayment();
                }
            }
        });
        tv_them_dia_chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckOutActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });
        btnback_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

    }

    private void getDataAddress() {
        adapter = new DiaChiAdapter(addressList, new ItemClickAddressListener() {
            @Override
            public void onClickAddress(Address address) {
                name = address.getName();
                diachi = address.getDetailAddress();
                phoneNumber = String.valueOf(address.getNumberPhone());
            }
        });
        rc_view_diachi.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CheckOutActivity.this, RecyclerView.VERTICAL, false);
        rc_view_diachi.setLayoutManager(linearLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "address/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<AddressItem> call = apiService.getAddress(token);
        call.enqueue(new Callback<AddressItem>() {
            @Override
            public void onResponse(Call<AddressItem> call, Response<AddressItem> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    AddressItem gioHang = response.body();
                    List<Address> datas = gioHang.getAddress();
                    //dung for de doc array
                    for (Address data : datas) {
                        addressList.add(data);
                        adapter.notifyDataSetChanged();
                    }
                    if (addressList.size() == 0) {
                        layout_no_diachi.setVisibility(View.VISIBLE);
                    } else {
                        layout_no_diachi.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressItem> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CheckOutActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "cart/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Cart> call = apiService.getCart(token);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Cart gioHang = response.body();
                    List<Products> datas = gioHang.getProducts();
                    //dung for de doc array
                    for (Products data : datas) {
                        productsList.add(data);
                    }
                    int soluong_sanpham = 0;
                    for (int i = 0; i < productsList.size(); i++) {
                        soluong_sanpham += productsList.get(i).getQuantity();
                    }
                    id = response.body().get_id();
                    idUser = gioHang.getUserId();
                    amount = Double.valueOf(response.body().getTotal());
                    tv_so_sanpham.setText(soluong_sanpham + " sản phẩm");
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tv_gia_checkout.setText(decimalFormat.format(Integer.parseInt(response.body().getTotal())) + "đ");
                    tv_fee_ship_checkout.setText(decimalFormat.format(feeship) + "đ");
                    int tongtien;
                    tongtien = Integer.parseInt(response.body().getTotal()) + feeship;
                    tv_tongtien_checkout.setText(decimalFormat.format(tongtien) + "đ");

                    fetchPaymentIntent(amount, idUser);
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CheckOutActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    ///thanh toan khi nhan hang
    private void postOrder() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "order/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Order> call = apiService.postOrder(token, id, name, phoneNumber, diachi);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(CheckOutActivity.this, ThanksOrder_Activity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CheckOutActivity.this, "Đặt hàng không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CheckOutActivity.this, "Lỗi api không đặt hàng được", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAlert(String title, @Nullable String message) {
        runOnUiThread(() -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Ok", null)
                    .create();
            dialog.show();
        });
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }

    private void fetchPaymentIntent(double amount, String id) {
        Map<String, Object> payMap = new HashMap<>();
        Map<String, Object> itemMap = new HashMap<>();
        List<Map<String, Object>> itemList = new ArrayList<>();
        itemMap.put("id", id);
        itemMap.put("amount", amount);
        itemList.add(itemMap);
        payMap.put("items", itemList);
        String json = new Gson().toJson(payMap);

        final RequestBody requestBody = RequestBody.create(
                json,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(BACKEND_URL + "/create-payment-intent")
                .post(requestBody)
                .build();

        new OkHttpClient()
                .newCall(request)
                .enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                        showAlert("Không thể tải dữ liệu", "Vui lòng thử lại sau");
                    }

                    @Override
                    public void onResponse(
                            @NonNull okhttp3.Call call,
                            @NonNull okhttp3.Response response
                    ) throws IOException {
                        if (!response.isSuccessful()) {
                            Toast.makeText(CheckOutActivity.this, "Không thể tải trang", Toast.LENGTH_SHORT).show();
                        } else {
                            final JSONObject responseJson = parseResponse(response.body());
                            paymentIntentClientSecret = responseJson.optString("clientSecret");
                            runOnUiThread(() -> btn_thanhtoan.setEnabled(true));
                        }
                    }
                });
    }

    private JSONObject parseResponse(ResponseBody responseBody) {
        if (responseBody != null) {
            try {
                return new JSONObject(responseBody.string());
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error parsing response", e);
            }
        }

        return new JSONObject();
    }

    private void onPaymentSheetResult(
            final PaymentSheetResult paymentSheetResult
    ) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            showToast("Thanh toán thành công!");
            CheckoutStripe();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Log.i(TAG, "Thanh toán đã bị hủy!");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Throwable error = ((PaymentSheetResult.Failed) paymentSheetResult).getError();
            showAlert("Thanh toán thất bại.", error.getLocalizedMessage());
        }
    }

    private void requestPayment() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "cart/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Cart> call = apiService.getCart(token);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
                    AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
                    int tongxien = Integer.parseInt(response.body().getTotal());
                    String ids = String.valueOf(response.body().get_id());
                    Map<String, Object> eventValue = new HashMap<>();
                    //client Required
                    eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
                    eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
                    eventValue.put("amount", tongxien); //Kiểu integer
                    eventValue.put("orderId", ids); //uniqueue id cho BillId, giá trị duy nhất cho mỗi BILL
                    eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn
                    AppMoMoLib.getInstance().requestMoMoCallBack(CheckOutActivity.this, eventValue);
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CheckOutActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
//                    String token = data.getStringExtra("data"); //Token response
//                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if (env == null) {
                        env = "app";
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(AppConstain.BASE_URL + "order/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ApiService apiService = retrofit.create(ApiService.class);
                        Call<Order> call = apiService.postCardOrder(token, id, name, phoneNumber, diachi);
                        call.enqueue(new Callback<Order>() {
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {
                                if (response.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(CheckOutActivity.this, ThanksOrder_Activity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(CheckOutActivity.this, "Đặt hàng không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Order> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(CheckOutActivity.this, "Vui lòng kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    if (token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                    }
                } else if (data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null ? data.getStringExtra("message") : "Thất bại";

                } else if (data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                } else {
                    //TOKEN FAIL
                }
            } else {
            }
        } else {
        }
    }

    private void CheckoutStripe() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "order/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Order> call = apiService.postCardOrder(token, id, name, phoneNumber, diachi);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(CheckOutActivity.this, ThanksOrder_Activity.class);
                    startActivity(intent);
                    finishAffinity();
//                    Toast.makeText(CheckOutActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CheckOutActivity.this, "Đặt hàng không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CheckOutActivity.this, "Vui lòng kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onRestart() {
        super.onRestart();
        addressList.clear();
        getDataAddress();
    }
}