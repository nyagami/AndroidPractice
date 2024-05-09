package com.nyagami.practice.data
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
@Dao
interface SongDAO {
    @Query("SELECT * FROM Song")
    suspend fun getAll(): List<Song>
    @Query("SELECT * FROM Song WHERE name LIKE '%' || :name || '%'")
    suspend fun findByName(name: String): List<Song>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg songs: Song)
    @Delete
    suspend fun delete(song: Song)
}

@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract class SongDb: RoomDatabase() {
    abstract fun songDao(): SongDAO
}
