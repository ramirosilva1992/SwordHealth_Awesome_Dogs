package com.swordhealth.api.datasource

import com.swordhealth.api.APIService
import com.swordhealth.api.objects.Breed
import kotlinx.coroutines.flow.Flow

class BreedsRemoteDataSource(private val apiService: APIService) {
    suspend fun getBreedsByPage(page: Int): Flow<List<Breed>> =
        apiService.provideAPIService().getBreedsByPage(page, 10)

    suspend fun getBreedsBySearchQuery(query: String): Flow<List<Breed>> =
        apiService.provideAPIService().getBreedsBySearchQuery(query)
}