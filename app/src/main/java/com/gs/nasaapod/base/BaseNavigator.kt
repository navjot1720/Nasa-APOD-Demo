package com.gs.nasaapod.base


interface BaseNavigator {
    fun showProgressBar()
    fun hideProgressBar()
    fun showToast(msg: String?)
    fun isNetworkAvailable() : Boolean
    fun showNoNetworkError()
    fun goBack()
}