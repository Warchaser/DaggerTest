package com.warchaser.daggertest.component;


import com.warchaser.daggertest.app.AppApplication;
import com.warchaser.daggertest.module.AppModule;
import com.warchaser.daggertest.module.BuildersModule;
import com.warchaser.daggertest.module.NetWorkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class,
        BuildersModule.class,
        NetWorkModule.class,
        AndroidSupportInjectionModule.class})
interface AppComponent extends AndroidInjector<AppApplication>{

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<AppApplication>{

        @BindsInstance
        public abstract Builder appModule(AppModule appModule);

        @BindsInstance
        public abstract Builder netWorkModule(NetWorkModule netWorkModule);

    }
}
