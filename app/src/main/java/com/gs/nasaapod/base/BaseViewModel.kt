package com.gs.nasaapod.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {

    val errorLiveData = MutableLiveData<DefaultResponseModel<*>>()

    var navigator: BaseNavigator? = null

    /**
     * [errorObserver] must be attached with [errorLiveData]
     */
    fun setErrorObserver(owner: LifecycleOwner, errorObserver: Observer<DefaultResponseModel<*>>) {
        errorLiveData.observe(owner, errorObserver)
    }


    override fun onCleared() {
        navigator = null
        super.onCleared()
    }

}