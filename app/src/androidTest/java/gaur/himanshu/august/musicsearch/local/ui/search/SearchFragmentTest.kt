package gaur.himanshu.august.musicsearch.local.ui.search

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock


@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class SearchFragmentTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    lateinit var musicViewModel: MusicViewModel
    lateinit var fakeRepository: FakeApiRepositoryAndroidTest

    @Before
    fun setUp() {
        hiltRule.inject()
        fakeRepository = FakeApiRepositoryAndroidTest()
        musicViewModel = MusicViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
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
    fun check_our_toolbar_visibility_and_toolbar_title(){
        val navigation=mock(NavController::class.java)
        launchFragmentInHiltContainer<SearchFragment> {
            Navigation.setViewNavController(requireView(),navigation)
        }
        // toolbar visibility
        onView(withId(R.id.toolbar_search)).check(matches(isDisplayed()))
        // check toolbar title
        onView(withText("Music Search")).check(matches(isDisplayed()))
    }

    @Test
    fun test_if_no_query_Insert() = runBlockingTest {
        // check search view is appear or not
        launchActivity()
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))

        // perform click on search view
        onView(withId(R.id.search_view)).perform(click()).perform(typeSearchViewText(null))
        // check Snackbar
        onView(withText("Please enter Query")).check(matches(isDisplayed()))
    }

    @Test
    fun searchView_searching() = runBlockingTest {
        // check search view is appear or not
        launchActivity()
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        // perform click on search view
        onView(withId(R.id.search_view)).perform(click()).perform(typeSearchViewText("query"))
        // set network
        fakeRepository.setUpNetwork(true)
        musicViewModel.getSearched("query")

        onView(withId(R.id.search_progress)).check(matches(isDisplayed()))

        val value = musicViewModel.searchResult.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)

    }




    @Test
    fun searchView_searching_when_Internet_is_not_there() = runBlockingTest {
        // check search view is appear or not
        launchActivity()
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        // perform click on search view
        onView(withId(R.id.search_view)).perform(click()).perform(typeSearchViewText("query"))
        // set network
        fakeRepository.setUpNetwork(false)
        musicViewModel.getSearched("query")

        onView(withId(R.id.search_progress)).check(matches(isDisplayed()))

        val value = musicViewModel.searchResult.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

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