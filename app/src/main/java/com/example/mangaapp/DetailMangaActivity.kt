package com.example.mangaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mangaapp.adapter.DetailChapterAdapter
import com.example.mangaapp.apiservice.RetrofitInstance
import com.example.mangaapp.datamodel.detail.DetailData
import retrofit2.HttpException
import java.io.IOException

class DetailMangaActivity : AppCompatActivity() {

    private lateinit var myAdapter : DetailChapterAdapter

    private lateinit var dataKu : DetailData

    private val TAG = "DetailMangaActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_manga)
        
        val mangaId = intent.getStringExtra("mangaId")
        val mangaDesc = intent.getStringExtra("mangaDescription")
        val pgrBar = findViewById<ProgressBar>(R.id.pgrBarDetail)
        val layout = findViewById<RelativeLayout>(R.id.layoutDetail)

        setupRecycleView()

        lifecycleScope.launchWhenCreated {
            layout.isVisible = false
            pgrBar.isVisible = true

            val respon = try {

                RetrofitInstance.api.getDetailManga("${mangaId}")

            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", )
                pgrBar.isVisible = false
                return@launchWhenCreated
            } catch (e : HttpException) {
                Log.e(TAG, "HttpException: ${e.message}", )
                pgrBar.isVisible = false
                return@launchWhenCreated
            }

            if(respon.isSuccessful && respon.body() != null) {
                dataKu = respon.body()!!

                Log.e(TAG, "data: ${dataKu.toString()}", )
                myAdapter.list = dataKu.chapterList

                Log.e(TAG, "Success", )
            } else {
//                dataKu = respon.body()!!
                Log.e(TAG, "Respon Error", )
            }

            pgrBar.isVisible = false

            val title = findViewById<TextView>(R.id.detailJudul)
            val totalChapter = findViewById<TextView>(R.id.detailTotalCh)
            val imagePoster = findViewById<ImageView>(R.id.detailImage)
            val deskripsi = findViewById<TextView>(R.id.detailDesc)

            title.text = dataKu.name
            totalChapter.text = "Total Chapter: ${dataKu.chapterList.size}"
            imagePoster.load(dataKu.imageUrl)
            deskripsi.text = mangaDesc

            layout.isVisible = true

        }








    }

    private fun setupRecycleView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView_DetailManga)

        recyclerView.apply {
            myAdapter = DetailChapterAdapter()
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@DetailMangaActivity, RecyclerView.VERTICAL, false)
        }
    }

//    override fun onItemClick(position: Int) {
//        TODO("Not yet implemented")
//    }
}