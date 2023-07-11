package com.example.app3do.features.personal_information.change_information.presenter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.loader.content.CursorLoader;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.constans.Constants;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.personal_information.change_information.view.ChangeInformationView;
import com.example.app3do.models.personal.BodyPersonal;
import com.example.app3do.models.personal.BodyUploadImage;
import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.models.personal.UpdatePersonal;
import com.example.app3do.until.PathUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.File;
import java.net.URISyntaxException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ChangeInformationPresenter extends BasePresenterT<ChangeInformationView> {
    private final String WITH = "banks,wallet,addresses";
    private final String SUCCESS = "Cập nhật thành công";
    private final boolean LOADING = true;

    public ChangeInformationPresenter(ChangeInformationView view) {
        super(view);
    }

    public void handleUpdateProfile(UpdatePersonal personal) throws URISyntaxException {
        if (!isViewAttached()) {
            return;
        }
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(personal));

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.updateProfile(WITH, personal.getAccessToken(), body)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (obj.getAsJsonObject().get("data").isJsonObject()) {
                    DataPersonal dataPersonal = gson.fromJson(obj.getAsJsonObject().get("data"), DataPersonal.class);
                    getView().createProfile(dataPersonal);
                }
                getView().sendMessage(SUCCESS, true);
            }

            @Override
            public void isFailed(String error) {
                getView().sendMessage(error, false);
            }
        };

        response.callAPI();

    }

    public void uploadImage(Context mContext, Uri uri, int select) throws URISyntaxException {
        if (!isViewAttached()) {
            return;
        }

        getView().loading(LOADING, select);
        String realPath = PathUtil.getRealPathFromURI(mContext, uri);
        File file = new File(realPath);

        RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part partImage = MultipartBody.Part.createFormData(Constants.IMAGE, file.getName(), imageRequestBody);

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.uploadImage(partImage)) {
            @Override
            public void isSuccess(JsonElement obj) {
                Log.d("JsonElement", obj.toString());
                if (obj.getAsJsonObject().get("data").isJsonObject()) {
                    Gson gson = new Gson();
                    BodyUploadImage body = gson.fromJson(obj, BodyUploadImage.class);
                    getView().showImage(body.getData(), select);
                }
                getView().loading(!LOADING, select);
            }

            @Override
            public void isFailed(String error) {
                getView().sendMessage(error, false);
                getView().loading(!LOADING, select);
            }
        };

        response.callAPI();
    }


    public void getPersonalInformation(String accessToken) {
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyPersonal> response = new HandleResponse<BodyPersonal>(apiService.getPersonalInformation(accessToken, WITH)) {
            @Override
            public void isSuccess(BodyPersonal obj) {
                if (isViewAttached()) {
                    getView().createProfile(obj.getData());
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().sendMessage(error, false);
                }
            }
        };

        response.callAPI();
    }
}
