package com.swordhealth.api

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val totalBreeds: Int? = null
) {

    class Success<T>(data: T, totalBreeds: Int? = null) : Resource<T>(data = data)

    class Error<T>(errorMessage: String) : Resource<T>(message = errorMessage)

    class Loading<T> : Resource<T>()
}
