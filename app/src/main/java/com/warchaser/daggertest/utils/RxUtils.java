package com.warchaser.daggertest.utils;

import io.reactivex.disposables.Disposable;

public class RxUtils {

    private RxUtils(){

    }

    /**
     * 取消订阅
     * */
    public static void unsubscribe(Disposable subscriptions){
        if(subscriptions != null && !subscriptions.isDisposed()){
            subscriptions.dispose();
        }
    }

    /**
     * 批量取消订阅
     * */
    public static void unsubscribeBatch(Disposable ... subscriptions){
        for(Disposable subscription : subscriptions){
            unsubscribe(subscription);
        }
    }

}
