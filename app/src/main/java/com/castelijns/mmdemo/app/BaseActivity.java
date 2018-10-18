package com.castelijns.mmdemo.app;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    public Context getContext() {
        return this;
    }
}
