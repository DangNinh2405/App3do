package com.example.app3do.data.api;

import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseAPIClient {
    public static final String API_SERVICE = "API_SERVICE";
    public static final String API_FIREBASE = "API_FIREBASE";

    private Map<String, Retrofit> mapRetrofit;
    private static String url = "http://3do.com.vn";
    private static String url_firebase = "https://fcm.googleapis.com";
    private static String keyHeader_Authorization = "Authorization";
    private static String valueHeader_Authorization = "Basic YmFzYWNvOmJhc2FjbyMkJSVe";

    private static String keyHeader_Authorization_Firebase = "Authorization";
    private static String valueHeader_Authorization_Firebase = "key=AAAAohAM58Q:APA91bFXTGrFDHnlHwcY2ZbBdCMoWOHDplD2NaaP8ADu52rU4lpkohG5Iu1Ho45dVs8mnFBd7Qv2GCXbNxJPghXWKlMZvnjKbEGJFsyEpMgwHRJk6knwqWRQc6epg_fndt_04tpGets2";
    private Retrofit retrofit, retrofitFirebase;
    private static BaseAPIClient instance;

    public BaseAPIClient(){
        mapRetrofit = new HashMap<>();
        mapRetrofit.put(API_SERVICE, getRetrofit());
        mapRetrofit.put(API_FIREBASE, getRetrofitFirebase());
    }

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

    public <T> T getAPIService(String keyRetrofit, Class<T> tClass) {
        return mapRetrofit.get(keyRetrofit).create(tClass);
    }

    private Retrofit getRetrofit(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .client(getOkHttpClient(false))
                    .build();
        }
        return retrofit;
    }

    private Retrofit getRetrofitFirebase(){
        if(retrofitFirebase == null) {
            retrofitFirebase = new Retrofit.Builder()
                    .baseUrl(url_firebase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient(true))
                    .build();
        }

        return retrofitFirebase;
    }

    private OkHttpClient getOkHttpClient(boolean isFirebase){
        Interceptor interceptor;
        if (isFirebase) {
            interceptor = getInterceptorFirebase();
        } else {
            interceptor = getInterceptor();
        }

        HttpLoggingInterceptor httpLogging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
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

    private Interceptor getInterceptorFirebase(){
        return chain -> {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder()
                    .addHeader(keyHeader_Authorization_Firebase, valueHeader_Authorization_Firebase);
            return chain.proceed(builder.build());
        };
    }
}
