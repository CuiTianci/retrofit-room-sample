package com.demo.retrofitroom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.retrofitroom.entity.Repo

@Database(entities = [Repo::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}