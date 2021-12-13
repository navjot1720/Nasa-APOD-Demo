package com.gs.nasaapod.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gs.nasaapod.base.BaseViewModel
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity
import com.gs.nasaapod.data.restapi.ResultWrapper
import kotlinx.coroutines.launch


class MainViewModel : BaseViewModel() {

    private val mRepo = MainRepository()

    val picturesLiveData by lazy { MutableLiveData<FavouritePicturesEntity?>() }

    val ifExistsInFavouritesLiveData by lazy { MutableLiveData<Int>() }


    /**
     * method to get picture for today
     */
    fun getPicturesForToday() {
        navigator?.showProgressBar()
        viewModelScope.launch {
            mRepo.getPicturesForToday().let {
                navigator?.hideProgressBar()

                when (it) {
                    is ResultWrapper.Success -> {
                        picturesLiveData.value = it.response
                    }

                    is ResultWrapper.Error -> {
                        errorLiveData.value = it.response
                    }
                }
            }
        }
    }


    /**
     * method to get picture for single selected day
     */
    fun getPictureByDate(date: String) {
        navigator?.showProgressBar()
        viewModelScope.launch {
            mRepo.getPictureByDate(date).let {
                navigator?.hideProgressBar()

                when (it) {
                    is ResultWrapper.Success -> {
                        picturesLiveData.value = it.response
                    }

                    is ResultWrapper.Error -> {
                        errorLiveData.value = it.response
                    }
                }
            }
        }
    }


    /**
     * method to  save favourites in the db
     */
    fun addToFavourites(entity: FavouritePicturesEntity) {
        entity.compositeId = entity.createCompositeId()
        entity.isFavourite = true
        viewModelScope.launch {
            mRepo.addToFavourites(entity)
        }
    }


    /**
     * method to get saved favourites list from db
     */
    fun getFavouritesList(): LiveData<List<FavouritePicturesEntity>> {
        navigator?.showProgressBar()
        return mRepo.getFavouritesList()
    }


    /**
     *  method to remove favourites from db
     */
    fun removeFromFavourites(compositeId: String) {
        viewModelScope.launch {
            mRepo.removeFromFavourites(compositeId)
        }
    }


    /**
     *  method to check if entity exists in our table
     */
    fun checkIfExistsInFavourites(compositeId: String) {
        navigator?.showProgressBar()
        viewModelScope.launch {
            mRepo.checkIfExists(compositeId).let {
                navigator?.hideProgressBar()
                ifExistsInFavouritesLiveData.postValue(it)
            }
        }
    }

}