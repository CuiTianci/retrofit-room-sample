package com.demo.retrofitroom.repository

import RetrofitClient
import androidx.lifecycle.LiveData
import com.demo.retrofitroom.dLog
import com.demo.retrofitroom.db.DB
import com.demo.retrofitroom.entity.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

object RepoRepository {

    suspend fun fetchRepos(userName: String) {
        withContext(Dispatchers.IO) {// IO 线程执行耗时任务。
            "Start fetching user repos from service.".dLog()
            try {
                val call = RetrofitClient.githubService.getUserRepos(userName)
                val repos = call.execute().body()
                "Finish fetching user repos from service.".dLog()
                val result = repos ?: emptyList()
                DB.repoDao.upsert(result)
                "Insert or update repos, size:${result.size}".dLog()
            } catch (e: IOException) {
                "Fetch user repos failed for ${e.message}".dLog()
            }
        }
    }

    fun getAll(): LiveData<List<Repo>> {
        return DB.repoDao.getAll()
    }

    suspend fun cleanup() {
        "Cleanup repo table".dLog()
        withContext(Dispatchers.IO) {
            DB.repoDao.clear()
        }
    }
}