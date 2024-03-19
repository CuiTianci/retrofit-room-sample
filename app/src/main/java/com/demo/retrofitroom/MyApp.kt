package com.demo.retrofitroom

import android.app.Application
import com.demo.retrofitroom.db.DB

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DB.init(this)
        SP.init(this)
    }
}