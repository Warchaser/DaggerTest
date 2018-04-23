package com.warchaser.daggertest.net;

import com.warchaser.daggertest.bean.MoviesWrapper;
import com.warchaser.daggertest.moshi.BaseRespBean;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface TmdbWebService {

    @GET("3/discover/movie?language=en&sort_by=popularity.desc")
    Observable<MoviesWrapper> popularMovies();

    @GET("3/discover/movie?language=en&sort_by=popularity.desc")
    Observable<BaseRespBean> getPlanes();

}
