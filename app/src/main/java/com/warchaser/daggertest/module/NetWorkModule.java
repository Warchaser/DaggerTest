package com.warchaser.daggertest.module;

import com.warchaser.daggertest.net.RequestInterceptor;
import com.warchaser.daggertest.net.TmdbWebService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetWorkModule {

    private static final int CONNECT_TIMEOUT_MS = 45000;
    private static final String BASE_URL = "http://api.themoviedb.org/";

    @Provides
    @Singleton
    public Interceptor provideInterceptor(RequestInterceptor interceptor){
        return interceptor;
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(RequestInterceptor interceptor){

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .build();

    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    TmdbWebService tmdbWebService(Retrofit retrofit){
        return retrofit.create(TmdbWebService.class);
    }

}
