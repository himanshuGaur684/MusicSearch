package gaur.himanshu.august.musicsearch.local.ui.search.repository

import gaur.himanshu.august.musicsearch.Result
import gaur.himanshu.august.musicsearch.Status
import gaur.himanshu.august.musicsearch.local.room.MusicDao
import gaur.himanshu.august.musicsearch.remote.ApiService
import gaur.himanshu.august.musicsearch.remote.response.MusicDetail
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class ApiRepository constructor(private val apiService: ApiService,private val musicDao: MusicDao) : IApiRepository {
    override suspend fun getSearchData(searchQuery: String): Result<List<MusicDetail>> {
        return try {
            val response = apiService.getAllTracks(searchQuery)
            return if (response.isSuccessful) {
                val data=response.body()?.results
                if (data != null) {
                    insertList(data)
                }
                Result(Status.SUCCESS, data, "Success")

            } else {
                Result(Status.ERROR, getCachedList(searchQuery), "Error Occurred")
            }
        } catch (e: HttpException) {
            Result(Status.ERROR, getCachedList(searchQuery), e.message())
        } catch (e: IOException) {
            Result(Status.ERROR, getCachedList(searchQuery), e.message)
        }
    }



     override suspend fun getCachedList(searchQuery: String): MutableList<MusicDetail>{
        val d= musicDao.getAllList()
        val returnList= mutableListOf<MusicDetail>()
        d.forEach {

        if (it.artistName?.toLowerCase(Locale.ROOT)?.contains(searchQuery.toLowerCase(Locale.ROOT))!!) {
            returnList.add(it)
        }

        }

        return returnList
    }

    override suspend fun insertList(list: List<MusicDetail>) {
        musicDao.insertList(list)
    }


}