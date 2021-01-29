package gaur.himanshu.august.musicsearch.local.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import gaur.himanshu.august.musicsearch.Status
import gaur.himanshu.august.musicsearch.databinding.FragmentSearchBinding
import gaur.himanshu.august.musicsearch.remote.response.MusicDetail

@AndroidEntryPoint
class SearchFragment : Fragment() {

    val musicViewModel: MusicViewModel by viewModels()

    private val adapter = SearchAdapter()

    lateinit var _binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        musicViewModel.searchResult.observe(viewLifecycleOwner) {
            when (it.peekContent().status) {
                Status.LOADING -> {
                    showProgress(_binding)
                }
                Status.ERROR -> {
                    hideProgress(_binding)
                    setRecyclerView(it.peekContent().data!!)
                }
                Status.SUCCESS -> {
                    hideProgress(_binding)
                    setRecyclerView(it.peekContent().data!!)
                }
            }
        }



        _binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        musicViewModel.getSearched(query)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showProgress(binding: FragmentSearchBinding) {
        binding.searchProgress.visibility = View.VISIBLE
    }

    private fun hideProgress(binding: FragmentSearchBinding) {
        binding.searchProgress.visibility = View.GONE
    }




    private fun setRecyclerView(list: List<MusicDetail>?) {
        if(list!=null){
            adapter.setContentList(list)
            _binding.recyclerView.layoutManager=GridLayoutManager(requireContext(),2)
            _binding.recyclerView.adapter = adapter
        }

    }


}