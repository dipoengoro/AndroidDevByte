package id.dipoengoro.devbyte.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import id.dipoengoro.devbyte.domain.Video
import id.dipoengoro.devbyte.network.Network
import id.dipoengoro.devbyte.network.asDomainModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class DevByteViewModel(application: Application) : AndroidViewModel(application) {
    private val _playlist = MutableLiveData<List<Video>>()
    val playlist: LiveData<List<Video>>
        get() = _playlist

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() = viewModelScope.launch {
        try {
            _playlist.value = Network.devbytes.getPlayList().asDomainModel()
            Timber.i("refreshDataFromNetwork: $_playlist")
        } catch (networkError: IOException) {

        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}