package com.gs.nasaapod.ui.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.gs.nasaapod.R
import com.gs.nasaapod.base.BaseActivity
import com.gs.nasaapod.base.BaseViewModel
import com.gs.nasaapod.data.TabNames
import com.gs.nasaapod.databinding.ActivityMainBinding
import com.gs.nasaapod.ui.main.dailypictures.DailyPictureFragment
import com.gs.nasaapod.ui.main.favourites.FavouritesFragment


class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewPager()
    }

    override fun getLayoutId() = R.layout.activity_main


    override fun initViewModel(): Nothing? = null


    override fun initVariables() {
    }

    override fun setObservers() {
        binding.rlToolbar.aivAppTheme.setOnClickListener {
            val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (nightModeFlags) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }


    /**
     * method to set up ViewPager
     */
    private fun setupViewPager() {
        val mFragmentList = ArrayList<Fragment>()
        mFragmentList.add(DailyPictureFragment())
        mFragmentList.add(FavouritesFragment())

        val pagerAdapter = AppViewPagerAdapter(this, mFragmentList)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.offscreenPageLimit = 1

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = TabNames.ASTRONOMY.value
                }
                1 -> {
                    tab.text = TabNames.FAVOURITES.value
                }
            }
        }.attach()
    }


}