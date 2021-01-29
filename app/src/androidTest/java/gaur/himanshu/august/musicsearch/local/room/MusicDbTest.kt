package gaur.himanshu.august.musicsearch.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import gaur.himanshu.august.musicsearch.remote.response.MusicDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@ExperimentalCoroutinesApi
@MediumTest
class MusicDbTest {


    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("music_db")
    lateinit var musicDb: MusicDb

    lateinit var musicDao: MusicDao

    @Before
    fun setUp() {
        hiltAndroidRule.inject()

        musicDao = musicDb.getMusicDao()

    }


    @After
    fun destroy() {
        musicDb.close()
    }

    @Test
    fun insertTesting() = runBlockingTest {
        musicDao.insertList(provideList())
        val data = musicDao.getAllList()
        assertThat(data[0].key).isEqualTo(1)
    }


    @Test
    fun queryTesting() = runBlockingTest {
        musicDao.insertList(provideList())
        val data = musicDao.getQuery("kishore kumar")
        assertThat(data[0].artistName).isEqualTo("kishore kumar")
    }

    // returning data
    private fun provideList(): List<MusicDetail> {
        return listOf<MusicDetail>(
            MusicDetail(
                key = 1,
                artistId = 11,
                "kishore kumar",
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