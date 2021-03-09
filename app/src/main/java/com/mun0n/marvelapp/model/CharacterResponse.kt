package com.mun0n.marvelapp.model

// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json    = Json(JsonConfiguration.Stable)
// val welcome = json.parse(Welcome.serializer(), jsonString)


import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class CharacterResponse(
    val code: Long,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val etag: String,
    val data: Data,
)


@Serializable
enum class ItemType(val value: String) {
    Cover("cover"),
    Empty(""),
    InteriorStory("interiorStory");

    companion object : KSerializer<ItemType> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.ItemType", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): ItemType =
            when (val value = decoder.decodeString()) {
                "cover" -> Cover
                "" -> Empty
                "interiorStory" -> InteriorStory
                else -> throw IllegalArgumentException("ItemType could not parse: $value")
            }

        override fun serialize(encoder: Encoder, value: ItemType) {
            return encoder.encodeString(value.value)
        }
    }
}


@Serializable
enum class Extension(val value: String) {
    GIF("gif"),
    Jpg("jpg");

    companion object : KSerializer<Extension> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.Extension", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): Extension =
            when (val value = decoder.decodeString()) {
                "gif" -> GIF
                "jpg" -> Jpg
                else -> throw IllegalArgumentException("Extension could not parse: $value")
            }

        override fun serialize(encoder: Encoder, value: Extension) {
            return encoder.encodeString(value.value)
        }
    }
}

@Serializable
enum class URLType(val value: String) {
    Comiclink("comiclink"),
    Detail("detail"),
    Wiki("wiki");

    companion object : KSerializer<URLType> {
        override val descriptor: SerialDescriptor
            get() {
                return PrimitiveSerialDescriptor("quicktype.URLType", PrimitiveKind.STRING)
            }

        override fun deserialize(decoder: Decoder): URLType =
            when (val value = decoder.decodeString()) {
                "comiclink" -> Comiclink
                "detail" -> Detail
                "wiki" -> Wiki
                else -> throw IllegalArgumentException("URLType could not parse: $value")
            }

        override fun serialize(encoder: Encoder, value: URLType) {
            return encoder.encodeString(value.value)
        }
    }
}

