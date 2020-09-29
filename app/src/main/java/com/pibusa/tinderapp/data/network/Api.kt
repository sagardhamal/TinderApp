package com.pibusa.tinderapp.data.network

import com.google.gson.Gson
import com.pibusa.tinderapp.data.network.response.TinderProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Api {
    @GET("api/0.4")
    suspend fun getProfileDetails(@Query("randomapi") userId: String?): TinderProfileResponse


    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): Api {
            val okkHttpClient =
                OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(networkConnectionInterceptor).build()
            return Retrofit.Builder().client(okkHttpClient)
                .baseUrl("https://randomuser.me").addConverterFactory(
                    GsonConverterFactory.create()
                ).build().create(Api::class.java)
        }

        private val interceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

}