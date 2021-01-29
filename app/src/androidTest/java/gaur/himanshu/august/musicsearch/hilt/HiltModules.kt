package gaur.himanshu.august.musicsearch.hilt

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gaur.himanshu.august.musicsearch.local.room.MusicDb
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object HiltModules {


    @Named("music_db")
    @Provides
    fun provideDb(@ApplicationContext context: Context): MusicDb {
        return Room.inMemoryDatabaseBuilder(context, MusicDb::class.java).allowMainThreadQueries()
            .build()
    }


}