package com.kamilk2003.rickmortyapp.objects.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kamilk2003.rickmortyapp.objects.models.Character

@Entity(tableName = "character")
class RoomCharacter {
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0
    var name: String = ""
    var image: String = ""

    constructor()

    constructor(character: Character) {
        this.id = character.id
        this.name = character.name
        this.image = character.image
    }
}