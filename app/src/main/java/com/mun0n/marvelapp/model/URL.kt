package com.mun0n.marvelapp.model

import kotlinx.serialization.Serializable

@Serializable
data class URL (
    val type: String,
    val url: String
)
