package com.sanyar.application

import android.app.Application

object ApplicationContextHolder {
    var application : Application? = null
        private set

    fun init(context : Application?) {
        application = context
    }

}