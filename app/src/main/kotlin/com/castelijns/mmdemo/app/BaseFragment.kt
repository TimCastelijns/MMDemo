package com.castelijns.mmdemo.app

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), BaseView {

    override fun getContext() = activity!!

}
