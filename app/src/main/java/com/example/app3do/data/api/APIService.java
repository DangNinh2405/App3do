package com.example.app3do.data.api;

import com.example.app3do.models.account.BodyLogin;
import com.example.app3do.models.cart.Order;
import com.example.app3do.models.home.BodyCategory;
import com.example.app3do.models.home.BodySlide;
import com.example.app3do.models.marketing.BodyMarketing;
import com.example.app3do.models.order.BodyOrder;
import com.example.app3do.models.personal.AddAddress;
import com.example.app3do.models.personal.BodyPersonal;
import com.example.app3do.models.personal.BodyWardDistrictProvince;
import com.example.app3do.models.personal.DeleteAddress;
import com.example.app3do.models.personal.NewbankPersonal;
import com.example.app3do.models.product.BodyProduct;
import com.example.app3do.models.register.Register;
import com.example.app3do.models.report.BodyReports;
import com.example.app3do.models.verify.BodyVerify;
import com.google.gson.JsonElement;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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

    @GET("api/reports/all")
    Call<BodyReports> getReports(@Query("access_token") String access_token, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/api/provinces")
    Call<BodyWardDistrictProvince> getListProvince(@Query("access_token") String access_token);

    @GET("/api/order")
    Call<JsonElement> getOrders(@Query("with") String with, @Query("access_token") String access_token, @Query("page") int page, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/api/direct-users")
    Call<BodyMarketing> getMemberMarketing(@Query("access_token") String access_token, @Query("page") int page, @Query("sort") String sort, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/api/order/sub-orders")
    Call<JsonElement> getSubOrders(@Query("access_token") String access_token, @Query("page") int page, @Query("start_date") String start_date, @Query("end_date") String end_date);

    @GET("/api/provinces/districts/wards")
    Call<BodyWardDistrictProvince> getListWard(@Query("access_token") String access_token, @Query("district_id") String districtId);

    @GET("/api/provinces/districts")
    Call<BodyWardDistrictProvince> getListDistrict(@Query("access_token") String access_token, @Query("province_id") String provinceId);

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

    @POST("/api/banks")
    Call<JsonElement> addNewBank(@Query("access_token") String accessToken, @Body RequestBody bank);

    @POST("/api/address")
    Call<JsonElement> addNewAddress(@Query("access_token") String accessToken, @Body RequestBody address);

    @POST("/api/order")
    Call<JsonElement> orderPayment(@Query("with") String with, @Query("access_token") String accessToken, @Body RequestBody order);

    @HTTP(method = "DELETE", path = "/api/address/{id}", hasBody = true)
    Call<JsonElement> deleteAddressById(@Path("id") int id, @Query("access_token") String accessToken, @Body DeleteAddress address);

    @HTTP(method = "DELETE", path = "/api/banks/{id}", hasBody = true)
    Call<JsonElement> deleteBankById(@Path("id") int id, @Query("access_token") String accessToken, @Body DeleteAddress address);

    @DELETE("/api/order/{id}")
    Call<JsonElement> deleteOrder(@Path("id") int id, @Query("access_token") String accessToken);

    @DELETE("/api/order/sub-orders/{id}")
    Call<JsonElement> deleteSubOrder(@Path("id") int id, @Query("access_token") String accessToken);

    @PUT("/api/order/{id}")
    Call<JsonElement> putOrder(@Path("id") int id, @Query("access_token") String accessToken, @Body RequestBody order);

    @PUT("/api/order/sub-orders/{id}")
    Call<JsonElement> putSubOrder(@Path("id") int id, @Query("access_token") String accessToken, @Body RequestBody order);
}
