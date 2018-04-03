package com.warchaser.daggertest.utils;

object ImageUtils {

    private const val BASE_POSTER_PATH : String = "http://image.tmdb.org/t/p/w342"

    @JvmStatic
    fun getPosterUri(posterPath : String) : String{
        return BASE_POSTER_PATH + posterPath
    }

}
