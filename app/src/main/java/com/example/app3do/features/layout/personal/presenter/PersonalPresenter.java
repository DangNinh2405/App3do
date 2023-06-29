package com.example.app3do.features.layout.personal.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.layout.personal.view.PersonalView;
import com.example.app3do.models.personal.BodyPersonal;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class PersonalPresenter extends BasePresenterT<PersonalView> {
    private final String WITH = "banks,wallet,addresses";

    public PersonalPresenter(PersonalView view) {
        super(view);
    }

    public void getPersonalInformation(String accessToken) {
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyPersonal> response = new HandleResponse<BodyPersonal>(apiService.getPersonalInformation(accessToken, WITH)) {
            @Override
            public void isSuccess(BodyPersonal obj) {
                if (isViewAttached()) {
                    getView().renderPersonalInformation(obj.getData());
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
