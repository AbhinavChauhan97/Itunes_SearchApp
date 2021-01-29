package com.example.itunessearchapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.itunessearchapp.model.Song

@Dao
interface SongsDao {

    @Query("SELECT * FROM songs WHERE artistName LIKE :artistName")
    fun getSongsFor(artistName:String):List<Song>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSong(songs: List<Song>)

}