package com.example.app3do.features.layout.personal.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.layout.personal.view.PersonalView;
import com.example.app3do.models.personal_information.BodyPersonal;

public class PersonalPresenter extends BasePresenterT<PersonalView> {
    private final String WITH = "banks,wallet,addresses";

    public PersonalPresenter(PersonalView view) {
        super(view);
    }

    public void handlePersonalInformation(String accessToken) {
        APIService apiService = BaseAPIClient.getInstance().getAPIService();
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
