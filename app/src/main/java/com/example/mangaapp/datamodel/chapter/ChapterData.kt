package com.example.mangaapp.datamodel.chapter

data class ChapterData(
    val chapterListIds: List<ChapterIds>,
    val currentChapter: String,
    val images: List<Image>,
    val title: String
)