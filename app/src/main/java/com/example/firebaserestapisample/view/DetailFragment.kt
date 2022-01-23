package com.example.firebaserestapisample.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.firebaserestapisample.R
import com.example.firebaserestapisample.datasource.DataRequestState
import com.example.firebaserestapisample.model.User
import com.example.firebaserestapisample.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.detail_fragment.progressBar
import kotlinx.android.synthetic.main.list_fragment.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = args.user
        tv_name.isEnabled = user == null
        tv_email.isEnabled = user == null
        tv_phone.isEnabled = user == null
        tv_name.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(if (user == null) Color.BLACK else Color.WHITE, BlendModeCompat.SRC_ATOP)
        tv_email.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(if (user == null) Color.BLACK else Color.WHITE, BlendModeCompat.SRC_ATOP)
        tv_phone.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(if (user == null) Color.BLACK else Color.WHITE, BlendModeCompat.SRC_ATOP)
        user?.let {
            tv_name.setText(resources.getString(R.string.name, it.name ?: ""))
            tv_email.setText(resources.getString(R.string.email, it.email ?: ""))
            tv_phone.setText(resources.getString(R.string.phone_number, it.phone))
        }
        button_save.visibility =  if (user == null) View.VISIBLE else  View.GONE
        button_save.setOnClickListener {
            if(tv_name.text.toString().isNotEmpty() && tv_email.text.toString().isNotEmpty() && tv_phone.text.toString().isNotEmpty()) {
                val user = User(
                    tv_name.text.toString(),
                    tv_email.text.toString(),
                    tv_phone.text.toString()
                )
                viewModel.saveUserToFirebase(user)
            }else{
                if(tv_name.text.toString().isEmpty()) {
                    tv_name.error = "Name is mandatory"
                }
                if(tv_email.text.toString().isEmpty()) {
                    tv_email.error = "Email is mandatory"
                }
                if(tv_phone.text.toString().isEmpty()) {
                    tv_phone.error = "Phone Number is mandatory"
                }
            }
        }
        setObserver()
    }

    private fun setObserver() {
        viewModel.dataRequestState.observe(viewLifecycleOwner, { dataRequestState ->
            when (dataRequestState.status) {
                DataRequestState.Status.RUNNING -> {
                    showProgress()
                }
                DataRequestState.Status.SUCCESS -> {
                    Toast.makeText(context,resources.getString(R.string.user_added_successfully), Toast.LENGTH_SHORT).show()
                    hideProgress()
                    Navigation.findNavController(button_save).popBackStack()
                }
                DataRequestState.Status.FAILED -> {
                    Toast.makeText(context,resources.getString(R.string.user_addition_failed), Toast.LENGTH_SHORT).show()
                    hideProgress()
                }
            }
        })
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}