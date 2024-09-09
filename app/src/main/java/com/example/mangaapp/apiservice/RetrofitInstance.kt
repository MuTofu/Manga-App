package com.example.mangaapp.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api : MangaApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.91.89:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MangaApi::class.java)

    }

}