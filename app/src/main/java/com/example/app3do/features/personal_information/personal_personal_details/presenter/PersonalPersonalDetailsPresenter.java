package com.example.app3do.features.personal_information.personal_personal_details.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.layout.personal.view.PersonalView;
import com.example.app3do.features.personal_information.personal_personal_details.view.PersonalPersonalDetailsView;
import com.example.app3do.models.personal.BodyPersonal;

public class PersonalPersonalDetailsPresenter extends BasePresenterT<PersonalPersonalDetailsView> {
    public PersonalPersonalDetailsPresenter(PersonalPersonalDetailsView view) {
        super(view);
    }

    private final String WITH = "banks,wallet,addresses";

    public void getPersonalInformation(String accessToken) {
        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<BodyPersonal> response = new HandleResponse<BodyPersonal>(apiService.getPersonalInformation(accessToken, WITH)) {
            @Override
            public void isSuccess(BodyPersonal obj) {
                if (isViewAttached()) {
                    getView().showData(obj.getData());
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
