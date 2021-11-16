package com.torres.demo.domain

import com.torres.demo.data.model.MovieDao
import com.torres.demo.data.model.MovieEntity
import com.torres.demo.vo.Resource

interface DataRepository {

    suspend fun requestSearchMovie(nameMovie:String): Resource<MovieEntity>

    suspend fun insertMovie(movieDao: MovieDao)

    suspend fun getMovie():Resource<List<MovieDao>>
}