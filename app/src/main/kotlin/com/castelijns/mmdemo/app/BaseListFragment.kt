package com.castelijns.mmdemo.app

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.castelijns.mmdemo.R
import kotlinx.android.synthetic.main.fragment_albums.*

abstract class BaseListFragment : Fragment(), BaseListView {

    protected fun setHeaderText(text: String) {
        tv_header.text = text
    }

    override fun showLoading() {
        pb.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb.visibility = View.GONE
    }

    override fun showError() {
        Toast.makeText(context, R.string.error_retrieving_data, Toast.LENGTH_SHORT)
                .show()
    }
}

interface BaseListView {
    fun showLoading()
    fun hideLoading()
    fun showError()
}
