package gaur.himanshu.august.musicsearch.hilt

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gaur.himanshu.august.musicsearch.local.room.MusicDb
import gaur.himanshu.august.musicsearch.local.ui.search.FakeApiRepositoryAndroidTest
import gaur.himanshu.august.musicsearch.local.ui.search.repository.IApiRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModulesTest {


    @Named("music_db")
    @Provides
    fun provideDb(@ApplicationContext context: Context): MusicDb {
        return Room.inMemoryDatabaseBuilder(context, MusicDb::class.java).allowMainThreadQueries()
            .build()
    }


}

//
//@InstallIn(SingletonComponent::class)
//@Module
//class HiltModuleThree {
//
//    @Provides
//
//    fun provideRepository():IApiRepository{
//        return FakeApiRepositoryAndroidTest()
//    }
//
//
//}