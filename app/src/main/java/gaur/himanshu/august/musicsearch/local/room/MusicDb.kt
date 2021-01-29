package gaur.himanshu.august.musicsearch.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gaur.himanshu.august.musicsearch.remote.response.MusicDetail

@Database(entities = [MusicDetail::class], version = 1, exportSchema = false)
abstract class MusicDb : RoomDatabase() {

    companion object {
        fun getInstance(context: Context): MusicDb {
            return Room.databaseBuilder(context, MusicDb::class.java, "music_db").build()
        }
    }
abstract fun getMusicDao():MusicDao

}