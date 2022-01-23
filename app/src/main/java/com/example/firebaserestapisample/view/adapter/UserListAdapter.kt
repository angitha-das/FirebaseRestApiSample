package com.example.firebaserestapisample.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserestapisample.R
import com.example.firebaserestapisample.model.User
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.*
import kotlin.collections.ArrayList

class UserListAdapter(private val users: ArrayList<User>,
                      private val context: Context
): RecyclerView.Adapter<UserListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = users[position]
        holder.nameTV.text = user.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } ?: ""
        holder.emailTV.text = user.email ?: ""
        holder.phoneTV.text = user.phone

        holder.itemView.setOnClickListener {
            val navController = Navigation.findNavController(holder.itemView)
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(user)
            navController.navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}

class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nameTV: TextView = view.tv_name
    val emailTV: TextView = view.tv_email
    val phoneTV: TextView = view.tv_phone
}