package com.swordhealth.api

import com.github.simonpercic.oklog3.OkLogInterceptor
import com.swordhealth.api.APIConstants.API_KEY
import com.swordhealth.api.APIConstants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {

    fun provideAPIService(): APIInterface = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(APIInterface::class.java)


    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        // Adding header interceptor to set API key on every request
        .addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
                .addHeader(
                    "x-api-key",
                    API_KEY
                )
            chain.proceed(builder.build())
        })

        //Adding interceptors for debug purposes
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(
            OkLogInterceptor.builder()
                .withProtocol(true)
                .withRequestContentType(true)
                .withRequestHeaders(true)
                .withResponseUrl(true)
                .withResponseHeaders(true)
                .shortenInfoUrl(true)
                .build()
        ).build()
}