package com.example.app3do.data.api;

import com.example.app3do.models.account.ErrorBody;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class HandleResponse <T> {
    private Call<T> apiService;

    public abstract void isSuccess(T obj);
    public abstract void isFailed(String error);

    public HandleResponse(Call<T> api) {
        this.apiService = api;
    }

    public void callAPI(){
        apiService.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(response.isSuccessful()) {
                    isSuccess(response.body());
                    return;
                }

                try {
                    String errorBodyString = response.errorBody().string();
                    Gson gson = new Gson();
                    ErrorBody body = gson.fromJson(errorBodyString, ErrorBody.class);
                    String error = "";
                    if(body.getError().lastIndexOf(":") == -1) {
                        error = body.getError();
                    } else {
                        String[] arrError = body.getError().split(":");
                        error = arrError[1];
                    }

                    isFailed(error);
                } catch (IOException e) {
                    isFailed(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (t instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) t).response().errorBody();
                    try {
                        String errorBody = responseBody.string();
                        Gson gson = new Gson();
                        ErrorBody body = gson.fromJson(errorBody, ErrorBody.class);

                        String error = "";
                        if(body.getError().lastIndexOf(":") == -1) {
                            error = body.getError();
                        } else {
                            String[] arrError = body.getError().split(":");
                            error = arrError[1];
                        }

                        isFailed(error);
                    } catch (IOException e) {
                        isFailed(e.getMessage());
                    }
                }
            }
        });
    }
}