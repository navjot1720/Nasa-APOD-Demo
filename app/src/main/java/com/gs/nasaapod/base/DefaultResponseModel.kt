package com.gs.nasaapod.base


data class DefaultResponseModel<T>(
    var apiRequestCode: Int = 0,
    var msg: String? = null,
    var code: Int = 0
)
