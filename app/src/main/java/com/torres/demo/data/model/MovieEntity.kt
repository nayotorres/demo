package com.torres.demo.data.model

import com.google.gson.annotations.SerializedName

class MovieEntity {

    @SerializedName("page")
    var page:Int?=null

    @SerializedName("results")
    var results:ArrayList<Movie>?=null

    class Movie{
        @SerializedName("poster_path")
        var poster_path:String?=null

        @SerializedName("adult")
        var adult:Boolean?=null

        @SerializedName("overview")
        var overview:String?=null

        @SerializedName("release_date")
        var release_date:String?=null

        @SerializedName("genre_ids")
        var genre_ids:ArrayList<Int>?=null

        @SerializedName("id")
        var id:Int?=null

        @SerializedName("original_title")
        var original_title:String?=null

        @SerializedName("original_language")
        var original_language:String?=null

        @SerializedName("title")
        var title:String?=null

        @SerializedName("backdrop_path")
        var backdrop_path:String?=null

        @SerializedName("popularity")
        var popularity:Double?=null

        @SerializedName("vote_count")
        var vote_count:Int?=null

        @SerializedName("video")
        var video:String?=null

        @SerializedName("vote_average")
        var vote_average:Double?=null


    }
}