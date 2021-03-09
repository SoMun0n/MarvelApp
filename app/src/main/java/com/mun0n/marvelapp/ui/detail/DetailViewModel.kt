package com.mun0n.marvelapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mun0n.marvelapp.model.Resource
import com.mun0n.marvelapp.model.SingleCharacterResponse
import com.mun0n.marvelapp.repository.DetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.HttpURLConnection

class DetailViewModel(
    private val detailRepository: DetailRepository,
    private val id: String
) : ViewModel() {

    private val characterLiveData: MutableLiveData<Resource<SingleCharacterResponse>>

    init {
        Log.d("MainViewModel", "init")
        characterLiveData = MutableLiveData<Resource<SingleCharacterResponse>>()
        characterRequest()
    }

    fun getCharacterData(): LiveData<Resource<SingleCharacterResponse>> {
        return characterLiveData
    }

    private fun characterRequest() {
        viewModelScope.launch {
            characterLiveData.value = Resource.loading()
            try {
                val response = detailRepository.loadMarvelCharacter(id.toLong())
                if (response?.code?.toInt() == HttpURLConnection.HTTP_OK) {
                    Log.d("Http info", "Character request OK")
                    characterLiveData.value = Resource.success(response)
                } else {
                    Log.d("Http info", "Character request FAIL")
                    characterLiveData.value = Resource.error("ERROR", response)
                }
            } catch (e: Exception) {
                characterLiveData.value = Resource.error(e.toString(), null)
            }

        }

    }

}