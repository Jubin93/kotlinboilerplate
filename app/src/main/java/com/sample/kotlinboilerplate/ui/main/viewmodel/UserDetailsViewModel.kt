package com.sample.kotlinboilerplate.ui.main.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.kotlinboilerplate.data.local.entity.User
import com.sample.kotlinboilerplate.data.repository.MainRepository
import com.sample.kotlinboilerplate.utils.Resource
import kotlinx.coroutines.launch

class UserDetailsViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _user = MutableLiveData<Resource<User>>()
    val userId = MutableLiveData<Int>()

    fun data(id: Int) {
        Log.d("TAG","Current View Model Id : "+id)
        userId.value = id
        fetchUser()
    }

    val users: LiveData<Resource<User>>
        get() = _user

    private fun fetchUser() {
        viewModelScope.launch {
            _user.postValue(Resource.loading(null))
            Log.d("TAG","Current View Model User Id : "+userId.value)
            val id =  userId.value
            val usersFromDb = id?.let { mainRepository.getDBUser(userId = it) }

            _user.postValue(Resource.success(usersFromDb))

        }
    }

}