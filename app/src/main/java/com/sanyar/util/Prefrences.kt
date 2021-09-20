package com.sanyar.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sanyar.model.Device
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class Prefrences {

    companion object{

        private val KEY_ALL_DEVICE = "devices"
        val NAME = "devicePref"

        fun saveDevice(context: Context, device: Device){
            var list = getAllDevices(context)
            if (list.isNullOrEmpty()){
                list = ArrayList()
            }
            list?.add(device)
            val newList = list?.distinctBy {
                it.id
            }
            val editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit()
            editor.putString(KEY_ALL_DEVICE, Gson().toJson(newList))
            editor.apply()
        }
        fun getAllDevices(context: Context): ArrayList<Device>?{
            val prefrences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
            val gson = Gson()
            val type: Type = object : TypeToken<ArrayList<Device>>() {}.type
            val json: String? = prefrences.getString(KEY_ALL_DEVICE, "")
            return gson.fromJson(json, type)
        }
        fun removeDevices(context: Context, id: String){
            val list = getAllDevices(context)
            val newList = list?.filter {
                it.id != id
            }
            val editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit()
            editor.putString(KEY_ALL_DEVICE, Gson().toJson(newList))
            editor.apply()
        }
    }

}