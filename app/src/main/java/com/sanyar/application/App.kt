package com.sanyar.application


import android.app.Application


class App : Application() {


    override fun onCreate() {

        super.onCreate()
        ApplicationContextHolder.init(this)

    }

}