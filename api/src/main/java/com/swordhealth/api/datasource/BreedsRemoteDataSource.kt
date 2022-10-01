package com.swordhealth.api.datasource

import com.swordhealth.api.APIInterface
import com.swordhealth.api.objects.Breed
import kotlinx.coroutines.flow.Flow

class BreedsRemoteDataSource(private val apiInterface: APIInterface) {
    suspend fun getBreedsByPage(page: Int): Flow<List<Breed>> =
        apiInterface.getBreedsByPage(page, 10)

    suspend fun getBreedsBySearchQuery(query: String): Flow<List<Breed>> =
        apiInterface.getBreedsBySearchQuery(query)
}