package com.demo.retrofitroom.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.demo.retrofitroom.entity.Repo

@Dao
interface RepoDao {

    @Upsert
    suspend fun upsert(repos: List<Repo>)

    @Query("SELECT * FROM t_repo")
    fun getAll(): LiveData<List<Repo>>

    @Query("DELETE FROM t_repo")
    suspend fun clear()
}