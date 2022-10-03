package com.swordhealth.api

import com.swordhealth.api.APIConstants.BREEDS
import com.swordhealth.api.APIConstants.BREEDS_SEARCH
import com.swordhealth.api.objects.Breed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET(BREEDS)
    suspend fun getBreedsByPage(
        @Query("page") pageNumber: Int,
        @Query("limit") pageLimit: Int
    ): Response<List<Breed>>

    @GET(BREEDS_SEARCH)
    suspend fun getBreedsBySearchQuery(
        @Query("q") query: String
    ): Response<List<Breed>>
}