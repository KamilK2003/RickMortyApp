package com.kamilk2003.rickmortyapp.objects.models

data class CharactersInfo (
    val info: Info,
    val results: List<ResponseCharacter>
)

data class Info (
    val count: Long,
    val pages: Long,
    val next: String? = null,
    val prev: String? = null
)

data class ResponseCharacter (
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

data class Location (
    val name: String,
    val url: String
)