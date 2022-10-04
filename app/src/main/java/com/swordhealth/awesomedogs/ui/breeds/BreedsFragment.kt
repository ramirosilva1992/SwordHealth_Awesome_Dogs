package com.swordhealth.awesomedogs.ui.breeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.swordhealth.awesomedogs.R
import com.swordhealth.awesomedogs.databinding.FragmentBreedsBinding
import com.swordhealth.awesomedogs.ui.BreedsEventHandler
import com.swordhealth.awesomedogs.ui.breeds.adapter.BreedsAdapter
import com.swordhealth.awesomedogs.ui.breeds.viewmodel.BreedsViewModel
import com.swordhealth.awesomedogs.ui.breeds.viewmodel.BreedsViewModelContract
import com.swordhealth.awesomedogs.utils.AppViewModelFactory
import objects.BreedPresentation

class BreedsFragment : Fragment(), BreedsEventHandler {

    private var _binding: FragmentBreedsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: BreedsViewModel by viewModels { AppViewModelFactory.invoke(requireContext().applicationContext) }

    private var breedsRecyclerViewScrollListener: OnScrollListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.breedsState.observe(viewLifecycleOwner, Observer(::renderViewState))
        viewModel.breedsEvent.observe(viewLifecycleOwner, Observer(::performViewEvent))

        viewModel.invokeAction(BreedsViewModelContract.Action.FetchBreeds())
    }

    private fun renderViewState(state: BreedsViewModelContract.State) {
        renderScreenVisibility(state)

        when (state) {
            is BreedsViewModelContract.State.ErrorState -> {
                binding.errorState.text = state.message
                binding.errorStateTryAgain.setOnClickListener { viewModel.invokeAction(BreedsViewModelContract.Action.FetchBreeds(0)) }
            }

            is BreedsViewModelContract.State.SuccessState -> configureRecyclerView(state.breeds)
            else -> Unit
        }
    }

    private fun performViewEvent(event: BreedsViewModelContract.Event) {
        when (event) {
            is BreedsViewModelContract.Event.PaginationLoading -> binding.paginationLoadingState.isVisible = event.isVisible
            is BreedsViewModelContract.Event.PaginationError -> Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
            is BreedsViewModelContract.Event.ChangeViewStyle -> configureViewStyle(event.isListView)
            is BreedsViewModelContract.Event.ChangeAlphabeticalOrder -> configureAlphabeticalOrder(event.isAZSort)
            is BreedsViewModelContract.Event.GoToBreedDetail ->
                BreedsFragmentDirections.actionNavigationBreedsToNavigationBreedDetail(event.id).also { action ->
                    view?.let { Navigation.findNavController(it).navigate(action) }
                }
        }
    }

    private fun renderScreenVisibility(state: BreedsViewModelContract.State) {
        binding.loadingState.isVisible = state is BreedsViewModelContract.State.LoadingState
        binding.emptyState.isVisible = state is BreedsViewModelContract.State.EmptyState
        binding.errorState.isVisible = state is BreedsViewModelContract.State.ErrorState
        binding.errorStateTryAgain.isVisible = state is BreedsViewModelContract.State.ErrorState
        binding.successState.isVisible = state is BreedsViewModelContract.State.SuccessState
    }

    private fun configureViewStyle(isListView: Boolean) {
        val itemCount = binding.successState.adapter?.itemCount
        if (binding.successState.layoutManager != null && (binding.successState.adapter?.itemCount ?: 0) > 0) {
            (binding.successState.adapter as BreedsAdapter).changeViewStyle()
            binding.toolbar.listGridView.background = ContextCompat.getDrawable(requireContext(), if (isListView) R.drawable.ic_grid_view else R.drawable.ic_list_view)
            (binding.successState.layoutManager as GridLayoutManager).spanCount = if (isListView) 1 else 2
            (binding.successState.adapter as BreedsAdapter).notifyItemRangeChanged(0, itemCount ?: 0)
        }

    }

    private fun configureAlphabeticalOrder(isAZSort: Boolean) {
        if ((binding.successState.adapter?.itemCount ?: 0) > 0) {
            binding.toolbar.alphabeticalOrder.background = ContextCompat.getDrawable(requireContext(), if (isAZSort) R.drawable.ic_z_a_sort else R.drawable.ic_a_z_sort)
            val sortedList = (binding.successState.adapter as BreedsAdapter).currentList.sortedBy { it.name }
            (binding.successState.adapter as BreedsAdapter).submitList(if (isAZSort) sortedList else sortedList.reversed()) {
                binding.successState.scrollToPosition(0)
            }
        }
    }

    private fun configureRecyclerView(breeds: List<BreedPresentation>?) {
        if (binding.successState.layoutManager == null)
            binding.successState.layoutManager = GridLayoutManager(requireContext(), 2)

        if (binding.successState.adapter == null)
            binding.successState.adapter = BreedsAdapter(this)

        binding.successState.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        binding.successState.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        configureScrollListener()
        configureToolbar()

        val updatedList = (binding.successState.adapter as BreedsAdapter).currentList + (breeds ?: emptyList())
        (binding.successState.adapter as BreedsAdapter).submitList(updatedList)
    }

    private fun configureScrollListener() {
        if (breedsRecyclerViewScrollListener == null) {
            breedsRecyclerViewScrollListener = object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1))
                        viewModel.invokeAction(
                            BreedsViewModelContract.Action.FetchBreeds(
                                (binding.successState.layoutManager as GridLayoutManager)
                                    .findLastCompletelyVisibleItemPosition()
                            )
                        )
                }
            }
            binding.successState.addOnScrollListener(breedsRecyclerViewScrollListener as OnScrollListener)
        }
    }

    private fun configureToolbar() {
        binding.toolbar.listGridView.setOnClickListener { viewModel.invokeAction(BreedsViewModelContract.Action.ChangeViewStyle) }
        binding.toolbar.alphabeticalOrder.setOnClickListener { viewModel.invokeAction(BreedsViewModelContract.Action.ChangeAlphabeticalOrder) }
    }

    override fun onBreedClick(id: Int) {
        viewModel.invokeAction(BreedsViewModelContract.Action.NavigateToBreedDetails(id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}