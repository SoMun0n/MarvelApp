package com.mun0n.marvelapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mun0n.marvelapp.model.CharacterResponse
import com.mun0n.marvelapp.model.Resource
import com.mun0n.marvelapp.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

class MainViewModel(
    private val mainRepository: MainRepository,
) : ViewModel() {

    private val charactersLiveData: MutableLiveData<Resource<CharacterResponse>>

    init {
        Log.d("MainViewModel", "init")
        charactersLiveData = MutableLiveData<Resource<CharacterResponse>>()
        charactersRequest(0)
    }

    fun getCharacters(): LiveData<Resource<CharacterResponse>> {
        return charactersLiveData
    }

    fun charactersRequest(offset: Int = 0) {
        viewModelScope.launch {
            charactersLiveData.value = Resource.loading()
            try {
                val response = mainRepository.loadMarvelCharacters(offset)
                 if (response?.code?.toInt() == HttpURLConnection.HTTP_OK) {
                    Log.d("Http info", "Request OK")
                    charactersLiveData.value = Resource.success(response)
                } else {
                    Log.d("Http info", "Request FAIL")
                    charactersLiveData.value = Resource.error("ERROR", response)
                }
            } catch (e: Exception) {
                charactersLiveData.value = Resource.error(e.toString(), null)
            }
        }

    }

}