package com.warchaser.daggertest.module;

import android.support.annotation.NonNull;
import android.util.Log;

import com.warchaser.daggertest.net.RequestInterceptor;
import com.warchaser.daggertest.net.TmdbWebService;
import com.warchaser.daggertest.utils.Constant;
import com.warchaser.daggertest.utils.NLog;

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

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                if(message.startsWith("{")){
                    NLog.printJson(Constant.COMMON_TAG, message, "result:");
                } else {
                    Log.e(Constant.COMMON_TAG, message);
                }
            }
        });

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

    /**
     * 实现请求单例，因为是在application中调用，所以是全局单例
     * */
    @Singleton
    @Provides
    TmdbWebService tmdbWebService(Retrofit retrofit){
        return retrofit.create(TmdbWebService.class);
    }

}
