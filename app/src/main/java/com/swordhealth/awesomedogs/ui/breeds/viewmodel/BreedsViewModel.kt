package com.swordhealth.awesomedogs.ui.breeds.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swordhealth.api.Resource
import com.swordhealth.awesomedogs.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import usecases.FetchBreedsByPageUseCase

class BreedsViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val breedsByPageUseCase: FetchBreedsByPageUseCase
) : ViewModel(), BreedsViewModelContract.ViewModel {

    private var page = 0
    private var totalBreeds = 0
    private var isListView = false
    private var isAZSort = true

    override fun invokeAction(action: BreedsViewModelContract.Action) {
        when (action) {
            is BreedsViewModelContract.Action.FetchBreeds -> getBreeds(action.lastCompletelyVisibleItemPosition)
            is BreedsViewModelContract.Action.NavigateToBreedDetails -> _breedsEvents.postValue(BreedsViewModelContract.Event.GoToBreedDetail(action.id))

            is BreedsViewModelContract.Action.ChangeViewStyle -> {
                isListView = !isListView
                _breedsEvents.postValue(BreedsViewModelContract.Event.ChangeViewStyle(isListView))
            }

            is BreedsViewModelContract.Action.ChangeAlphabeticalOrder -> {
                isAZSort = !isAZSort
                _breedsEvents.postValue(BreedsViewModelContract.Event.ChangeAlphabeticalOrder(isAZSort))
            }
        }
    }

    private val _breedsStates = MediatorLiveData<BreedsViewModelContract.State>()
    override val breedsState = _breedsStates

    private val _breedsEvents = SingleLiveEvent<BreedsViewModelContract.Event>()
    override val breedsEvent = _breedsEvents

    private fun getBreeds(lastCompletelyVisibleItemPosition: Int = 0) {
        viewModelScope.launch(dispatcher) {
            if (shouldFetchNextPage(lastCompletelyVisibleItemPosition)) {
                breedsByPageUseCase(page).collect { resource ->
                    resource.totalBreeds?.also { totalBreeds = it }

                    if (page > 0 && (resource is Resource.Success || resource is Resource.Error))
                        _breedsEvents.postValue(BreedsViewModelContract.Event.PaginationLoading(false))

                    when (resource) {
                        is Resource.Loading ->
                            if (page == 0) _breedsStates.postValue(BreedsViewModelContract.State.LoadingState)
                            else _breedsEvents.postValue(BreedsViewModelContract.Event.PaginationLoading(true))

                        is Resource.Error ->
                            if (page == 0) _breedsStates.postValue(BreedsViewModelContract.State.ErrorState(resource.message))
                            else _breedsEvents.postValue(BreedsViewModelContract.Event.PaginationError(resource.message))

                        is Resource.Success ->
                            if (page == 0 && resource.data.isNullOrEmpty()) _breedsStates.postValue(BreedsViewModelContract.State.EmptyState)
                            else _breedsStates.postValue(BreedsViewModelContract.State.SuccessState(resource.data))
                    }

                    if (resource is Resource.Success)
                        page++
                }
            }
        }
    }

    private fun shouldFetchNextPage(lastCompletelyVisibleItemPosition: Int) = lastCompletelyVisibleItemPosition >= totalBreeds

}