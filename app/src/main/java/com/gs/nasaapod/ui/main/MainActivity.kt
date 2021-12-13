package com.gs.nasaapod.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.gs.nasaapod.R
import com.gs.nasaapod.base.BaseActivity
import com.gs.nasaapod.base.BaseViewModel
import com.gs.nasaapod.databinding.ActivityMainBinding
import com.gs.nasaapod.ui.main.dailypictures.DailyPictureFragment
import com.gs.nasaapod.ui.main.favourites.FavouritesFragment
import com.myastrotell.adapters.AppViewPagerAdapter


class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewPager()
    }

    override fun getLayoutId() = R.layout.activity_main


    override fun getBindingVariable() = 0


    override fun initViewModel() = null

    override fun initVariables() {

    }

    override fun setObservers() {

    }

    private fun setupViewPager() {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(DailyPictureFragment())
        fragmentList.add(FavouritesFragment())

        val pagerAdapter = AppViewPagerAdapter(this, fragmentList)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Astronomy"
                }
                1 -> {
                    tab.text = "Favourites"
                }
            }
        }.attach()
    }


}