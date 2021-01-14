package com.sample.kotlinboilerplate.data.api

import com.sample.kotlinboilerplate.data.model.ApiUser
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<ApiUser>> = apiService.getUsers()

}