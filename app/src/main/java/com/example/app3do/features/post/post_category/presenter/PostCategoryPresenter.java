package com.example.app3do.features.post.post_category.presenter;

import com.example.app3do.base.BasePresenterT;
import com.example.app3do.data.api.APIService;
import com.example.app3do.data.api.BaseAPIClient;
import com.example.app3do.data.api.HandleResponse;
import com.example.app3do.features.post.post_category.view.PostCategoryView;
import com.example.app3do.models.post.BodyPost;
import com.example.app3do.models.post.DataPost;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class PostCategoryPresenter extends BasePresenterT<PostCategoryView> {
    public PostCategoryPresenter(PostCategoryView view) {
        super(view);
        init();
    }
    private int page;
    private int totalPage;
    private List<DataPost> list;

    public void init(){
        this.page = 1;
        this.totalPage = 0;
        this.list = new ArrayList<>();
    }

    public void createPosts(String accessToken, String type) {
        if (totalPage != 0 && totalPage < page) {
            return;
        }

        if (!isViewAttached()) {
            return;
        }

        getView().isLoading(true);

        APIService apiService = BaseAPIClient.getInstance().getAPIService(BaseAPIClient.API_SERVICE, APIService.class);
        HandleResponse<JsonElement> response = new HandleResponse<JsonElement>(apiService.getPosts(type, accessToken, page)) {
            @Override
            public void isSuccess(JsonElement obj) {
                if (obj.getAsJsonObject().get("meta").isJsonObject()) {
                    Gson gson = new Gson();
                    BodyPost body = gson.fromJson(obj, BodyPost.class);

                    totalPage = body.getMeta().getPagination().getTotal_pages();
                    if (page <= totalPage) {
                        if (list == null) {
                            list = new ArrayList<>();
                        }

                        list.addAll(body.getData());
                        getView().showPostsView(list);
                        page++;
                        getView().isLoading(false);
                    }
                }
            }

            @Override
            public void isFailed(String error) {
                getView().sendMessage(error);
            }
        };

        response.callAPI();
    }
}
