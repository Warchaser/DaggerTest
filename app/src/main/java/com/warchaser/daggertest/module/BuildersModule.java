package com.warchaser.daggertest.module;

import com.warchaser.daggertest.activities.MainActivity;
import com.warchaser.daggertest.app.AppApplication;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = UserModule.class)
    abstract MainActivity bindMainActivityInjectorFactory();


}
