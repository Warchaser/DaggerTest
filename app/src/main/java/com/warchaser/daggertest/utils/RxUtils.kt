package com.warchaser.daggertest.utils;

import io.reactivex.disposables.Disposable;

object RxUtils {

    /**
     * 取消订阅
     * */
    @JvmStatic
    fun unsubscribe(subscriptions : Disposable){
        if(subscriptions != null && !subscriptions.isDisposed){
            subscriptions.dispose();
        }
    }

    /**
     * 批量取消订阅
     * */
    @JvmStatic
    fun unsubscribeBatch(vararg subscriptions : Disposable){
        for( subscription : Disposable in subscriptions){
            unsubscribe(subscription);
        }
    }

}
