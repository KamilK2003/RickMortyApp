package com.kamilk2003.rickmortyapp.services.api.mapper

import com.kamilk2003.rickmortyapp.objects.models.Character
import com.kamilk2003.rickmortyapp.objects.models.ResponseCharacter

fun List<ResponseCharacter>?.toDomain(): List<Character>? {
    return this?.map { responseCharacter ->
        Character(
            id = responseCharacter.id,
            name = responseCharacter.name,
            image = responseCharacter.image
        )
    }
}