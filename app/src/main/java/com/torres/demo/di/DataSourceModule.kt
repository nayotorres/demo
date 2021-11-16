package com.torres.demo.di

import com.torres.demo.application.App
import com.torres.demo.data.DataSource
import com.torres.demo.data.DataSourceImp
import com.torres.demo.vo.AppDatabase
import org.koin.dsl.module

val datasourceModule = module {
    single<DataSource> { DataSourceImp(appDatabase = AppDatabase.getDatabase(App.appInstance!!.applicationContext)) }
}