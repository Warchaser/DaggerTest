package com.warchaser.daggertest.moshi;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class Parse2StringAdapter {

    @ToJson
    String toJson(@Parse2String String objectOrArray) {
        return objectOrArray;
    }

    @FromJson
    @Parse2String String fromJson(Object rgb) {
        return String.valueOf(rgb);
    }

}
