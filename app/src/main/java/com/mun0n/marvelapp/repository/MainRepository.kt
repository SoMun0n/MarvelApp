package com.mun0n.marvelapp.repository

import com.mun0n.marvelapp.BuildConfig
import com.mun0n.marvelapp.model.CharacterResponse
import com.mun0n.marvelapp.network.MarvelApi
import com.mun0n.marvelapp.util.md5
import java.lang.Exception
import java.util.*


class MainRepository(private val marvelApi: MarvelApi) : Repository {

    private val timestamp = Date().time

    private var queryMap: Map<String, String> = mapOf(
        "ts" to timestamp.toString(),
        "apikey" to BuildConfig.MARVEL_PUBLIC_KEY,
        "hash" to "${timestamp}${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_PUBLIC_KEY}".md5()
    )

    suspend fun loadMarvelCharacters(offset: Int): CharacterResponse? {
        return try {
            marvelApi.fetchMarvelCharacters(queryMap, offset = offset)
        } catch (e: Exception) {
            null
        }
    }

}