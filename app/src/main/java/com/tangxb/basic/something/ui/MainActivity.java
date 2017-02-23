package com.tangxb.basic.something.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tangxb.basic.something.R;
import com.tangxb.basic.something.RetrofitClient;
import com.tangxb.basic.something.api.ApiFactory;
import com.tangxb.basic.something.bean.BaseBean;
import com.tangxb.basic.something.bean.WelfareBean;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testRetrofit();
    }

    private void testRetrofit() {
        Call<BaseBean<List<WelfareBean>>> externalBeanCall = ApiFactory.getWelfareAPI().getExternalBean("福利", 20, 1);
        RetrofitClient.enqueue(TAG, "baiiu02", externalBeanCall, new Callback<BaseBean<List<WelfareBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<WelfareBean>>> call, Response<BaseBean<List<WelfareBean>>> response) {
                System.out.println();
            }

            @Override
            public void onFailure(Call<BaseBean<List<WelfareBean>>> call, Throwable t) {
                System.out.println();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RetrofitClient.cancel(TAG);
    }
}
