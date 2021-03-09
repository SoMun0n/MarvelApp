package com.mun0n.marvelapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Stories (
    val available: Long,
    val collectionURI: String,
    val items: List<StoriesItem>,
    val returned: Long
)

@Serializable
data class StoriesItem (
    val resourceURI: String,
    val name: String,
    val type: ItemType
)
