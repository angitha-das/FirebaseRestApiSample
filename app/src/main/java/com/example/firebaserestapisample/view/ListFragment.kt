package com.example.firebaserestapisample.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserestapisample.R
import com.example.firebaserestapisample.datasource.DataRequestState
import com.example.firebaserestapisample.model.User
import com.example.firebaserestapisample.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment() {

    private var usersList = ArrayList<User>()
    private var userListAdapter: UserListAdapter? = null
    var navController: NavController? = null
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerViewWithAdapter()
        setObserver()
        button_add.setOnClickListener {
            navController = Navigation.findNavController(button_add)
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(null)
            navController?.navigate(action)
        }
    }

    private fun initializeRecyclerViewWithAdapter(){
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_users.layoutManager = layoutManager
        rv_users.addItemDecoration(DividerItemDecoration(context,layoutManager.orientation))
        userListAdapter = UserListAdapter(usersList, requireContext())
        rv_users.adapter = userListAdapter
    }

    private fun setObserver(){
        viewModel.dataRequestState.observe(viewLifecycleOwner, { dataRequestState ->
           when(dataRequestState.status){
               DataRequestState.Status.RUNNING-> {
                   showProgress()
               }
               DataRequestState.Status.SUCCESS -> {
                   viewModel.fetchUsersFromDB()
                   hideProgress()
               }
               DataRequestState.Status.FAILED -> {
                   hideProgress()
               }
           }
        })

        viewModel.usersLiveData.observe(viewLifecycleOwner,{
            updateUI(it)
        })
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun updateUI(users: List<User>) {
        usersList.clear()
        usersList.addAll(users)
        userListAdapter?.notifyDataSetChanged()
    }
}