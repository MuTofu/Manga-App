package com.example.mangaapp.datamodel.detail

data class DetailData(
    val author: String,
    val chapterList: List<Chapter>,
    val genres: List<String>,
    val imageUrl: String,
    val name: String,
    val status: String,
    val updated: String,
    val view: String
)