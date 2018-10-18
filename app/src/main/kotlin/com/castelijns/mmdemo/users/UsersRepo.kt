package com.castelijns.mmdemo.users

import com.castelijns.mmdemo.app.SimpleCache
import com.castelijns.mmdemo.models.User
import com.castelijns.mmdemo.network.ApiManager
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.coroutineScope

object UsersRepo : SimpleCache<User>() {

    private var apiService = ApiManager.service

    suspend fun getAllUsers(): Deferred<List<User>> = coroutineScope {
        async(Dispatchers.IO) {
            cache ?: apiService.getAllUsers().await()
        }
    }
}
