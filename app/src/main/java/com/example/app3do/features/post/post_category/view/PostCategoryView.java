package com.example.app3do.features.post.post_category.view;

import com.example.app3do.models.post.DataPost;

import java.util.List;

public interface PostCategoryView {
    void sendMessage(String message);
    void isLoading(boolean loading);
    void showPostsView(List<DataPost> data);
}
