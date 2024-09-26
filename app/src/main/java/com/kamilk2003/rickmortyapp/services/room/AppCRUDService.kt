package com.kamilk2003.rickmortyapp.services.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kamilk2003.rickmortyapp.objects.models.Character
import com.kamilk2003.rickmortyapp.objects.models.room.RoomCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun insertCharacter(roomCharacter: RoomCharacter) {
        CoroutineScope(Dispatchers.IO).launch {
            appDao.insertCharacter(roomCharacter)
        }
    }
}