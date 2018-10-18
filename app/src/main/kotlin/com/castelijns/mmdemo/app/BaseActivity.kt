package com.castelijns.mmdemo.app

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override fun getContext() = this
}
