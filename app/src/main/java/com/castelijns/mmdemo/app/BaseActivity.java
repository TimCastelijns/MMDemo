package com.castelijns.mmdemo.app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    public Context getContext() {
        return this;
    }
}
