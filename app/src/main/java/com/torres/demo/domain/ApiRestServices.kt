package com.torres.demo.domain

import com.torres.demo.data.model.MovieEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRestServices {

    @Headers("Content-Type: application/json")
    @GET("search/movie")
    suspend fun searchMovie(@Query("api_key") apikey: String,@Query("query") nameMovie: String,@Query("page") page: Int): Response<MovieEntity>
}