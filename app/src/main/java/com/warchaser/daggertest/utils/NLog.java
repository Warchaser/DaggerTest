package com.warchaser.daggertest.utils;

import android.util.Log;

import com.warchaser.daggertest.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

public class NLog {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private NLog(){

    }

    public static void i(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.e(tag, msg);
        }
    }

    public static void printLine(String tag, boolean isTop){
        if(isTop){
            Log.e(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.e(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    public static void printJson(String tag, String msg, String headString){
        String message;

        try {
            if(msg.startsWith("{")){
                JSONObject jsonObject = new JSONObject(msg);
                //最重要的方法,就一行,返回格式化的json字符串,其中的数字4是缩进字符数
                message = jsonObject.toString(4);
            } else if(msg.startsWith("[")){
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (Exception | Error e){
            message = msg;
            e.printStackTrace();
        }

//        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for(String line : lines){
            NLog.e(tag, line);
        }
//        printLine(tag, false);
    }
}
