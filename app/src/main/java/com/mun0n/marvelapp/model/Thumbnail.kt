package com.mun0n.marvelapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail (
    val path: String,
    val extension: Extension
)
