package gaur.himanshu.august.musicsearch.local.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gaur.himanshu.august.musicsearch.local.room.MusicDao
import gaur.himanshu.august.musicsearch.local.room.MusicDb
import gaur.himanshu.august.musicsearch.local.ui.search.SearchAdapter
import gaur.himanshu.august.musicsearch.local.ui.search.repository.ApiRepository
import gaur.himanshu.august.musicsearch.local.ui.search.repository.IApiRepository
import gaur.himanshu.august.musicsearch.remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModules {


    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class dbDependency

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class apiService


    @Provides
    @Singleton
    @apiService
    fun provideRetrofit(): ApiService {
        return Retrofit.Builder().baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
    }






    @Singleton
    @Provides
    @dbDependency
    fun provideDb(@ApplicationContext context: Context): MusicDb{
        return MusicDb.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideMusicDao(musicDb: MusicDb):MusicDao{
        return musicDb.getMusicDao()
    }

    @Provides
    fun provideSearchAdapter():SearchAdapter{
        return SearchAdapter()
    }


}


@Module
@InstallIn(SingletonComponent::class)
class HiltModulesTwo{
    @Provides
    fun provideRepository(@HiltModules.apiService apiService: ApiService,@HiltModules.dbDependency musicDb: MusicDb): IApiRepository {
        return ApiRepository(apiService,musicDb.getMusicDao())
    }
}
