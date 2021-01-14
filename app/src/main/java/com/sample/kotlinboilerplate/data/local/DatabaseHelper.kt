package com.sample.kotlinboilerplate.data.local

import com.sample.kotlinboilerplate.data.local.entity.User


interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun getUser(userId: Int): User

    suspend fun insertAll(users: List<User>)

}