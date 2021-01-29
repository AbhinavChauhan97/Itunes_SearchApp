package com.example.itunessearchapp.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.itunessearchapp.dao.SongsDao
import com.example.itunessearchapp.model.Song

@Database(entities = [Song::class],version = 1)
abstract class RoomClient : RoomDatabase() {

    abstract fun songsDao():SongsDao

    companion object{
       fun getRoom(context: Context) =  Room.databaseBuilder(context,
           RoomClient::class.java,
           "songsdb"
       ).build()
    }
}