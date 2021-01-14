package com.sample.kotlinboilerplate.data.api

import com.sample.kotlinboilerplate.data.model.ApiUser
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<ApiUser>>

}