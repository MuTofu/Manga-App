package com.example.mangaapp.datamodel.list

data class MetaData(
    val category: List<Category>,
    val state: List<State>,
    val totalPages: Int,
    val totalStories: Int,
    val type: List<Type>
)