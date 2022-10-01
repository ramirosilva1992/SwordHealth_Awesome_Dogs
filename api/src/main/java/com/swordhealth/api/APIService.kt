package com.swordhealth.api

import com.swordhealth.api.APIConstants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {

    fun provideAPIService() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(APIInterface::class.java)


    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
                .addHeader(
                    "x-api-key",
                    "live_UZzXPPjgB0UNBalex2S5bapDxlpFj6ju9XZKbifb9dZSq0w3HDA5lcSOGKcTAY2f"
                )
            chain.proceed(builder.build())
        }).build()
}