package com.sample.kotlinboilerplate.ui.main.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.kotlinboilerplate.data.local.DatabaseHelper
import com.sample.kotlinboilerplate.data.local.entity.User
import com.sample.kotlinboilerplate.data.repository.MainRepository
import com.sample.kotlinboilerplate.utils.NetworkHelper
import com.sample.kotlinboilerplate.utils.Resource
import kotlinx.coroutines.launch

class UserListViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _users = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>>
        get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val usersFromDb = mainRepository.getDBUsers()
                if (usersFromDb.isEmpty()) {
                    Log.d("TAG","Network Call Required")
                    mainRepository.getUsers().let {
                        if (it.isSuccessful) {
                            Log.d("TAG","Network Call Success")
                            val usersFromApi = it.body()
                            val usersToInsertInDB = mutableListOf<User>()

                            for (apiUser in usersFromApi.orEmpty()) {
                                val user = User(
                                    apiUser.id,
                                    apiUser.name,
                                    apiUser.email,
                                    apiUser.avatar
                                )
                                usersToInsertInDB.add(user)
                            }

                            mainRepository.insertAllUsers(usersToInsertInDB)

                            _users.postValue(Resource.success(usersToInsertInDB))

                        } else {
                            Log.d("TAG","Network Call Failed")
                            _users.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }

                } else {
                    Log.d("TAG","Network Call Not Required. Data is in DB")
                    _users.postValue(Resource.success(usersFromDb))
                }

            } else _users.postValue(Resource.error("No internet connection", null))
        }
    }
}