package com.swordhealth.awesomedogs.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.swordhealth.awesomedogs.databinding.FragmentSearchBinding
import com.swordhealth.awesomedogs.ui.BreedsEventHandler
import com.swordhealth.awesomedogs.ui.search.adapter.SearchAdapter
import com.swordhealth.awesomedogs.ui.search.viewmodel.SearchViewModel
import com.swordhealth.awesomedogs.ui.search.viewmodel.SearchViewModelContract
import com.swordhealth.awesomedogs.utils.AppViewModelFactory
import objects.BreedPresentation

class SearchFragment : Fragment(), BreedsEventHandler {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels { AppViewModelFactory.invoke(requireContext().applicationContext) }

    private var query: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchState.observe(viewLifecycleOwner, Observer(::renderViewState))
        viewModel.searchEvent.observe(viewLifecycleOwner, Observer(::performViewEvent))

        binding.searchBreed.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                this@SearchFragment.query = query ?: ""
                viewModel.invokeAction(SearchViewModelContract.Action.SearchBreeds(query ?: ""))
                binding.searchBreed.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?) = false
        })
    }

    private fun renderViewState(state: SearchViewModelContract.State) {
        renderScreenVisibility(state)

        when (state) {
            is SearchViewModelContract.State.ErrorState -> {
                binding.errorState.text = state.message
                binding.errorStateTryAgain.setOnClickListener { viewModel.invokeAction(SearchViewModelContract.Action.SearchBreeds(query)) }
            }

            is SearchViewModelContract.State.SuccessState -> configureRecyclerView(state.breeds)
            else -> Unit
        }
    }

    private fun performViewEvent(event: SearchViewModelContract.Event) {
        when (event) {
            is SearchViewModelContract.Event.GoToBreedDetail ->
                SearchFragmentDirections.actionNavigationSearchToNavigationBreedDetail(event.id).also {
                    Navigation.findNavController(binding.root).navigate(it)
                }
        }
    }

    private fun renderScreenVisibility(state: SearchViewModelContract.State) {
        binding.loadingState.isVisible = state is SearchViewModelContract.State.LoadingState
        binding.emptyState.isVisible = state is SearchViewModelContract.State.EmptyState
        binding.errorState.isVisible = state is SearchViewModelContract.State.ErrorState
        binding.errorStateTryAgain.isVisible = state is SearchViewModelContract.State.ErrorState
        binding.successState.isVisible = state is SearchViewModelContract.State.SuccessState
    }

    private fun configureRecyclerView(breeds: List<BreedPresentation>?) {
        if (binding.successState.adapter == null)
            binding.successState.adapter = SearchAdapter(this)

        binding.successState.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        (binding.successState.adapter as SearchAdapter).submitList(breeds)
    }

    override fun onBreedClick(id: Int) {
        viewModel.invokeAction(SearchViewModelContract.Action.NavigateToBreedDetails(id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}