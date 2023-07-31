package com.example.app3do.features.post.post_details.fragment;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Constants;
import com.example.app3do.models.post.DataPost;

public class PostDetailsFragment extends BaseFragment {

    ImageView img_back, img_post;
    TextView txt_title, txt_time, txt_post_content;
    DataPost post;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_post_details;
    }

    @Override
    public void onViewFragment(View view) {
        init(view);
        initView();
        event();
    }

    private void init(View view ) {
        post = (DataPost) getArguments().getSerializable(Constants.POSTS);

        img_back = view.findViewById(R.id.img_back);
        img_post = view.findViewById(R.id.img_post);
        txt_title = view.findViewById(R.id.txt_title);
        txt_time = view.findViewById(R.id.txt_time);
        txt_post_content = view.findViewById(R.id.txt_post_content);
    }

    private void initView() {
        if (post != null) {
            txt_title.setText(post.getTitle());
            String time = "Ngày " + post.getCreatedAt().getDate().split(" ")[0];
            txt_time.setText(time);

            if (post.getImage().length() > 0) {
                Glide.with(this).load(post.getImage()).into(img_post);
            }

            Spanned content = HtmlCompat.fromHtml(post.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY);

            txt_post_content.setText(content);
        }
    }

     private void event() {
        img_back.setOnClickListener( v -> {
            getParentFragmentManager().popBackStack();
        });
     }
}
