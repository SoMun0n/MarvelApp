package com.mun0n.marvelapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Comics (
    val available: Long,
    val collectionURI: String,
    val items: List<ComicsItem>,
    val returned: Long
)

@Serializable
data class ComicsItem (
    val resourceURI: String,
    val name: String
)
