package gaur.himanshu.august.musicsearch.local.ui.search

import androidx.lifecycle.MutableLiveData
import gaur.himanshu.august.musicsearch.Result
import gaur.himanshu.august.musicsearch.Status
import gaur.himanshu.august.musicsearch.local.ui.search.repository.IApiRepository
import gaur.himanshu.august.musicsearch.remote.response.MusicDetail

class FakeApiRepositoryAndroidTest : IApiRepository {


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
            cachedMusicFiles.addAll(data)
            musicList.addAll(data)
            refreshRemote()
            refreshCache()
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
                "https://is4-ssl.mzstatic.com/image/thumb/Music113/v4/f4/cd/06/f4cd06a4-e750-21ce-ecd4-7eef457997f9/source/100x100bb.jpg",
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
                "https://is5-ssl.mzstatic.com/image/thumb/Music113/v4/96/9d/b9/969db918-e0f5-441a-ebe5-457d32e1cac0/source/100x100bb.jpg",
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