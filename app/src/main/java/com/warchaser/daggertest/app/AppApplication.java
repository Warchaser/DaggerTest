package com.warchaser.daggertest.app;

import com.warchaser.daggertest.component.DaggerAppComponent;
import com.warchaser.daggertest.module.AppModule;
import com.warchaser.daggertest.module.NetWorkModule;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class AppApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netWorkModule(new NetWorkModule())
                .create(this);
    }

    public AppApplication getApplication(){
        return this;
    }

}
