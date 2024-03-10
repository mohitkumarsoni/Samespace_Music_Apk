package com.example.samespacemusicapk.ui_layers.sheets.activity_ui.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.samespacemusicapk.mvvm.repository.SongRepository

class MainActivityViewModelProvider(var repository: SongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }
}