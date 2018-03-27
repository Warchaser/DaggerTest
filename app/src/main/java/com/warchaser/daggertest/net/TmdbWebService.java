package com.warchaser.daggertest.net;

import com.warchaser.daggertest.bean.MoviesWrapper;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TmdbWebService {

    @GET("3/discover/movie?language=en&sort_by=popularity.desc")
    Observable<MoviesWrapper> popularMovies();

}
