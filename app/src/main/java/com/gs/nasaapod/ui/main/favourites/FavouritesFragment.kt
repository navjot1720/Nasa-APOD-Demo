package com.gs.nasaapod.ui.main.favourites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.gs.nasaapod.R
import com.gs.nasaapod.base.BaseFragment
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity
import com.gs.nasaapod.databinding.FragmentFavouritesBinding
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

    override fun getBindingVariable() = 0


    override fun initViewModel(): MainViewModel {
        return ViewModelProvider(this)[MainViewModel::class.java]
    }


    override fun initVariables() {
        mFavouriteList = ArrayList()
    }


    override fun setObservers() {

        viewModel?.getFavouritesList()?.observe(viewLifecycleOwner, {
            hideProgressBar()
            mFavouriteList.clear()
            it?.let { list ->
                mFavouriteList.addAll(list)
            }
            mFavouriteListAdapter?.notifyDataSetChanged()

            if (mFavouriteList.isEmpty()) binding.atvNoDataFound.visible()
            else binding.atvNoDataFound.gone()
        })
    }


    private fun setUpAdapter() {
        mFavouriteListAdapter = FavouritesListAdapter(mFavouriteList, clickListener = { position ->

            // removing from the table
            viewModel?.removeFromFavourites(mFavouriteList[position].createCompositeId())

            // removing from the list
            mFavouriteList.removeAt(position)
            mFavouriteListAdapter?.notifyItemRemoved(position)

            if (mFavouriteList.isEmpty()) binding.atvNoDataFound.visible()
            else binding.atvNoDataFound.gone()

        })

        binding.rvFavourites.adapter = mFavouriteListAdapter
    }


    override fun showProgressBar() {
        binding.progress.visible()
    }


    override fun hideProgressBar() {
        binding.progress.gone()
    }

}