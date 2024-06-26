package com.emil.effectivemobiletest

import MainViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emil.network.ApiService


class MainViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
