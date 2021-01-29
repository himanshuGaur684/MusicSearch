package gaur.himanshu.august.musicsearch.local.ui.search.repository

import androidx.lifecycle.MutableLiveData
import gaur.himanshu.august.musicsearch.Result
import gaur.himanshu.august.musicsearch.Status
import gaur.himanshu.august.musicsearch.remote.response.MusicDetail

class FakeApiRepositoryTest : IApiRepository {


    private val musicList = mutableListOf<MusicDetail>()
    private val _musicList = MutableLiveData<List<MusicDetail>>(musicList)

    private val cachedMusicFiles = mutableListOf<MusicDetail>()
    private val _cachedMusicFiles = MutableLiveData<List<MusicDetail>>(cachedMusicFiles)

    private var isConnected = false

    fun setUpNetwork(boolean: Boolean) {
        isConnected = boolean
    }

    private fun refreshCache() {
        _cachedMusicFiles.postValue(cachedMusicFiles)
    }

    private fun refreshRemote() {
        _musicList.postValue(musicList)
    }


    override suspend fun getSearchData(searchQuery: String): Result<List<MusicDetail>> {

        return if (isConnected) {
            // call the Api and take data
            val data = getApiData()
            musicList.addAll(data)
            refreshRemote()
            // insert it into room cache
            insertList(data)
            Result(Status.SUCCESS, musicList, null)
        } else {
            // search in cache
            Result(Status.ERROR, cachedMusicFiles, null)
        }

    }

    override suspend fun getCachedList(searchQuery: String): List<MusicDetail> {
        return cachedMusicFiles
    }

    override suspend fun insertList(list: List<MusicDetail>) {
        cachedMusicFiles.addAll(list)
        refreshCache()
    }

    private fun getApiData(): List<MusicDetail> {
        return listOf<MusicDetail>(
            MusicDetail(
                key = 1,
                artistId = 11,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            ), MusicDetail(
                key = 2,
                artistId = 12,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        )
    }
}