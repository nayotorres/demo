package com.torres.demo.data

import com.torres.demo.data.model.MovieDao
import com.torres.demo.data.model.MovieEntity
import com.torres.demo.vo.Resource

interface DataSource {

    suspend fun requestSearchMovie(nameMovie:String):Resource<MovieEntity>

    suspend fun insertMovie(movieDao: MovieDao)

    suspend fun getMovie():Resource<List<MovieDao>>
}