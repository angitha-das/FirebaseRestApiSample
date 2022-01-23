package com.example.firebaserestapisample.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.firebaserestapisample.R
import com.example.firebaserestapisample.model.User
import com.example.firebaserestapisample.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*

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
        button_save.visibility =  if (user == null) View.VISIBLE else  View.GONE
        tv_name.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(if (user == null) Color.BLACK else Color.WHITE, BlendModeCompat.SRC_ATOP)
        tv_email.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(if (user == null) Color.BLACK else Color.WHITE, BlendModeCompat.SRC_ATOP)
        tv_phone.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(if (user == null) Color.BLACK else Color.WHITE, BlendModeCompat.SRC_ATOP)
        tv_name.setText(user?.name?:"")
        tv_email.setText(user?.email?:"")
        tv_phone.setText(user?.phone?:"")
    }
}