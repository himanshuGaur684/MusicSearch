package gaur.himanshu.august.musicsearch.local.ui.search

import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import gaur.himanshu.august.musicsearch.*
import gaur.himanshu.august.musicsearch.local.ui.search.repository.IApiRepository
import gaur.himanshu.august.musicsearch.utils.EspressoIdlingResource
import gaur.himanshu.august.musicsearch.utils.getSearchedList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import javax.inject.Inject


@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class SearchFragmentTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var repository: IApiRepository


    lateinit var musicViewModel: MusicViewModel
    lateinit var realMusicViewModel: MusicViewModel
    lateinit var fakeRepository: FakeApiRepositoryAndroidTest

    @Before
    fun setUp() {
        hiltRule.inject()
        fakeRepository = FakeApiRepositoryAndroidTest()
        musicViewModel = MusicViewModel(fakeRepository)
        realMusicViewModel = MusicViewModel(repository)

        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)

    }


    @Test
    fun checkAvalibilityOfSearchView() {
        launchActivity()
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
    }

    @Test
    fun checkAvaliabilityOfRecyclerView() = runBlockingTest {
        launchActivity()
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }


    fun launchActivity(): ActivityScenario<MainActivity>? {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->

        }
        return activityScenario
    }


    @Test
    fun check_our_toolbar_visibility_and_toolbar_title() {
        val navigation = mock(NavController::class.java)
        launchFragmentInHiltContainer<SearchFragment> {
            Navigation.setViewNavController(requireView(), navigation)
        }
        // toolbar visibility
        onView(withId(R.id.toolbar_search)).check(matches(isDisplayed()))
        // check toolbar title
        onView(withText("Music Search")).check(matches(isDisplayed()))
    }



    @Test
    fun searchView_searching() = runBlockingTest {
        // check search view is appear or not
        launchActivity()
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        // perform click on search view
        onView(withId(R.id.search_view)).perform(click()).perform(typeSearchViewText("kis"))
        // set network

    }




    @Test
    fun test_our_recycler_View_visibility() {
        launchActivity()
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun check_value_is_coming_to_recycler()  {
        launchActivity()
        onView(withId(R.id.search_view)).perform(click()).perform(typeSearchViewText("kishore"))
        realMusicViewModel.getSearched("kishore")
        val data = repository.getSearchedList("kishore")
        when (data.status) {
            Status.SUCCESS -> {
                onView(withId(R.id.search_progress)).check(matches(Matchers.not(isDisplayed())))
            }
            Status.LOADING -> {
                onView(withId(R.id.search_progress)).check(matches(isDisplayed()))
            }
            Status.ERROR -> {
                onView(withId(R.id.search_progress)).check(matches(Matchers.not(isDisplayed())))

            }
        }
    }

    fun typeSearchViewText(text: String?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): org.hamcrest.Matcher<View>? {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun getDescription(): String {
                return "search here..."
            }

            override fun perform(uiController: UiController?, view: View) {
                (view as SearchView).setQuery(text, true)
            }
        }
    }


}