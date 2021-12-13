package com.gs.nasaapod.data.restapi

import com.gs.nasaapod.base.DefaultResponseModel


sealed class ResultWrapper<out T> {
    data class Success<out T>(val response: T?) : ResultWrapper<T?>()
    data class Error(val response: DefaultResponseModel<*>? = null) : ResultWrapper<Nothing>()
}