package com.sample.kotlinboilerplate.data.repository

import com.sample.kotlinboilerplate.data.api.ApiHelper
import com.sample.kotlinboilerplate.data.local.DatabaseHelper
import com.sample.kotlinboilerplate.data.local.entity.User
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) {

    suspend fun getUsers() = apiHelper.getUsers()
    suspend fun getDBUsers() = dbHelper.getUsers()
    suspend fun getDBUser(userId: Int) = dbHelper.getUser(userId)
    suspend fun insertAllUsers(users: List<User>) = dbHelper.insertAll(users)

}