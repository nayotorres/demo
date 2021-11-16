package com.torres.demo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviEntity")
data class MovieDao(
    @PrimaryKey
    val uuidMovie:Int,
    @ColumnInfo(name =  "name_movie")
    val nameMovie:String="",
    @ColumnInfo(name =  "overview")
    val overview:String="",
    @ColumnInfo(name =  "poster_path")
    val posterPath:String="",
    @ColumnInfo(name =  "release_date")
    val releaseDate:String=""
)
