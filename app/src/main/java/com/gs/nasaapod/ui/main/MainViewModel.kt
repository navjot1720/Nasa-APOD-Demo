package com.gs.nasaapod.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gs.nasaapod.base.BaseViewModel
import com.gs.nasaapod.data.ApiStatusCodes
import com.gs.nasaapod.data.MediaType
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity
import com.gs.nasaapod.data.restapi.ResultWrapper
import com.gs.nasaapod.utils.SingleLiveEvent
import kotlinx.coroutines.launch


class MainViewModel : BaseViewModel() {

    private val mRepo by lazy { MainRepository() }

    // LiveData to show/hide UI
    val isDataInitialized by lazy { MutableLiveData<Boolean>() }

    // LiveData to manage retry option for no internet and update UI
    val showRetry by lazy { MutableLiveData<Boolean>() }

    // LiveData to store astronomy picture details and update UI
    val apodLiveData by lazy { MutableLiveData<FavouritePicturesEntity>() }

    // LiveData to show datePicker to select the date
    val selectDateLiveData by lazy { SingleLiveEvent<Any?>() }

    // LiveData to handle click on video thumbnail
    val videoClickLiveData by lazy { SingleLiveEvent<String?>() }


    /**
     * method to get picture for today
     */
    fun getPicturesForToday() {
        // checking if liveData already holds value
        // then return same value on orientation change
        // else get data from api
        if (apodLiveData.value != null) {
            showRetry.value = false
            isDataInitialized.value = true
            apodLiveData.value = apodLiveData.value

        } else {
            setLoading(true)
            showRetry.value = false

            viewModelScope.launch {

                mRepo.getPictureForToday().let {
                    setLoading(false)

                    when (it) {
                        is ResultWrapper.Success -> {
                            it.response?.let { model ->
                                apodLiveData.value = model
                                isDataInitialized.value = true
                                checkIfExistsInFavourites(model.date!!)
                            }
                        }

                        is ResultWrapper.Error -> {
                            if (it.response?.code == ApiStatusCodes.TIMEOUT_ERROR) {
                                isDataInitialized.value = false
                                showRetry.value = true
                            }
                            errorLiveData.value = it.response
                        }
                    }
                }
            }
        }
    }


    /**
     * method to get picture for single selected day
     */
    fun getPictureByDate(date: String) {
        setLoading(true)
        showRetry.value = false
        isDataInitialized.value = true

        viewModelScope.launch {
            mRepo.getPictureByDate(date).let {
                setLoading(false)

                when (it) {
                    is ResultWrapper.Success -> {
                        it.response?.let { model ->
                            apodLiveData.value = model
                            apodLiveData.value = model
                            isDataInitialized.value = true
                            checkIfExistsInFavourites(model.date!!)
                        }
                    }

                    is ResultWrapper.Error -> {
                        if (it.response?.code == ApiStatusCodes.TIMEOUT_ERROR) {
                            isDataInitialized.value = false
                            showRetry.value = true
                        }
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
        entity.isFavourite = true
        viewModelScope.launch {
            mRepo.addToFavourites(entity)
        }
    }


    /**
     * method to get saved favourites list from db
     */
    fun getFavouritesList(): LiveData<List<FavouritePicturesEntity>> {
        return mRepo.getFavouritesList()
    }


    /**
     * method to get picture details by date from db
     */
    fun getPictureDetailsByDate(date:String) {
        viewModelScope.launch {
            mRepo.getPictureDetailsByDate(date).let {
                apodLiveData.value = it
                isDataInitialized.value = true
            }
        }
    }


    /**
     *  method to remove favourites from db
     */
    fun removeFromFavourites(date: String) {
        viewModelScope.launch {
            mRepo.removeFromFavourites(date)
        }
    }


    /**
     *  method to check if entity exists in our table
     */
    private fun checkIfExistsInFavourites(date: String) {
        viewModelScope.launch {
            mRepo.checkIfExists(date).let {
                apodLiveData.value?.let { model ->
                    model.isFavourite = it != 0
                    apodLiveData.value = model
                }
            }
        }
    }


    /**
     *  onClick handle on select date
     */
    fun onDateSelect() {
        selectDateLiveData.value = Any()
    }


    /**
     *  onClick handle on retry
     */
    fun onRetryClicked() {
        getPicturesForToday()
    }


    /**
     *  onClick handle on add/remove favourites
     */
    fun addRemoveFavourites() {
        apodLiveData.value?.let {
            if (it.isFavourite) {
                removeFromFavourites(it.date!!)
                it.isFavourite = false
            } else {
                addToFavourites(it)
                it.isFavourite = true
            }
            apodLiveData.value = it
        }
    }


    /**
     *  onClick handle on video
     */
    fun onImageClicked(){
        apodLiveData.value?.let {
            if (it.media_type.equals(MediaType.VIDEO.value, true)){
                videoClickLiveData.value = it.url
            }
        }
    }

}