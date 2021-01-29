package gaur.himanshu.august.musicsearch.utils


import gaur.himanshu.august.musicsearch.local.ui.search.repository.IApiRepository
import gaur.himanshu.august.musicsearch.remote.response.MusicDetail
import kotlinx.coroutines.runBlocking

fun IApiRepository.insertData(list: List<MusicDetail>) = runBlocking {
    this@insertData.insertList(list)
}

fun IApiRepository.getSearchedList(query:String)= runBlocking {
    this@getSearchedList.getSearchData(query)
}

fun IApiRepository.queryFromDB(query:String)= runBlocking {
    this@queryFromDB.getCachedList(query)
}