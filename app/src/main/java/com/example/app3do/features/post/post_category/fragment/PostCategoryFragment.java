package com.example.app3do.features.post.post_category.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Constants;
import com.example.app3do.adapter.PostAdapter;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.layout.personal.fragment.PersonalFragment;
import com.example.app3do.features.post.post_category.presenter.PostCategoryPresenter;
import com.example.app3do.features.post.post_category.view.PostCategoryView;
import com.example.app3do.features.post.post_details.fragment.PostDetailsFragment;
import com.example.app3do.models.post.DataPost;
import com.example.app3do.utils.direction.Direction;

import java.util.List;

public class PostCategoryFragment extends BaseFragment implements PostCategoryView {
    private final String TITLE_NEWS = "Tin tức sự kiện";
    private final String TITLE_POLICY = "Chính sách và lợi ích";
    private final String TITLE_VIDEO = "Video đào tạo";
    private final String TITLE_ORIENTATION = "Định hướng và mục tiêu phát triển";
    PostCategoryPresenter presenter;
    HomeActivity homeActivity;
    ImageView img_back;
    TextView txt_title;
    ProgressBar pg_loading;
    RecyclerView rcv_news;
    LinearLayoutManager manager;
    PostAdapter adapter;
    String type;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_event_news;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view) {
        presenter = new PostCategoryPresenter(this);
        homeActivity = (HomeActivity) getActivity();
        type = getArguments().getString(PersonalFragment.KEY);

        img_back = view.findViewById(R.id.img_back);
        pg_loading = view.findViewById(R.id.pg_loading);
        rcv_news = view.findViewById(R.id.rcv_news);
        txt_title = view.findViewById(R.id.txt_title);
    }

    private void initView() {
        if (type != null) {
            switch (type) {
                case PersonalFragment.TYPE_NEWS:
                    txt_title.setText(TITLE_NEWS);
                    break;
                case PersonalFragment.TYPE_POLICY:
                    txt_title.setText(TITLE_POLICY);
                    break;
                case PersonalFragment.TYPE_VIDEO:
                    txt_title.setText(TITLE_VIDEO);
                    break;
                case PersonalFragment.TYPE_ORIENTATION:
                    txt_title.setText(TITLE_ORIENTATION);
                    break;
            }

            presenter.createPosts(homeActivity.getAccessToken(), type);
        }
    }

    private void event() {
        img_back.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        rcv_news.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int countPost = manager.getChildCount();
                    int positionPost = manager.findFirstCompletelyVisibleItemPosition();
                    int totalPost = adapter.getItemCount();

                    if (countPost + 1 <= positionPost + countPost && totalPost - countPost <= positionPost) {
                        if (pg_loading.getVisibility() == View.GONE) {
                            if (type != null) {
                                presenter.createPosts(homeActivity.getAccessToken(), type);
                            }
                        }
                    }
                }
            }
        });
    }

    private void eventRecycleView(PostAdapter postAdapter) {
        postAdapter.setItemOnClickListener((post, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.POSTS, post);

            PostDetailsFragment fragment = new PostDetailsFragment();
            fragment.setArguments(bundle);

            Direction.getInstance().directionToFragment(getParentFragmentManager(), R.id.frame_home, fragment, null, "post_details");
        });
    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isLoading(boolean loading) {
        if (loading) {
            pg_loading.setVisibility(View.VISIBLE);
        } else {
            pg_loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPostsView(List<DataPost> data) {
        if (data != null) {
            int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
            if (adapter == null) {
                manager = new LinearLayoutManager(getContext());
                adapter = new PostAdapter(width, data);
                rcv_news.setAdapter(adapter);
                rcv_news.setLayoutManager(manager);

                eventRecycleView(adapter);
            } else {
                adapter.updateData(data);
                adapter.notifyDataSetChanged();
            }
        }
    }
}