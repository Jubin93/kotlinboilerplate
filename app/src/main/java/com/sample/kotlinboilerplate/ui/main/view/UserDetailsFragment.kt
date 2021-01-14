package com.sample.kotlinboilerplate.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.sample.kotlinboilerplate.R
import com.sample.kotlinboilerplate.data.local.entity.User
import com.sample.kotlinboilerplate.ui.main.viewmodel.UserDetailsViewModel
import com.sample.kotlinboilerplate.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_details.*
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailsFragment @Inject constructor(): Fragment(R.layout.fragment_user_details) {

    private val userDetailsViewModel: UserDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("user_id") ?: 0
        Log.d("TAG","Current User Id : "+id)
        /*userDetailsViewModel.data.observe(this, Observer<Int> { item ->
            // Update the UI
        })*/
        setupObserver(id)
    }

    private fun setupObserver(id: Int) {
        userDetailsViewModel.data(id)
        userDetailsViewModel.users.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    detailsProgressBar.visibility = View.GONE
                    it.data?.let { user -> populateUI(user) }
                    imageViewAvatar.visibility = View.VISIBLE
                    textViewUserName.visibility = View.VISIBLE
                    textViewUserEmail.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    detailsProgressBar.visibility = View.VISIBLE
                    imageViewAvatar.visibility = View.GONE
                    textViewUserName.visibility = View.GONE
                    textViewUserEmail.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    detailsProgressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun populateUI(user: User){
        Glide.with(imageViewAvatar.context)
            .load(user.avatar).placeholder(R.drawable.no_image)
            .into(imageViewAvatar)
        textViewUserName.setText(user.name)
        textViewUserEmail.setText(user.email)
    }
}