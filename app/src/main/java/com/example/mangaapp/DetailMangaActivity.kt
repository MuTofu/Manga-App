package com.example.mangaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mangaapp.adapter.DetailChapterAdapter
import com.example.mangaapp.datamodel.detail.DetailMangaDummyData

class DetailMangaActivity : AppCompatActivity(), DetailChapterAdapter.RecyclerListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_manga)

        val mangaTitle = intent.getStringExtra("mangaTitle")
        val mangaImg = intent.getStringExtra("mangaImage")

        val titleDetail = findViewById<TextView>(R.id.detailJudul)
        val imageDetail : ImageView = findViewById(R.id.detailImage)

        titleDetail.text = mangaTitle
        imageDetail.load(mangaImg)

        val dataChapter = arrayOf("chapter1", "chapter2", "chapter3", "chapter4", "chapter5", "chapter6", "chapter7")
        val dataRelease = arrayOf("01-09-2024","02-09-2024","03-09-2024","05-09-2024","11-09-2024", "21-09-2024","12-09-2024")

        val data = ArrayList<DetailMangaDummyData>()

        for (i in 0 until dataChapter.size) {
            data.add(
                DetailMangaDummyData(
                    dataChapter[i],
                    dataRelease[i],
                )
            )
        }

        val recyclerChapter = findViewById<RecyclerView>(R.id.recycleView_DetailManga)
        val myadapter = DetailChapterAdapter(data, this)
        val recycler : RecyclerView = recyclerChapter
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        recycler.adapter = myadapter

    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}