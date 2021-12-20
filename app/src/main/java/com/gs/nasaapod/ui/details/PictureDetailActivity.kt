package com.gs.nasaapod.ui.details

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.gs.nasaapod.R
import com.gs.nasaapod.base.BaseActivity
import com.gs.nasaapod.data.AppConstants
import com.gs.nasaapod.databinding.FragmentDailyPicturesBinding
import com.gs.nasaapod.ui.main.MainViewModel
import com.gs.nasaapod.utils.gone


class PictureDetailActivity : BaseActivity<FragmentDailyPicturesBinding, MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.groupHide.gone()
        binding.viewModel = viewModel
        // setting the details to be shown
        val date  = intent.getStringExtra(AppConstants.KEY_DATE)
        viewModel?.getPictureDetailsByDate(date!!)
    }


    override fun getLayoutId() = R.layout.fragment_daily_pictures


    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(this)[MainViewModel::class.java]
    }


    override fun initVariables() {
    }

    override fun setObservers() {
    }

}