package com.sample.kotlinboilerplate.data.api

import com.sample.kotlinboilerplate.data.model.ApiUser
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<List<ApiUser>>
}