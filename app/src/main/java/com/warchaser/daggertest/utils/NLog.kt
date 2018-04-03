package com.warchaser.daggertest.utils

import android.util.Log

import com.warchaser.daggertest.BuildConfig

import org.json.JSONArray
import org.json.JSONObject

object NLog {

    private val LINE_SEPARATOR : String = System.getProperty("line.separator")

    @JvmStatic
    fun i(tag : String, msg : String){
        if(BuildConfig.DEBUG){
            Log.i(tag, msg)
        }
    }

    @JvmStatic
    fun e (tag : String, msg : String){
        if(BuildConfig.DEBUG){
            Log.e(tag, msg)
        }
    }

    @JvmStatic
    fun printLine(tag : String, isTop : Boolean){
        if(isTop){
            NLog.e(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════")
        } else {
            NLog.e(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════")
        }
    }

    @JvmStatic
    fun printJson(tag : String, msg : String, headString : String){

        var message : String

        try {
            if(msg.startsWith("{")){
                val jsonObject : JSONObject = JSONObject(msg)
                //最重要的方法,就一行,返回格式化的json字符串,其中的数字4是缩进字符数
                message = jsonObject.toString(4)
            } else if(msg.startsWith("[")){
                val jsonArray : JSONArray = JSONArray(msg)
                message = jsonArray.toString(4)
            } else {
                message = msg
            }
        } catch (e : Exception ){
            message = msg
            e.printStackTrace()
        } catch (e : Error){
            message = msg
            e.printStackTrace()
        }

//        printLine(tag, true)
        message = headString + LINE_SEPARATOR + message
        val lines : List<String> = message.split(LINE_SEPARATOR)
        for (line : String in lines){
            NLog.e(tag, line)
        }
//        printLine(tag, false)

    }
}
