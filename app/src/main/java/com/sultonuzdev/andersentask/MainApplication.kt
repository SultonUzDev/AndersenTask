package com.sultonuzdev.andersentask

import android.app.Application
import com.sultonuzdev.andersentask.di.AppContainer
import com.sultonuzdev.andersentask.di.AppContainerImpl

class MainApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}