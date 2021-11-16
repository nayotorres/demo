package com.torres.demo.application

import android.app.Application
import com.torres.demo.di.datasourceModule
import com.torres.demo.di.homeViewModel
import com.torres.demo.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application()  {

    override fun onCreate() {
        super.onCreate()

        appInstance = this

        startKoin {
            androidContext(androidContext = this@App)
            modules(datasourceModule)
            modules(repositoryModule)
            modules(homeViewModel)
        }
    }

    companion object {
        var appInstance: App? = null
    }

}