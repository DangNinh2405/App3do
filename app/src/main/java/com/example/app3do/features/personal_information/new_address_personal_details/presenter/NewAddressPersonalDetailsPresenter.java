package com.example.app3do.features.personal_information.new_address_personal_details.presenter;

import android.widget.TextView;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.personal_information.new_address_personal_details.view.NewAddressPersonalDetailsView;
import com.example.app3do.models.personal.AddAddress;
import com.example.app3do.models.personal.BodyWardDistrictProvince;
import com.example.app3do.models.personal.WardDistrictProvincePersonal;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NewAddressPersonalDetailsPresenter extends BasePresenterT<NewAddressPersonalDetailsView> {
    private final String WARD = "WARD";
    private final String DISTRICT = "DISTRICT";
    private final String PROVINCE = "PROVINCE";
    public NewAddressPersonalDetailsPresenter(NewAddressPersonalDetailsView view) {
        super(view);
    }

    public void createSelectProvince(String accessToken, TextView view) {
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyWardDistrictProvince> response = new HandleResponse<BodyWardDistrictProvince>(apiService.getListProvince(accessToken)) {
            @Override
            public void isSuccess(BodyWardDistrictProvince obj) {
                if (isViewAttached()) {
                    getView().renderAddress(obj.getData(), PROVINCE, view);
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().sendMessage(error);
                }
            }
        };

        response.callAPI();
    }

    public void createSelectDistrict(String accessToken, TextView view, WardDistrictProvincePersonal province) {
        String province_id = "undefined";
        if (province != null) {
            province_id = String.valueOf(province.getId());
        }
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyWardDistrictProvince> response = new HandleResponse<BodyWardDistrictProvince>(apiService.getListDistrict(accessToken, province_id)) {
            @Override
            public void isSuccess(BodyWardDistrictProvince obj) {
                if (isViewAttached()) {
                    getView().renderAddress(obj.getData(), DISTRICT, view);
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().sendMessage(error);
                }
            }
        };

        response.callAPI();
    }

    public void createSelectWard(String accessToken, TextView view, WardDistrictProvincePersonal district) {
        String district_id = "undefined";
        if (district != null) {
            district_id = String.valueOf(district.getId());
        }

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyWardDistrictProvince> response = new HandleResponse<BodyWardDistrictProvince>(apiService.getListWard(accessToken, district_id)) {
            @Override
            public void isSuccess(BodyWardDistrictProvince obj) {
                if (isViewAttached()) {
                    getView().renderAddress(obj.getData(), WARD, view);
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().sendMessage(error);
                }
            }
        };

        response.callAPI();
    }

    public void handleAddAddress(AddAddress address){
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(address));

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.addNewAddress(address.getAccessToken(), body)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (isViewAttached()) {
                    if (obj.getAsJsonObject().get("data").isJsonObject()) {
                        getView().sendMessage("Thêm địa chỉ mới thành công.");
                        getView().sendBroadcast();
                    }
                }
            }

            @Override
            public void isFailed(String error) {
                if (isViewAttached()) {
                    getView().sendMessage(error);
                }
            }
        };

        response.callAPI();
    }
}
