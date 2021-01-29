package gaur.himanshu.august.musicsearch.local.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gaur.himanshu.august.musicsearch.Events
import gaur.himanshu.august.musicsearch.Result
import gaur.himanshu.august.musicsearch.Status
import gaur.himanshu.august.musicsearch.local.ui.search.repository.IApiRepository
import gaur.himanshu.august.musicsearch.remote.response.MusicDetail
import gaur.himanshu.august.musicsearch.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val repository: IApiRepository
) : ViewModel() {


    private val _searchResult = MutableLiveData<Events<Result<List<MusicDetail>>>>()
    val searchResult: LiveData<Events<Result<List<MusicDetail>>>> = _searchResult

    fun getSearched(search: String) = viewModelScope.launch {
        wrapEspressoIdlingResource {
            _searchResult.postValue(Events(Result(Status.LOADING, null, null)))
            _searchResult.postValue(Events(repository.getSearchData(search)))
        }
    }


}