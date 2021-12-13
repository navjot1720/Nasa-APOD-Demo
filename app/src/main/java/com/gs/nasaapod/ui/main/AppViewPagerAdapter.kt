package com.myastrotell.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter


class AppViewPagerAdapter(
    activity: FragmentActivity,
    private val fragmentList: ArrayList<Fragment>,
) : FragmentStateAdapter(activity){


    override fun getItemCount() = fragmentList.size


    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}