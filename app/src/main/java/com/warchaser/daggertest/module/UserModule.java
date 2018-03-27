package com.warchaser.daggertest.module;

import com.warchaser.daggertest.bean.Login;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    Login provideXiaoMingUser(){

        Login login = new Login();
        login.setName("小明");
        login.setPasswd("123");

        return login;
    }

}
