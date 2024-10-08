package com.example.mangaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
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

class DetailMangaActivity : AppCompatActivity(), DetailChapterAdapter.RecycleEvent {

    private lateinit var myAdapter : DetailChapterAdapter
    private lateinit var dataKu : DetailData
    private lateinit var mangaId : String

    private var lastReadId = ""
    private var lastChapterId = ""
    private var lastReadTitle = ""
    private var lastChapterTitle = ""
    private var lastImg = ""
    private var lastPosition = 0


    private val TAG = "DetailMangaActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_manga)


        
        mangaId = intent.getStringExtra("mangaId")!!
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

        val btnStart = findViewById<Button>(R.id.btnStartCh)



        btnStart.setOnClickListener {
            val firstCh = dataKu.chapterList.size
            val chapter  = dataKu.chapterList[firstCh - 1]
            Log.e(TAG, "onCreate: $chapter", )
            val idManga = mangaId
            val position = dataKu.chapterList.size - 1
            val intent = Intent(this, ReadActivity::class.java)

            lastReadId = idManga
            lastChapterId = dataKu.chapterList[position].id
            lastReadTitle = dataKu.name
            lastChapterTitle = dataKu.chapterList[position].name
            lastImg = dataKu.imageUrl
            lastPosition = position

            writeLastManga()



            intent.putExtra("ChapterId", chapter.id)
            intent.putExtra("MangaId", idManga)
            intent.putExtra("position",position)
            startActivity(intent)
        }




    }

    private fun setupRecycleView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView_DetailManga)

        recyclerView.apply {

            myAdapter = DetailChapterAdapter(this@DetailMangaActivity)
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@DetailMangaActivity, RecyclerView.VERTICAL, false)
        }
    }

    override fun onItemClick(position: Int) {
        val chapter  = dataKu.chapterList[position]
        val idManga = mangaId
        val position = position
        val intent = Intent(this, ReadActivity::class.java)

        lastReadId = idManga
        lastChapterId = dataKu.chapterList[position].id
        lastReadTitle = dataKu.name
        lastChapterTitle = dataKu.chapterList[position].name
        lastImg = dataKu.imageUrl
        lastPosition = position

        writeLastManga()




        intent.putExtra("ChapterId", chapter.id)
        intent.putExtra("MangaId", idManga)
        intent.putExtra("position",position)
        startActivity(intent)
    }

    private fun writeLastManga() {
        openFileOutput("lastReadId.txt", Context.MODE_PRIVATE).use { fileOut ->
            fileOut?.write(lastReadId.toByteArray())
        }
        openFileOutput("lastReadTitle.txt", Context.MODE_PRIVATE).use { fileOut ->
            fileOut?.write(lastReadTitle.toByteArray())
        }
        openFileOutput("lastReadImg.txt", Context.MODE_PRIVATE).use { fileOut ->
            fileOut?.write(lastImg.toByteArray())
        }
        openFileOutput("lastReadCh.txt", Context.MODE_PRIVATE).use { fileOut ->
            fileOut?.write(lastChapterTitle.toByteArray())
        }

        openFileOutput("lastReadChId.txt", Context.MODE_PRIVATE).use { fileOut ->
            fileOut?.write(lastChapterId.toByteArray())
        }

        openFileOutput("lastReadPosition.txt", Context.MODE_PRIVATE).use { fileOut ->
            fileOut?.write(lastPosition.toString().toByteArray())
        }
    }
}