package com.swordhealth.awesomedogs.ui.search.viewmodel

import androidx.lifecycle.LiveData
import objects.BreedPresentation

class SearchViewModelContract {

    interface ViewModel {
        val searchState: LiveData<State>
        val searchEvent: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class Action {
        data class SearchBreeds(val query: String) : Action()
        data class NavigateToBreedDetails(val breed: BreedPresentation) : Action()
    }

    sealed class State {
        object LoadingState : State()
        object EmptyState : State()
        data class ErrorState(val message: String?) : State()
        data class SuccessState(val breeds: List<BreedPresentation>?) : State()
    }

    sealed class Event {
        data class GoToBreedDetail(val breed: BreedPresentation) : Event()
    }
}