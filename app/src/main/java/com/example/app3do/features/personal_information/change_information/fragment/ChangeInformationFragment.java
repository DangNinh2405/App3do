package com.example.app3do.features.personal_information.change_information.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.example.app3do.R;
import com.example.app3do.base.BaseFragment;
import com.example.app3do.constans.Constants;
import com.example.app3do.features.layout.home.activity.HomeActivity;
import com.example.app3do.features.personal_information.change_information.presenter.ChangeInformationPresenter;
import com.example.app3do.features.personal_information.change_information.view.ChangeInformationView;
import com.example.app3do.models.personal.DataPersonal;
import com.example.app3do.models.personal.DataUploadImage;
import com.example.app3do.models.personal.UpdatePersonal;
import com.example.app3do.until.broadcast.BroadcastUpdateProfile;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChangeInformationFragment extends BaseFragment implements ChangeInformationView {
    private final String MALE = "Nam";
    private View view;
    private final int GENDER_FEMALE = 0;
    private final int GENDER_MALE = 1;
    private int SELECT = -1;
    private final int REQUEST_CODE = 100;
    private ChangeInformationPresenter presenter;
    private HomeActivity homeActivity;
    ImageView img_back, img_front_IC, img_back_IC, img_avatar, img_ic_camera_front, img_ic_camera_back;
    CardView cv_avatar;
    ProgressBar pg_loading_avatar_image, pg_loading_front_image, pg_loading_back_image;
    TextView txt_date_of_birth;
    EditText etxt_name, etxt_identity_number, etxt_email, etxt_address;
    RadioGroup rg_gender;
    Button btn_update;
    DataPersonal personal;
    String urlAvatar, urlFrontImage, urlBackImage;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_information;
    }

    @Override
    public void onViewFragment(View view) {
        this.view = view;
        init(view);
        initView();
        event();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        resultLauncher.unregister();
    }

    private void init(View view) {
        homeActivity = (HomeActivity) getActivity();
        presenter = new ChangeInformationPresenter(this);
        img_back = view.findViewById(R.id.img_back);
        img_front_IC = view.findViewById(R.id.img_front_IC);
        img_back_IC = view.findViewById(R.id.img_back_IC);
        etxt_name = view.findViewById(R.id.etxt_name);
        txt_date_of_birth = view.findViewById(R.id.txt_date_of_birth);
        rg_gender = view.findViewById(R.id.rg_gender);
        etxt_identity_number = view.findViewById(R.id.etxt_identity_number);
        etxt_email = view.findViewById(R.id.etxt_email);
        etxt_address = view.findViewById(R.id.etxt_address);
        img_avatar = view.findViewById(R.id.img_avatar);
        cv_avatar = view.findViewById(R.id.cv_avatar);
        btn_update = view.findViewById(R.id.btn_update);
        pg_loading_avatar_image = view.findViewById(R.id.pg_loading_avatar_image);
        pg_loading_front_image = view.findViewById(R.id.pg_loading_front_image);
        pg_loading_back_image = view.findViewById(R.id.pg_loading_back_image);
        img_ic_camera_front = view.findViewById(R.id.img_ic_camera_front);
        img_ic_camera_back = view.findViewById(R.id.img_ic_camera_back);
    }

    private void initView() {
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        img_back_IC.getLayoutParams().width = (int) Math.round(width * 0.33608695652);
        img_front_IC.getLayoutParams().width = (int) Math.round(width * 0.33608695652);
        img_back_IC.getLayoutParams().height = (int) Math.round(width * 0.22739130434);
        img_front_IC.getLayoutParams().height = (int) Math.round(width * 0.22739130434);
        img_avatar.getLayoutParams().height = (int) Math.round(width * 0.16);
        img_avatar.getLayoutParams().width = (int) Math.round(width * 0.16);

        presenter.getPersonalInformation(homeActivity.getAccessToken());
    }

    private void event() {
        img_back.setOnClickListener( v -> {
            getParentFragmentManager().popBackStack();
        });

        txt_date_of_birth.setOnClickListener( v -> {
            showCalendar();
        });

        img_front_IC.setOnClickListener( v -> {
            getImage(Constants.FRONT_IMAGE);
        });

        img_back_IC.setOnClickListener( v -> {
            getImage(Constants.BACK_IMAGE);
        });

        cv_avatar.setOnClickListener( v -> {
            getImage(Constants.AVATAR_IMAGE);
        });

        btn_update.setOnClickListener( v -> {
            updateProfile();
        });
    }

    private void updateProfile() {
        String name = etxt_name.getText().toString();
        int gender;
        if (rg_gender.getCheckedRadioButtonId() == R.id.rbtn_male) {
            gender = GENDER_MALE;
        } else {
            gender = GENDER_FEMALE;
        }

        String birthDays = txt_date_of_birth.getText().toString();


        String address = etxt_address.getText().toString();
        String identityNumber = etxt_identity_number.getText().toString();
        String email = etxt_email.getText().toString();
        String accessToken = homeActivity.getAccessToken();
        urlAvatar = getNameImage(personal.getAvatar());
        urlFrontImage = getNameImage(personal.getIdentity().getFrontImage());
        urlBackImage = getNameImage(personal.getIdentity().getBackImage());

        UpdatePersonal updatePersonal = new UpdatePersonal(name, urlAvatar, gender, birthDays, address, email, identityNumber, urlFrontImage, urlBackImage, accessToken);

        try {
            presenter.handleUpdateProfile(updatePersonal);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String getNameImage(String url) {
        String list[] = url.split("/");
        return list[list.length - 1];
    }

    private void showCalendar(){
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            String date = "" + dayOfMonth + "/" + (month + 1) + "/" + year;
            txt_date_of_birth.setText(date);
        }, y, m ,d);
        dialog.show();
    }

    private void getImage(int select) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        switch (select) {
            case Constants.AVATAR_IMAGE:
                SELECT = Constants.AVATAR_IMAGE;
                break;
            case Constants.BACK_IMAGE:
                SELECT = Constants.BACK_IMAGE;
                break;
            case Constants.FRONT_IMAGE:
                SELECT = Constants.FRONT_IMAGE;
                break;
        }

        resultLauncher.launch("image/*");
    }

    ActivityResultLauncher<String> resultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            try {
                presenter.uploadImage(getContext(), result, SELECT);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    });

    @Override
    public void sendMessage(String message, boolean isSuccess) {
        if (isSuccess) {
            Intent intent = new Intent(BroadcastUpdateProfile.ACTION_PROFILE);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            getParentFragmentManager().popBackStack();
        }
        Toast.makeText(homeActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showImage(DataUploadImage image, int select) {
        switch (select) {
            case Constants.AVATAR_IMAGE:
                Glide.with(view).load(image.getUrl()).into(img_avatar);
                personal.setAvatar(image.getName());
                break;
            case Constants.BACK_IMAGE:
                Glide.with(view).load(image.getUrl()).into(img_back_IC);
                personal.getIdentity().setBackImage(image.getName());

                break;
            case Constants.FRONT_IMAGE:
                Glide.with(view).load(image.getUrl()).into(img_front_IC);
                personal.getIdentity().setFrontImage(image.getName());
                break;
        }
    }

    @Override
    public void loading(boolean isLoading, int select) {
        switch (select) {
            case Constants.AVATAR_IMAGE:
                if (isLoading) {
                    pg_loading_avatar_image.setVisibility(View.VISIBLE);
                } else {
                    pg_loading_avatar_image.setVisibility(View.GONE);
                }
                break;
            case Constants.BACK_IMAGE:
                if (isLoading) {
                    img_ic_camera_back.setVisibility(View.GONE);
                    pg_loading_back_image.setVisibility(View.VISIBLE);
                } else {
                    img_ic_camera_back.setVisibility(View.VISIBLE);
                    pg_loading_back_image.setVisibility(View.GONE);
                }
                break;
            case Constants.FRONT_IMAGE:
                if (isLoading) {
                    img_ic_camera_front.setVisibility(View.GONE);
                    pg_loading_front_image.setVisibility(View.VISIBLE);
                } else {
                    img_ic_camera_front.setVisibility(View.VISIBLE);
                    pg_loading_front_image.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void createProfile(DataPersonal data) {
        if (data != null) {
            personal = data;
            urlAvatar = data.getAvatar();
            urlBackImage = data.getIdentity().getBackImage();
            urlFrontImage = data.getIdentity().getFrontImage();

            etxt_name.setText(data.getName());
            txt_date_of_birth.setText(data.getBirthdays());
            if (data.getGender().equals(MALE)) {
                rg_gender.check(R.id.rbtn_male);
            } else {
                rg_gender.check(R.id.rbtn_female);
            }

            etxt_identity_number.setText(data.getIdentity().getNumber());
            etxt_email.setText(data.getEmail());
            etxt_address.setText(data.getAddress());
            if (!data.getIdentity().getBackImage().isEmpty()){
                Glide.with(view).load(data.getIdentity().getBackImage()).into(img_back_IC);
            }

            if (!data.getIdentity().getFrontImage().isEmpty()){
                Glide.with(view).load(data.getIdentity().getFrontImage()).into(img_front_IC);
            }
            if (!data.getAvatar().isEmpty()){
                Glide.with(view).load(data.getAvatar()).into(img_avatar);
            }
        }
    }
}
