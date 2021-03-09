package com.mun0n.marvelapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Data (
    val offset: Long,
    val limit: Long,
    val total: Long,
    val count: Long,
    val results: List<Result>
)
