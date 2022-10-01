package com.swordhealth.api.objects

import com.google.gson.annotations.SerializedName

data class Breed(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("bred_for") val category: String?,
    @SerializedName("breed_group") val group: String?,
    @SerializedName("origin") val origin: String?,
    @SerializedName("temperament") val temperament: String?,
    @SerializedName("image") val image: BreedImage?
)

data class BreedImage(
    @SerializedName("url") val url: String?
)