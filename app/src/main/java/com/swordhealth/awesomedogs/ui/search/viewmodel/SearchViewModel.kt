package com.swordhealth.awesomedogs.ui.search.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swordhealth.api.Resource
import com.swordhealth.awesomedogs.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import usecases.FetchBreedsBySearchQueryUseCase

class SearchViewModel(
    private val dispatcher: CoroutineDispatcher,
    private var breedsBySearchQueryUseCase: FetchBreedsBySearchQueryUseCase
) : ViewModel(), SearchViewModelContract.ViewModel {

    override fun invokeAction(action: SearchViewModelContract.Action) {
        when (action) {
            is SearchViewModelContract.Action.SearchBreeds -> searchBreeds(action.query)
            is SearchViewModelContract.Action.NavigateToBreedDetails -> _searchEvents.postValue(SearchViewModelContract.Event.GoToBreedDetail(action.breed))
        }
    }

    private val _searchStates = MediatorLiveData<SearchViewModelContract.State>()
    override val searchState = _searchStates

    private val _searchEvents = SingleLiveEvent<SearchViewModelContract.Event>()
    override val searchEvent = _searchEvents

    private fun searchBreeds(query: String) {
        viewModelScope.launch(dispatcher) {
            breedsBySearchQueryUseCase(query).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _searchStates.postValue(SearchViewModelContract.State.LoadingState)
                    is Resource.Error -> _searchStates.postValue(SearchViewModelContract.State.ErrorState(resource.message))
                    is Resource.Success ->
                        if (resource.data.isNullOrEmpty()) _searchStates.postValue(SearchViewModelContract.State.EmptyState)
                        else _searchStates.postValue(SearchViewModelContract.State.SuccessState(resource.data))
                }
            }
        }
    }
}