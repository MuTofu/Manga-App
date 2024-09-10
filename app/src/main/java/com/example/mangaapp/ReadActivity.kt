package com.example.mangaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp.adapter.ReadPageAdapter
import com.example.mangaapp.apiservice.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.log

class ReadActivity : AppCompatActivity() {

    private val TAG = "ReadActivity"

    private lateinit var myAdapter: ReadPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        val chapterId = intent.getStringExtra("ChapterId")!!
        val mangaId = intent.getStringExtra("MangaId")!!
        val pgrBar = findViewById<ProgressBar>(R.id.pgrBarChapter)
        val layout = findViewById<ScrollView>(R.id.layoutChapter)



        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            pgrBar.isVisible = true
            layout.isVisible = false

            val respon = try {

                RetrofitInstance.api.getChapter(mangaId, chapterId)

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
                myAdapter.list = data.images

                Log.e(TAG, "Succsess", )
            } else {
                Log.e(TAG, "Respon Error", )
            }
            pgrBar.isVisible = false
            layout.isVisible = true
            Log.e(TAG, "onCreate: $chapterId")
            Log.e(TAG, "onCreate: $mangaId")


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