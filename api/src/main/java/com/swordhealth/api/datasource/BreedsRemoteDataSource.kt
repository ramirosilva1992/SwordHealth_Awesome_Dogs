package com.swordhealth.api.datasource

import com.swordhealth.api.APIInterface
import com.swordhealth.api.Resource
import com.swordhealth.api.objects.Breed

class BreedsRemoteDataSource(private val apiInterface: APIInterface) {
    suspend fun getBreedsByPage(page: Int): Resource<List<Breed>> =
        safeApiCall { apiInterface.getBreedsByPage(page, 20) }

    suspend fun getBreedsBySearchQuery(query: String): Resource<List<Breed>> =
        safeApiCall { apiInterface.getBreedsBySearchQuery(query) }
}