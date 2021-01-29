package gaur.himanshu.august.musicsearch.local.ui.search.repository

import gaur.himanshu.august.musicsearch.Result
import gaur.himanshu.august.musicsearch.remote.response.MusicDetail

interface IApiRepository {

    suspend fun getSearchData(searchQuery: String): Result<List<MusicDetail>>

    suspend fun getCachedList(searchQuery: String): List<MusicDetail>
    suspend fun insertList(list: List<MusicDetail>)



}