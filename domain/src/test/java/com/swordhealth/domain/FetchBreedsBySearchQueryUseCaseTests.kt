package com.swordhealth.domain

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import repository.BreedsRepository
import usecases.FetchBreedsBySearchQueryUseCase

class FetchBreedsBySearchQueryUseCaseTests {

    private val repository: BreedsRepository = mock()

    private lateinit var fetchBreedsBySearchQueryUseCase: FetchBreedsBySearchQueryUseCase

    @Before
    fun setup() {
        fetchBreedsBySearchQueryUseCase = FetchBreedsBySearchQueryUseCase(repository)
    }

    @Test
    fun `when id is passed verify if repository is called`() = runTest {
        fetchBreedsBySearchQueryUseCase("q")
        verify(repository).getBreedsBySearchQuery("q")
    }
}