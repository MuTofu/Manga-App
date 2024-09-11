package com.example.mangaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp.adapter.ReadPageAdapter
import com.example.mangaapp.apiservice.RetrofitInstance
import com.example.mangaapp.datamodel.chapter.ChapterIds
import com.example.mangaapp.datamodel.detail.Chapter
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.log

class ReadActivity : AppCompatActivity() {

    private val TAG = "ReadActivity"
    private var positionNow = 0
    private lateinit var chapterId : String
    private lateinit var mangaId : String
    private lateinit var chapterList : List<ChapterIds>

    private lateinit var myAdapter: ReadPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        chapterId = intent.getStringExtra("ChapterId")!!
        mangaId = intent.getStringExtra("MangaId")!!
        positionNow = intent.getIntExtra("position", 0)

        apiCall(mangaId, chapterId)



        val btnNext = findViewById<Button>(R.id.btnNextCh)
        btnNext.setOnClickListener {


            if (positionNow != 0) {
                val chapter  = chapterList[positionNow - 1].id
                Log.e(TAG + "chId 1", "onCreate: $chapterId", )
                Log.e(TAG + "chId 2", "onCreate: $chapter", )
                val idManga = mangaId
                apiCall(idManga, chapter)
                positionNow = positionNow - 1
            } else {
                Toast.makeText(this, "Wait for Update!", Toast.LENGTH_LONG).show()
            }
            




        }





//        setupRecyclerView()
//
//        lifecycleScope.launchWhenCreated {
//            pgrBar.isVisible = true
//            layout.isVisible = false
//
//            val respon = try {
//
//                RetrofitInstance.api.getChapter(mangaId, chapterId)
//
//            } catch (e: IOException) {
//                Log.e(TAG, "IOException: ${e.message}", )
//                pgrBar.isVisible = false
//                return@launchWhenCreated
//            }catch (e : HttpException){
//                Log.e(TAG, "HttpException: ${e.message}", )
//                pgrBar.isVisible = false
//                return@launchWhenCreated
//            }
//
//            if (respon.isSuccessful && respon.body() != null){
//                val data = respon.body()!!
//
//                chapterList = data.chapterListIds
//                myAdapter.list = data.images
//
//                Log.e(TAG, "Succsess", )
//            } else {
//                Log.e(TAG, "Respon Error", )
//            }
//            pgrBar.isVisible = false
//            layout.isVisible = true
//            Log.e(TAG, "onCreate: $chapterId")
//            Log.e(TAG, "onCreate: $mangaId")
//
//        }








    }

    private fun apiCall(idManga : String, idChapter : String) {

        var mangaIdku = idManga
        var chapterIdku = idChapter
        setupRecyclerView()

        val pgrBar = findViewById<ProgressBar>(R.id.pgrBarChapter)
        val layout = findViewById<ScrollView>(R.id.layoutChapter)

        lifecycleScope.launchWhenCreated {
            pgrBar.isVisible = true
            layout.isVisible = false

            val respon = try {

                RetrofitInstance.api.getChapter(mangaIdku, chapterIdku)

            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}", )
                pgrBar.isVisible = false
                return@launchWhenCreated
            }catch (e : HttpException){
                Log.e(TAG, "HttpException: ${e.message}", )
                pgrBar.isVisible = false
                return@launchWhenCreated
            }

            if (respon.isSuccessful && respon.body() != null){
                val data = respon.body()!!

                chapterList = data.chapterListIds
                myAdapter.list = data.images

                Log.e(TAG, "Succsess", )
            } else {
                Log.e(TAG, "Respon Error", )
            }
            pgrBar.isVisible = false
            layout.isVisible = true

            var curenCh = chapterList[positionNow].id
            Log.e(TAG, "Size: ${chapterList.size}", )
            Log.e(TAG, "apiCall: $positionNow}", )
            Log.e("ReadActivity Current ch", "onCreate: $curenCh", )

        }
    }

    private fun setupRecyclerView() {
        val recycler = findViewById<RecyclerView>(R.id.recyclerView_Read)
        recycler.apply {
            myAdapter = ReadPageAdapter()
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@ReadActivity, RecyclerView.VERTICAL, false)

        }
    }
}