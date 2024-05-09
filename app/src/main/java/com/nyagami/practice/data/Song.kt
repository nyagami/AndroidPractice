package com.nyagami.practice.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song (
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo val name: String,
    @ColumnInfo val artist: String,
    @ColumnInfo val album: String,
    @ColumnInfo val genre: String,
    @ColumnInfo val liked: Boolean
)

data class Category(val album: String, val numberOfSongs: Int)