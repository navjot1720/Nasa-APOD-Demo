package com.gs.nasaapod.ui.main.favourites

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.gs.nasaapod.R
import com.gs.nasaapod.base.BaseFragment
import com.gs.nasaapod.data.AppConstants
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity
import com.gs.nasaapod.databinding.FragmentFavouritesBinding
import com.gs.nasaapod.ui.details.PictureDetailActivity
import com.gs.nasaapod.ui.main.MainViewModel
import com.gs.nasaapod.utils.gone
import com.gs.nasaapod.utils.visible


class FavouritesFragment : BaseFragment<FragmentFavouritesBinding, MainViewModel>() {

    private lateinit var mFavouriteList: ArrayList<FavouritePicturesEntity>
    private var mFavouriteListAdapter: FavouritesListAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }


    override fun getLayoutId() = R.layout.fragment_favourites


    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }


    override fun initVariables() {
        mFavouriteList = ArrayList()
    }


    override fun setObservers() {
        viewModel?.getFavouritesList()?.observe(viewLifecycleOwner, {
            mFavouriteList.clear()
            it?.let { list ->
                mFavouriteList.addAll(list)
            }
            mFavouriteListAdapter?.notifyItemRangeChanged(0, mFavouriteList.size)

            if (mFavouriteList.isEmpty()) binding.atvNoDataFound.visible()
            else binding.atvNoDataFound.gone()

        })
    }


    private fun setUpAdapter() {
        mFavouriteListAdapter = FavouritesListAdapter(mFavouriteList, clickCallBack = { date->
            val intent = Intent(requireContext(), PictureDetailActivity::class.java)
            intent.putExtra(AppConstants.KEY_DATE, date)
            startActivity(intent)

        }, removeCallBack = {
            viewModel?.removeFromFavourites(it?.date!!)

            if (mFavouriteList.isEmpty()) binding.atvNoDataFound.visible()
            else binding.atvNoDataFound.gone()

        })

        binding.rvFavourites.adapter = mFavouriteListAdapter
    }


}