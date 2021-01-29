package gaur.himanshu.august.musicsearch.local.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gaur.himanshu.august.musicsearch.local.room.MusicDao
import gaur.himanshu.august.musicsearch.local.room.MusicDb
import gaur.himanshu.august.musicsearch.local.ui.search.repository.ApiRepository
import gaur.himanshu.august.musicsearch.local.ui.search.repository.IApiRepository
import gaur.himanshu.august.musicsearch.remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModules {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideRetroInterface(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    fun provideRepository(apiService: ApiService,musicDao: MusicDao): IApiRepository {
        return ApiRepository(apiService,musicDao)
    }

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): MusicDb{
        return MusicDb.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideMusicDao(musicDb: MusicDb):MusicDao{
        return musicDb.getMusicDao()
    }


}
