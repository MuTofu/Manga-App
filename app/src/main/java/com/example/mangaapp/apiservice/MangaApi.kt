package com.example.mangaapp.apiservice

import com.example.mangaapp.datamodel.list.MangaData
import retrofit2.http.GET
import retrofit2.Response

interface MangaApi {

    @GET("/api/mangaList")
    suspend fun getMangaList() : Response<MangaData>

}