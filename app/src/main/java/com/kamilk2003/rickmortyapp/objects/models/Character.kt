package com.kamilk2003.rickmortyapp.objects.models

import com.kamilk2003.rickmortyapp.objects.models.room.RoomCharacter

data class Character(
    val id: Long,
    val name: String,
    val image: String
) {
    constructor(roomCharacter: RoomCharacter) : this(
        id = roomCharacter.id,
        name = roomCharacter.name,
        image = roomCharacter.image
    )
}