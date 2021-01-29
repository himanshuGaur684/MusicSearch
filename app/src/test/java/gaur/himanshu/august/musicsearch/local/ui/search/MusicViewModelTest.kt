package gaur.himanshu.august.musicsearch.local.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import gaur.himanshu.august.musicsearch.CoroutineRule
import gaur.himanshu.august.musicsearch.Status
import gaur.himanshu.august.musicsearch.getOrAwaitValues
import gaur.himanshu.august.musicsearch.local.ui.search.repository.FakeApiRepositoryTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MusicViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    lateinit var musicViewModel: MusicViewModel
    lateinit var fakeRepository: FakeApiRepositoryTest

    @Before
    fun setUp() {
        fakeRepository = FakeApiRepositoryTest()
        musicViewModel = MusicViewModel(fakeRepository)
    }

    @Test
    fun `when our device is connected to the Internet`() {
        // connected to the internet
        fakeRepository.setUpNetwork(true)

        musicViewModel.getSearched("query")

        val value = musicViewModel.searchResult.getOrAwaitValues()
        // check our results
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
        assertThat(value.peekContent().data?.size).isEqualTo(2)

    }

    @Test
    fun `check for data caching in room db`() {
        // network is present , take data and cache in room
        fakeRepository.setUpNetwork(true)
        musicViewModel.getSearched("query")
        // Internet is not present , take data from Room Database
        fakeRepository.setUpNetwork(false)
        musicViewModel.getSearched("query")
        // check our results
        val value = musicViewModel.searchResult.getOrAwaitValues()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
        assertThat(value.peekContent().data?.size).isEqualTo(2)
    }





}