package com.example.mangaapp.apiservice

import com.example.mangaapp.datamodel.chapter.ChapterData
import com.example.mangaapp.datamodel.detail.DetailData
import com.example.mangaapp.datamodel.list.MangaData
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface MangaApi {

    @GET("/api/mangaList")
    suspend fun getMangaList(@Query("state")  state : String) : Response<MangaData>

    @GET("/api/mangaList")
    suspend fun getMangaListType() : Response<MangaData>

    @GET("/api/manga/{id}")
    suspend fun getDetailManga(@Path("id") id : String) : Response<DetailData>

    @GET("/api/manga/{mangaId}/{chapterId}")
    suspend fun getChapter(@Path("mangaId") mangaId: String, @Path("chapterId") chapterId: String) : Response<ChapterData>

    @GET("api/mangaList")
    suspend fun getMangaPage(@Query("page") page: Int) : Response<MangaData>

    @GET("api/search/{querySearch}")
    suspend fun searchManga(@Path("querySearch") querySearch: String, @Query("page") page : Int) : Response<MangaData>




}