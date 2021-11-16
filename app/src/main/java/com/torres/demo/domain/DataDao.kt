package com.torres.demo.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.torres.demo.data.model.MovieDao

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(user: MovieDao)

    @Query("SELECT * FROM moviEntity")
    suspend fun getMovies(): List<MovieDao>
}