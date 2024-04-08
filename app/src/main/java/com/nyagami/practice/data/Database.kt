package com.nyagami.practice.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
object SongEntry: BaseColumns {
    const val TABLE_NAME = "Song"
    const val COLUMN_NAME_NAME = "name"
    const val COLUMN_NAME_ARTIST = "artist"
    const val COLUMN_NAME_ALBUM = "album"
    const val COLUMN_NAME_GENRE = "genre"
    const val COLUMN_NAME_LIKED = "liked"
}

private const val SQL_CREATE_ENTRIES = """
    CREATE TABLE ${SongEntry.TABLE_NAME} (
        ${BaseColumns._ID} INTEGER PRIMARY KEY,
        ${SongEntry.COLUMN_NAME_NAME} TEXT,
        ${SongEntry.COLUMN_NAME_ARTIST} TEXT,
        ${SongEntry.COLUMN_NAME_ALBUM} TEXT,
        ${SongEntry.COLUMN_NAME_GENRE} TEXT,
        ${SongEntry.COLUMN_NAME_LIKED} INTEGER
    )
"""

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${SongEntry.TABLE_NAME}"

class SongDbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "Practice.db"
        const val DATABASE_VERSION = 1
    }

    fun addSong(song: Song){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(SongEntry.COLUMN_NAME_NAME, song.name)
            put(SongEntry.COLUMN_NAME_ARTIST, song.artist)
            put(SongEntry.COLUMN_NAME_ALBUM, song.album)
            put(SongEntry.COLUMN_NAME_GENRE, song.genre)
            put(SongEntry.COLUMN_NAME_LIKED, song.liked)
        }
        db.insert(SongEntry.TABLE_NAME, null, values)
    }
}