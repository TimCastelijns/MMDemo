package com.castelijns.mmdemo.users

import android.widget.TextView
import com.castelijns.mmdemo.app.BasePresenter
import com.castelijns.mmdemo.models.User
import kotlinx.coroutines.experimental.launch

class UsersPresenter(
        private val view: UsersContract.View
) : BasePresenter() {

    private val usersRepo = UsersRepo

    override fun start() {
        view.showLoading()

        launch {
            try {
                loadUsers()
            } catch (e: Exception) {
                view.showError()
            } finally {
                view.hideLoading()
            }
        }
    }

    private suspend fun loadUsers() {
        val allUsers = usersRepo.getAllUsers().await()

        val users = allUsers.sortedBy { it.name }
        usersRepo.cacheData(users)

        view.showUsers(users)
        view.showUserCount(users.size)
    }

    fun onUserClicked(user: User, tvUsername: TextView, tvEmail: TextView) {
        this.view.showUserDetail(user, tvUsername, tvEmail)
    }
}
