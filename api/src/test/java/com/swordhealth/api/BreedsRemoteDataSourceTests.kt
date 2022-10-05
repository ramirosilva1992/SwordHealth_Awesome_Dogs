package com.swordhealth.api

import com.swordhealth.api.datasource.BreedsRemoteDataSource
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class BreedsRemoteDataSourceTests {

    private lateinit var breedsRemoteDataSource: BreedsRemoteDataSource

    private val apiInterface = mockk<APIInterface>(relaxed = true)

    @Before
    fun setup() {
        breedsRemoteDataSource = BreedsRemoteDataSource((apiInterface))
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getBreedsByPage should call apiInterface getBreedsByPage`() = runTest {
        breedsRemoteDataSource.getBreedsByPage(1)
        coVerify { apiInterface.getBreedsByPage(1, 20) }
    }

    @Test
    fun `getBreedsBySearchQuery should call apiInterface getBreedsBySearchQuery`() = runTest {
        breedsRemoteDataSource.getBreedsBySearchQuery("q")
        coVerify { apiInterface.getBreedsBySearchQuery("q") }
    }
}