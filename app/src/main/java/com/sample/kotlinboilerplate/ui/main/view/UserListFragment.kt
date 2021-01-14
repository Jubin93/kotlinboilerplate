package com.sample.kotlinboilerplate.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.kotlinboilerplate.R
import com.sample.kotlinboilerplate.data.local.entity.User
import com.sample.kotlinboilerplate.ui.main.adapter.MainAdapter
import com.sample.kotlinboilerplate.ui.main.viewmodel.UserListViewModel
import com.sample.kotlinboilerplate.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_list.*
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment @Inject constructor(): Fragment(R.layout.fragment_user_list),
    MainAdapter.onUserClick {

    private val userListViewModel: UserListViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    private val userClick = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MainAdapter(arrayListOf(),userClick)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        userListViewModel.users.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    override fun userClicked(userId: Bundle) {
        Navigation.findNavController(this.requireView()).navigate(
            R.id.user_details,
            userId)
    }
}