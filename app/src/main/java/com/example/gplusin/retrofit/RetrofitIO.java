package com.example.gplusin.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitIO {
    private static Retrofit retrofit=null;

    public static Retrofit getClient(String baseUrl)
    {
        if (retrofit==null)
        {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client1 = new OkHttpClient.Builder()
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .readTimeout(10000, TimeUnit.SECONDS)
                    .writeTimeout(10000, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(true)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl( baseUrl )
                    .client( client1 )
                    .addConverterFactory( ScalarsConverterFactory.create() )
                    .addConverterFactory( GsonConverterFactory.create() )
                    .addCallAdapterFactory( RxJava2CallAdapterFactory.create() )
                    .build();
        }
        return retrofit;
    }
}
