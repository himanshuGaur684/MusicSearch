package gaur.himanshu.august.musicsearch.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gaur.himanshu.august.musicsearch.remote.response.MusicDetail

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(list: List<MusicDetail>)

    @Query("SELECT * FROM MusicDetail WHERE artistName LIKE :name ")
    suspend fun getQuery(name: String):List<MusicDetail>

    @Query("SELECT * FROM MusicDetail")
    suspend fun getAllList():List<MusicDetail>


}