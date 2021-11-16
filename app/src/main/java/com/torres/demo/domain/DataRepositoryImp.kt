package com.torres.demo.domain

import com.torres.demo.data.DataSource
import com.torres.demo.data.model.MovieDao
import com.torres.demo.data.model.MovieEntity
import com.torres.demo.vo.Resource

class DataRepositoryImp(private val remoteDataSource: DataSource):DataRepository {

    override suspend fun requestSearchMovie(nameMovie: String): Resource<MovieEntity> {
        return remoteDataSource.requestSearchMovie(nameMovie)
    }

    override suspend fun insertMovie(movieDao: MovieDao) {
         remoteDataSource.insertMovie(movieDao)
    }

    override suspend fun getMovie(): Resource<List<MovieDao>> {
        return remoteDataSource.getMovie()
    }
}