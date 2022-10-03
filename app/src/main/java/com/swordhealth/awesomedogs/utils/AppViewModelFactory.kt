package com.swordhealth.awesomedogs.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.swordhealth.awesomedogs.ui.breeds.viewmodel.BreedsViewModel
import com.swordhealth.awesomedogs.ui.search.viewmodel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import repository.BreedsRepository
import usecases.FetchBreedsByPageUseCase
import usecases.FetchBreedsBySearchQueryUseCase

class AppViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(BreedsViewModel::class.java) ->
                    BreedsViewModel(
                        Dispatchers.IO,
                        FetchBreedsByPageUseCase(
                            BreedsRepository(context)
                        )
                    )

                isAssignableFrom(SearchViewModel::class.java) ->
                    SearchViewModel(
                        Dispatchers.IO,
                        FetchBreedsBySearchQueryUseCase(
                            BreedsRepository(context)
                        )
                    )

                else -> throw IllegalArgumentException("Unknown view model class: ${modelClass.name}")
            }
        } as T

    companion object {
        private var INSTANCE: ViewModelProvider.NewInstanceFactory? = null

        operator fun invoke(context: Context) =
            INSTANCE ?: synchronized(AppViewModelFactory::class.java) {
                INSTANCE ?: AppViewModelFactory(context).also {
                    INSTANCE = it
                }
            }
    }

}