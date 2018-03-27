package com.warchaser.daggertest.bean;

import com.squareup.moshi.Json;

import java.util.List;

public class MoviesWrapper {

    @Json(name = "results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
