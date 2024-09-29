package com.kamilk2003.rickmortyapp.services.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kamilk2003.rickmortyapp.objects.models.Character
import com.kamilk2003.rickmortyapp.objects.models.room.RoomCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
    I was wondering whether to implement a mechanism to save all characters in case, for example, we lose the internet connection.
    However, I decided that according to the documentation, only favorite characters should be stored in the database
*/

class AppCRUDService(context: Context) {

    // MARK: - Stored properties

    private val appDao = AppRoomDatabase.getInstance(context).careDao()

    val allCharacters: LiveData<List<Character>?> = appDao
        .getAllCharacters()
        .map { roomCharacters ->
            roomCharacters.map { Character(it) }
        }

    // MARK: - Methods

    fun deleteCharacter(characterId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            appDao.deleteCharacter(characterId)
        }
    }

    fun deleteCharacters(characters: List<Character>) {
        CoroutineScope(Dispatchers.IO).launch {
            for (character in characters) {
                appDao.deleteCharacter(character.id.toInt())
            }
        }
    }

    fun insertCharacter(roomCharacter: RoomCharacter) {
        CoroutineScope(Dispatchers.IO).launch {
            appDao.insertCharacter(roomCharacter)
        }
    }
}