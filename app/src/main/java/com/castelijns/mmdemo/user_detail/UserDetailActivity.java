package com.castelijns.mmdemo.user_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.castelijns.mmdemo.R;
import com.castelijns.mmdemo.app.BaseActivity;

import butterknife.ButterKnife;

public class UserDetailActivity extends BaseActivity implements UserDetailContract.View {

    private UserDetailContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        presenter = new UserDetailPresenter(this);
    }

}
