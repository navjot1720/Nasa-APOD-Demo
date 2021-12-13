package com.gs.nasaapod

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco


class BaseApplication : Application() {


    companion object {
        lateinit var instance: BaseApplication
        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(this)
    }


    override fun onLowMemory() {
        super.onLowMemory()
        System.gc()
    }

}
