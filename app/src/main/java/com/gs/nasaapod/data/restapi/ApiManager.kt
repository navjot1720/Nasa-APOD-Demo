package com.gs.nasaapod.data.restapi

import android.content.Context
import android.net.ConnectivityManager
import com.gs.nasaapod.BaseApplication
import com.gs.nasaapod.BuildConfig
import com.gs.nasaapod.utils.AppUtils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


object ApiManager {

    private const val CONNECTION_TIMEOUT: Long = 15L

    val cacheSize = (5 * 1024 * 1024).toLong()
    val myCache = Cache(BaseApplication.instance.cacheDir, cacheSize)


    val retrofitService by lazy {
        retrofit.create(ApiInterface::class.java)
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient())
        .build()


    private fun getOkHttpClient(): OkHttpClient {
        val okClientBuilder = OkHttpClient.Builder()
            .cache(myCache)

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        okClientBuilder.addInterceptor(getInterceptor())
        okClientBuilder.addInterceptor(httpLoggingInterceptor)

        okClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        okClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        okClientBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        okClientBuilder.callTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)

        return okClientBuilder.build()
    }


    private fun getInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val requestBuilder = chain.request().newBuilder()
                if (isNetworkAvailable())
                    requestBuilder.header("Cache-Control", "public, max-age=" + 1)
                else
                    requestBuilder.removeHeader("Pragma").header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24)

                val headers = requestBuilder.build()
                return chain.proceed(headers)
            }
        }
    }


    fun isNetworkAvailable(): Boolean {
        val connectivity =
            BaseApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivity.activeNetworkInfo
        return (networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected)
    }

}
