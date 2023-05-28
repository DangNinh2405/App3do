package com.example.app3do.data.api;

import com.example.app3do.models.account.BodyLogin;
import com.example.app3do.models.home.BodyCategory;
import com.example.app3do.models.home.BodySlide;
import com.example.app3do.models.personal_information.BodyPersonal;
import com.example.app3do.models.product.BodyProduct;
import com.example.app3do.models.register.Register;
import com.example.app3do.models.verify.BodyVerify;
import com.google.gson.JsonElement;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @GET("/api/slider/home-slider")
    Call<BodySlide> getListSlider();

    @GET("/api/category")
    Call<BodyCategory> getListCategory();

    @GET("/api/products")
    Call<BodyProduct> getListProductByCategoryId(@Query("category_id") int category_id, @Query("page") int page);

    @GET("/api/products")
    Call<BodyProduct> getListHotProduct(@Query("is_hot") int id_hot, @Query("page") int page);

    @GET("/api/products")
    Call<JsonElement> getProductByKeyword(@Query("keyword") String keyword, @Query("page") int page);

    @GET("/api/products")
    Call<BodyProduct> getListNewProduct(@Query("sort_by") String sort_by, @Query("page") int page);

    @GET("/api/auth/me")
    Call<BodyPersonal> getPersonalInformation(@Query("access_token") String accessToken, @Query("with") String with);

    @GET("/api/cart")
    Call<JsonElement> getListProductToCart(@Query("access_token") String access_token);

    @POST("/api/cart")
    Call<JsonElement> updateProductInCart(@Query("access_token") String access_token, @Body RequestBody body);

    @FormUrlEncoded
    @POST("/api/auth/login")
    Call<BodyLogin> login(@Field("username") String username, @Field("password") String password);

    @POST("/api/auth/register")
    Call<Register> register(@Body RequestBody register);

    @POST("/api/auth/resend-confirm-code")
    Call<BodyVerify> reSendConfirmCode(@Query("user_id") String phoneNumber);

    @POST("/api/auth/check-confirm-code")
    Call<JsonElement> checkConfirmCode(@Query("user_id") int id,
                                       @Query("code") String smsCode);
}
