package com.demo.retrofitroom.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "t_repo" ,indices = [androidx.room.Index(value = ["url"], unique = true)])
data class Repo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    @SerializedName("html_url")
    val url: String,
    @ColumnInfo(name = "start_count")
    @SerializedName("stargazers_count")
    val starCount: Int,
    @ColumnInfo(name = "update_time")
    @SerializedName("updated_at")
    val updateTime: String
)
