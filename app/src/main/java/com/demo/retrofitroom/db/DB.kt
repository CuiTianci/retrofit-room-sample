package com.demo.retrofitroom.db

import android.content.Context
import androidx.room.Room

object DB {
    private lateinit var database: MyDatabase

    val repoDao: RepoDao get() = database.repoDao()

    fun init(context: Context) {
        database =
            Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java,
                "my_db.db",
            )
                .build()
    }
}