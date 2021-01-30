package gaur.himanshu.august.musicsearch.local.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import gaur.himanshu.august.musicsearch.R
import gaur.himanshu.august.musicsearch.Status
import gaur.himanshu.august.musicsearch.databinding.FragmentSearchBinding
import javax.inject.Inject

class SearchFragment @Inject constructor(val adapters: SearchAdapter) :
    Fragment(R.layout.fragment_search) {

    lateinit var musicViewModel: MusicViewModel


    lateinit var _binding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerView()

        musicViewModel = ViewModelProvider(requireActivity()).get(MusicViewModel::class.java)

        musicViewModel.searchResult.observe(viewLifecycleOwner) {
            when (it.peekContent().status) {
                Status.LOADING -> {
                    showProgress(_binding)
                }
                Status.ERROR -> {
                    hideProgress(_binding)
                    adapters.setContentList(it.peekContent().data!!)
                }
                Status.SUCCESS -> {
                    hideProgress(_binding)
                    adapters.setContentList(it.peekContent().data!!)
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


    private fun setRecyclerView() {
        _binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adapters
        }


    }


}