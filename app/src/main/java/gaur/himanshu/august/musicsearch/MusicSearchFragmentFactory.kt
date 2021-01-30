package gaur.himanshu.august.musicsearch

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import gaur.himanshu.august.musicsearch.local.ui.search.SearchAdapter
import gaur.himanshu.august.musicsearch.local.ui.search.SearchFragment
import javax.inject.Inject

class MusicSearchFragmentFactory @Inject constructor(private val searchAdapter: SearchAdapter) : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
       return when(className){
            SearchFragment::class.java.name->{
                SearchFragment(searchAdapter)
            }
           else->{
               super.instantiate(classLoader, className)
           }
        }
    }
}