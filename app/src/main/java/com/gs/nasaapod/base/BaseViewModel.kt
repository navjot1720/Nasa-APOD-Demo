package com.gs.nasaapod.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {

    val errorLiveData = MutableLiveData<DefaultResponseModel<*>>()

    val loadingLiveData = MutableLiveData<Boolean>()


    /**
     * [errorObserver] must be attached with [errorLiveData]
     */
    fun setErrorObserver(owner: LifecycleOwner, errorObserver: Observer<DefaultResponseModel<*>>) {
        errorLiveData.observe(owner, errorObserver)
    }


    fun setLoading(loading : Boolean){
        loadingLiveData.value = loading
    }

}