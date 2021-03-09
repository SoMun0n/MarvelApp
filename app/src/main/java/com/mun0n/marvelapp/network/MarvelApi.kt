package com.mun0n.marvelapp.network

import com.mun0n.marvelapp.model.CharacterResponse
import com.mun0n.marvelapp.model.Resource
import com.mun0n.marvelapp.model.SingleCharacterResponse
import retrofit2.Call
import retrofit2.http.*

interface MarvelApi {

    @Headers("Accept: application/json")
    @GET("characters")
    suspend fun fetchMarvelCharacters(
        @QueryMap options: Map<String, String>,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int
    ): CharacterResponse

    @Headers("Accept: application/json")
    @GET("characters/{id}")
    suspend fun fetchMarvelSingleCharacter(
        @Path("id") id: Long,
        @QueryMap options: Map<String, String>
    ): SingleCharacterResponse
}