package com.gs.nasaapod.base

import com.google.gson.Gson
import com.gs.nasaapod.data.ApiStatusCodes
import com.gs.nasaapod.data.NetworkStatusMessage
import com.gs.nasaapod.data.database.entities.FavouritePicturesEntity
import com.gs.nasaapod.data.restapi.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


open class BaseRepository {

    suspend fun <T> safeApiCall(
        apiRequestCode: Int, dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> T
    ): ResultWrapper<T?> {
        return withContext(dispatcher) {
            try {

                val response = apiCall.invoke()

                if (response is FavouritePicturesEntity || response is List<*>) {
                    ResultWrapper.Success(response)

                } else {
                    val errorResponse = getDefaultErrorObject()
                    errorResponse.apiRequestCode = apiRequestCode
                    errorResponse.msg = NetworkStatusMessage.NETWORK_ERROR.message
                    ResultWrapper.Error(errorResponse)
                }

            } catch (throwable: Throwable) {

                when (throwable) {
                    is SocketTimeoutException, is SocketException, is ConnectException, is TimeoutException, is UnknownHostException -> {
                        val errorResponse = getDefaultErrorObject()
                        errorResponse.apiRequestCode = apiRequestCode
                        errorResponse.msg = NetworkStatusMessage.CONNECTION_ERROR.message
                        ResultWrapper.Error(errorResponse)
                    }

                    is HttpException -> {
                        if (throwable.code() == ApiStatusCodes.NOT_FOUND && throwable.response() is Response<*>){
                            var message = throwable.message()
                            JSONObject(throwable.response()!!.errorBody()?.string()).let { jsonResponse ->
                                if (jsonResponse.has("msg")){
                                    message = jsonResponse.getString("msg")
                                }
                            }
                            val errorResponse = getDefaultErrorObject()
                            errorResponse.apiRequestCode = apiRequestCode
                            errorResponse.code = throwable.code()
                            errorResponse.msg = message
                            ResultWrapper.Error(errorResponse)
                        } else {
                            val errorResponse = getDefaultErrorObject()
                            errorResponse.apiRequestCode = apiRequestCode
                            errorResponse.code = throwable.code()
                            errorResponse.msg = throwable.message() ?: NetworkStatusMessage.NETWORK_ERROR.message
                            ResultWrapper.Error(errorResponse)
                        }
                    }

                    else -> {
                        val errorResponse = getDefaultErrorObject()
                        errorResponse.apiRequestCode = apiRequestCode
                        errorResponse.msg = throwable.localizedMessage ?: NetworkStatusMessage.NETWORK_ERROR.message
                        ResultWrapper.Error(errorResponse)
                    }
                }
            }
        }
    }

    private fun getDefaultErrorObject(): DefaultResponseModel<*> {
        return DefaultResponseModel<Any>()
    }

}