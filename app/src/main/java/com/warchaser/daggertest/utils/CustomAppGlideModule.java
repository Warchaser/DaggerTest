package com.warchaser.daggertest.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * glide4.x以上，在kotlin下必须实现
 * */
@GlideModule
public class CustomAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //重新设置内存限制,这里10M
        builder.setMemoryCache(new LruResourceCache(10*1024*1024));
    }

    /**
     * 为App注册一个自定义的String类型的BaseGlideUrlLoader
     *
     */
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.append(String.class, InputStream.class, CustomBaseGlideUrlLoader.factory);
    }

    /**
     * 关闭扫描AndroidManifests.xml
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
