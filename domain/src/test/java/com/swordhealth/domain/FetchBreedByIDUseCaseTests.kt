package com.swordhealth.domain

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import repository.BreedsRepository
import usecases.FetchBreedByIDUseCase

class FetchBreedByIDUseCaseTests {

    private val repository: BreedsRepository = mock()

    private lateinit var fetchBreedByIDUseCase: FetchBreedByIDUseCase

    @Before
    fun setup() {
        fetchBreedByIDUseCase = FetchBreedByIDUseCase(repository)
    }

    @Test
    fun `when id is passed verify if repository is called`() = runTest {
        fetchBreedByIDUseCase(1)
        verify(repository).getBreedByID(1)
    }
}