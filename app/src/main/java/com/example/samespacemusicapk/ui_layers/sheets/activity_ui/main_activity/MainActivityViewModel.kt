package com.example.samespacemusicapk.ui_layers.sheets.activity_ui.main_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samespacemusicapk.common.SongResource
import com.example.samespacemusicapk.mvvm.repository.SongRepository
import com.example.samespacemusicapk.ui_layers.model.ResponseData
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivityViewModel(val repository: SongRepository) : ViewModel() {

    var songList = MutableLiveData<SongResource<ResponseData>>()

    init {
        getSongList()
    }

    fun getSongList() = viewModelScope.launch {
        songList.postValue(SongResource.Loading())
        val response = repository.getSongList()
        songList.postValue(handleSongResponse(response))
    }

    private fun handleSongResponse(response: Response<ResponseData>): SongResource<ResponseData> {
        if (response.isSuccessful) {
            response.body()?.let {
                return SongResource.Success(it)
            }
        }
        return SongResource.Error(message = response.message())
    }

}