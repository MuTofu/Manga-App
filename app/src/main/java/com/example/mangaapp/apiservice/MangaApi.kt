package com.example.mangaapp.apiservice

import com.example.mangaapp.datamodel.detail.DetailData
import com.example.mangaapp.datamodel.list.MangaData
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Path

interface MangaApi {

    @GET("/api/mangaList")
    suspend fun getMangaList() : Response<MangaData>

    @GET("/api/manga/{id}")
    suspend fun getDetailManga(@Path("id") id : String) : Response<DetailData>


}