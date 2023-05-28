package com.example.app3do.data.api;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseAPIClient {
    private static String url = "http://3do.com.vn";
    private static String keyHeader_Authorization = "Authorization";
    private static String valueHeader_Authorization = "Basic YmFzYWNvOmJhc2FjbyMkJSVe";

    private Retrofit retrofit;
    private static BaseAPIClient instance;

    private BaseAPIClient(){}
    public static BaseAPIClient getInstance(){
        if(instance == null) {
            synchronized (BaseAPIClient.class) {
                if(instance == null) {
                    instance = new BaseAPIClient();
                }
            }
        }
        return instance;
    }

    public APIService getAPIService() {
        return getRetrofit().create(APIService.class);
    }

    private Retrofit getRetrofit(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }

    private OkHttpClient getOkHttpClient(){
        HttpLoggingInterceptor httpLogging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(getInterceptor())
                .addInterceptor(httpLogging)
                .build();
    }


    private Interceptor getInterceptor(){
        return chain -> {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder()
                    .addHeader(keyHeader_Authorization, valueHeader_Authorization);
            return chain.proceed(builder.build());
        };
    }
}
