package com.swordhealth.api.datasource

import com.swordhealth.api.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = call()

        if (response.isSuccessful && response.body() != null) {
            val totalBreeds = response.headers().firstOrNull { it.first == "pagination-count" }?.second
            Resource.Success(data = response.body()!!, totalBreeds?.toInt())
        } else
            Resource.Error(errorMessage = response.message() ?: "Something went wrong")

    } catch (e: HttpException) {
        Resource.Error(errorMessage = e.message ?: "Something went wrong")
    } catch (e: IOException) {
        Resource.Error("Please check your network connection")
    } catch (e: Exception) {
        Resource.Error(errorMessage = "Something went wrong")
    }

}