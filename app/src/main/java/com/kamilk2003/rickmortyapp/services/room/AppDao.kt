package com.kamilk2003.rickmortyapp.services.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kamilk2003.rickmortyapp.objects.models.room.RoomCharacter

@Dao
interface AppDao {

    @Query("SELECT * FROM character")
    fun getAllCharacters(): LiveData<List<RoomCharacter>>

    @Query("DELETE FROM character WHERE id = :characterId")
    fun deleteCharacter(characterId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(roomCharacter: RoomCharacter)
}