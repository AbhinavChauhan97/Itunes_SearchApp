package com.example.itunessearchapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey val trackId:Int,
    val artistName:String,
    val artworkUrl100:String,
    val trackName:String,
)