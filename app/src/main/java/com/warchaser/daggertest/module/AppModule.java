package com.warchaser.daggertest.module;


import android.content.Context;

import com.warchaser.daggertest.app.AppApplication;


import dagger.Module;

@Module
public class AppModule {

    private Context mContext;

    public AppModule(AppApplication appApplication){
        mContext = appApplication;
    }

//    @Singleton
//    @Provides
//    public Context provideContext(){
//        return mContext;
//    }

//    @Provides
//    @Singleton
//    public Resources provideResources(Context context){
//        return context.getResources();
//    }

}
