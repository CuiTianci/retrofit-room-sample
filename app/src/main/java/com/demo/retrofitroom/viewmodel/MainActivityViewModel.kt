package com.demo.retrofitroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.retrofitroom.entity.Repo
import com.demo.retrofitroom.repository.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    fun fetchUserRepos(
        userName: String,
        cleanup: Boolean = false,
        completeBlock: (() -> Unit)? = null
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            if (cleanup) RepoRepository.cleanup()
            RepoRepository.fetchRepos(userName)
            completeBlock?.invoke()
        }
    }

    fun getAll(): LiveData<List<Repo>> {
        return RepoRepository.getAll()
    }
}