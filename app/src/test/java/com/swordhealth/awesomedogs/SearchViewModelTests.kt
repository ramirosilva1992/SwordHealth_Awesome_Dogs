package com.swordhealth.awesomedogs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.swordhealth.api.Resource
import com.swordhealth.awesomedogs.ui.search.viewmodel.SearchViewModel
import com.swordhealth.awesomedogs.ui.search.viewmodel.SearchViewModelContract
import com.swordhealth.awesomedogs.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import objects.BreedPresentation
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import usecases.FetchBreedsBySearchQueryUseCase

class SearchViewModelTests {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    private val fetchBreedsBySearchQueryUseCase = mock<FetchBreedsBySearchQueryUseCase>()

    private val dogId = 1
    private val queryTest = "query"
    private val errorMessage = "errorMessage"

    @Before
    fun setup() {
        viewModel = SearchViewModel(
            Dispatchers.IO,
            fetchBreedsBySearchQueryUseCase
        )
    }

    @Test
    fun `when searchBreeds should invoke use case`() = runTest {
        viewModel.invokeAction(SearchViewModelContract.Action.SearchBreeds(queryTest))

        verify(fetchBreedsBySearchQueryUseCase).invoke(queryTest)
    }

    @Test
    fun `when NavigateToBreedDetails action trigger GoToBreedDetail`() = runTest {
        viewModel.invokeAction(SearchViewModelContract.Action.NavigateToBreedDetails(dogId))
        val actual = viewModel.searchEvent.getOrAwaitValue()

        val expected = SearchViewModelContract.Event.GoToBreedDetail(dogId)
        assertEquals(expected, actual)
    }

    @Test
    fun `when searchBreeds and response is error should return ErrorState`() = runTest {
        whenever(fetchBreedsBySearchQueryUseCase(queryTest)).thenReturn(flow { emit(Resource.Error(errorMessage)) })

        viewModel.invokeAction(SearchViewModelContract.Action.SearchBreeds(queryTest))
        val actual = viewModel.searchState.getOrAwaitValue()

        val expected = SearchViewModelContract.State.ErrorState(errorMessage)
        assertEquals(expected, actual)
    }

    @Test
    fun `when searchBreeds and response is empty should return EmptyState`() = runTest {
        whenever(fetchBreedsBySearchQueryUseCase(queryTest)).thenReturn(flow { emit(Resource.Success(emptyList())) })

        viewModel.invokeAction(SearchViewModelContract.Action.SearchBreeds(queryTest))
        val actual = viewModel.searchState.getOrAwaitValue()

        val expected = SearchViewModelContract.State.EmptyState
        assertEquals(expected, actual)
    }

    @Test
    fun `when searchBreeds and response is list of breeds should return SuccessState`() = runTest {
        whenever(fetchBreedsBySearchQueryUseCase(queryTest)).thenReturn(flow { emit(Resource.Success(mockListOfBreeds())) })

        viewModel.invokeAction(SearchViewModelContract.Action.SearchBreeds(queryTest))
        val actual = viewModel.searchState.getOrAwaitValue()

        val expected = SearchViewModelContract.State.SuccessState(mockListOfBreeds())
        assertEquals(expected, actual)
    }

    private fun mockListOfBreeds() = listOf(
        BreedPresentation(
            1,
            "dog_name",
            "dog_category",
            "dog_group",
            "dog_origin",
            "dog_temperament",
            "dog_image"
        )
    )
}