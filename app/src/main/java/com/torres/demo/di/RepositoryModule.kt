package com.torres.demo.di

import com.torres.demo.domain.DataRepository
import com.torres.demo.domain.DataRepositoryImp
import org.koin.dsl.module

val repositoryModule = module {
    single<DataRepository> { DataRepositoryImp(remoteDataSource = get()) }
}