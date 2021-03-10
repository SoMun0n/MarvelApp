package com.mun0n.marvelapp.repository

import com.mun0n.marvelapp.BuildConfig
import com.mun0n.marvelapp.model.SingleCharacterResponse
import com.mun0n.marvelapp.network.MarvelApi
import com.mun0n.marvelapp.util.md5
import java.lang.Exception
import java.util.*

class DetailRepository(
    private val marvelApi: MarvelApi,
    date: Date = Date()
) : Repository {


    private var queryMap: Map<String, String> = mapOf(
        "ts" to date.time.toString(),
        "apikey" to BuildConfig.MARVEL_PUBLIC_KEY,
        "hash" to "${date.time}${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_PUBLIC_KEY}".md5()
    )

    suspend fun loadMarvelCharacter(id: Long): SingleCharacterResponse? {
        return try {
            marvelApi.fetchMarvelSingleCharacter(id, queryMap)
        } catch (e: Exception) {
            null
        }
    }

}