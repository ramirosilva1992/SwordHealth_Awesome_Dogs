package com.swordhealth.domain

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import repository.BreedsRepository
import usecases.FetchBreedsByPageUseCase

class FetchBreedsByPageUseCaseTests {

    private val repository: BreedsRepository = mock()

    private lateinit var fetchBreedsByPageUseCase: FetchBreedsByPageUseCase

    @Before
    fun setup() {
        fetchBreedsByPageUseCase = FetchBreedsByPageUseCase(repository)
    }

    @Test
    fun `when id is passed verify if repository is called`() = runTest {
        fetchBreedsByPageUseCase(1)
        verify(repository).getBreedsByPage(1)
    }
}