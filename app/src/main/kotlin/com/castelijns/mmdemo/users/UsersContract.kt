package com.castelijns.mmdemo.users

import android.widget.TextView

import com.castelijns.mmdemo.app.BaseListView
import com.castelijns.mmdemo.app.BasePresenter
import com.castelijns.mmdemo.app.BaseView
import com.castelijns.mmdemo.models.User

interface UsersContract {

    interface View : BaseView, BaseListView {
        fun showUsers(users: List<User>)
        fun showUserCount(count: Int)
        fun showUserDetail(user: User, tvUsername: TextView, tvEmail: TextView)
    }

    interface Presenter : BasePresenter {
        fun onUserClicked(user: User, tvUsername: TextView, tvEmail: TextView)
    }
}