package com.castelijns.mmdemo.users

import android.widget.TextView
import com.castelijns.mmdemo.app.BaseListView
import com.castelijns.mmdemo.models.User

interface UsersContract {

    interface View : BaseListView {
        fun showUsers(users: List<User>)
        fun showUserCount(count: Int)
        fun showUserDetail(user: User, tvUsername: TextView, tvEmail: TextView)
    }
}