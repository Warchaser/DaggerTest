package com.warchaser.daggertest.moshi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

public class Test {

    public static void main(String args[]){
        Moshi moshi = new Moshi.Builder()
                .add(new Parse2StringAdapter())
                .build();

        String json = "{\n" +
                "    \"state\":0,\n" +
                "    \"data\":[\n" +
                "        \"flight\"\n" +
                "    ]\n" +
                "}";
        JsonAdapter<BaseRespBean> jsonAdapter = moshi.adapter(BaseRespBean.class);

        try {
            BaseRespBean event = jsonAdapter.fromJson(json);
            event.getData();
        } catch (Exception | Error e){
            e.printStackTrace();
        }

    }

}
