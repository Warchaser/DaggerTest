package com.warchaser.daggertest.utils;

public class ImageUtils {

    public static final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w342";

    private ImageUtils(){

    }

    public static String getPosterUri(String posterPath){
        return BASE_POSTER_PATH + posterPath;
    }

}
