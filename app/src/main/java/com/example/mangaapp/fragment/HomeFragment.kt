package com.example.mangaapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp.DetailMangaActivity
import com.example.mangaapp.adapter.HomeOngoingAdapter
import com.example.mangaapp.R
import com.example.mangaapp.adapter.HomeCompletedAdapter
import com.example.mangaapp.adapter.HomeNewUploadAdapter
import com.example.mangaapp.apiservice.RetrofitInstance
import com.example.mangaapp.datamodel.list.MangaData
import retrofit2.HttpException
import java.io.IOException

class HomeFragment : Fragment(), HomeOngoingAdapter.RecycleEvent, HomeNewUploadAdapter.RecycleEvent, HomeCompletedAdapter.RecycleEvent {

    private lateinit var onGoingAdapter : HomeOngoingAdapter
    private lateinit var newUploadAdapter : HomeNewUploadAdapter
    private lateinit var completedApapter : HomeCompletedAdapter
    private lateinit var dataNewUpload : MangaData
    private lateinit var dataCompleted : MangaData

    private val TAG = "HomeFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)



        val onGoingRecycler = view.findViewById<RecyclerView>(R.id.recycleView_OnGoing)
        val CompletedRecycler = view.findViewById<RecyclerView>(R.id.recycleView_Completed)

        val newUploadRecycle = view.findViewById<RecyclerView>(R.id.recycleView_NewUpload)
        val pgrBarNewUpload = view.findViewById<ProgressBar>(R.id.pgrBarNewUpload)
        val pgrBarOngoing = view.findViewById<ProgressBar>(R.id.pgrBarOngoing)
        val pgrBarCompleted = view.findViewById<ProgressBar>(R.id.pgrBarCompleted)




        lifecycleScope.launchWhenCreated {


            //
            pgrBarNewUpload.isVisible = true
            pgrBarOngoing.isVisible = true
            pgrBarCompleted.isVisible = true
            setupNewUploadRecyclerView(newUploadRecycle)

            val responNewUpload = try {

                RetrofitInstance.api.getMangaListType()

            } catch (e: IOException) {
                Log.e(TAG, "IOException ${e.message}", )
                pgrBarNewUpload.isVisible = false
                return@launchWhenCreated

            } catch (e : HttpException) {
                Log.e(TAG, "HttpException ${e.message}", )
                pgrBarNewUpload.isVisible = false
                return@launchWhenCreated
            }

            if(responNewUpload.isSuccessful && responNewUpload.body() != null) {
                dataNewUpload = responNewUpload.body()!!

                newUploadAdapter.list = dataNewUpload.mangaList
                Log.e(TAG, "Success", )

            } else {
                Log.e(TAG, "Respon error", )
            }
            pgrBarNewUpload.isVisible = false


            /////////////////////////////////
            setupOngoingRecyclerView(onGoingRecycler)


            val responOnGoing = try {

                RetrofitInstance.api.getMangaList("Ongoing")

            } catch (e: IOException) {
                Log.e(TAG, "IOException ${e.message}", )
                pgrBarOngoing.isVisible = false
                return@launchWhenCreated

            } catch (e : HttpException) {
                Log.e(TAG, "HttpException ${e.message}", )
                pgrBarOngoing.isVisible = false
                return@launchWhenCreated
            }

            if(responOnGoing.isSuccessful && responOnGoing.body() != null) {
                dataNewUpload = responOnGoing.body()!!

                onGoingAdapter.list = dataNewUpload.mangaList
                Log.e(TAG, "Success", )

            } else {
                Log.e(TAG, "Respon error", )
            }
            pgrBarOngoing.isVisible = false
            /////////////////////////////////////

            setupCompletedRecyclerView(CompletedRecycler)

            val responCompleted = try {

                RetrofitInstance.api.getMangaList("Completed")

            } catch (e: IOException) {
                Log.e(TAG, "IOException ${e.message}", )
                pgrBarCompleted.isVisible = false
                return@launchWhenCreated

            } catch (e : HttpException) {
                Log.e(TAG, "HttpException ${e.message}", )
                pgrBarCompleted.isVisible = false
                return@launchWhenCreated
            }

            if(responCompleted.isSuccessful && responCompleted.body() != null) {
                dataCompleted = responCompleted.body()!!

                completedApapter.list = dataCompleted.mangaList
                Log.e(TAG, "Success", )

            } else {
                Log.e(TAG, "Respon error", )
            }
            pgrBarCompleted.isVisible = false





        }


//        apiCalllOnGoing("Ongoing", onGoingRecycler)
//        apiCalllCompleted("Completed", CompletedRecycler)








        return view
    }




    private fun setupCompletedRecyclerView(recyclerView: RecyclerView) {
        val recycleKu = recyclerView

        recycleKu.apply {
            completedApapter = HomeCompletedAdapter(this@HomeFragment)
            adapter = completedApapter
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setupOngoingRecyclerView(recyclerView: RecyclerView) {
        val recycleKu = recyclerView

        recycleKu.apply {
            onGoingAdapter = HomeOngoingAdapter(this@HomeFragment)
            adapter = onGoingAdapter
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setupNewUploadRecyclerView(recyclerView: RecyclerView) {
        val recycleKu = recyclerView

        recycleKu.apply {
            newUploadAdapter = HomeNewUploadAdapter(this@HomeFragment)
            adapter = newUploadAdapter
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        }
    }

    override fun onItemClick(position: Int) {
        var manga = dataNewUpload.mangaList[position]
        val intent = Intent(context, DetailMangaActivity::class.java)
        intent.putExtra("mangaId", manga.id)
        intent.putExtra("mangaDescription", manga.description)
        startActivity(intent)
    }

    override fun onItemOngoingClick(position: Int) {
        var manga = dataNewUpload.mangaList[position]
        val intent = Intent(context, DetailMangaActivity::class.java)
        intent.putExtra("mangaId", manga.id)
        intent.putExtra("mangaDescription", manga.description)
        startActivity(intent)
    }

    override fun onItemCompletedClick(position: Int) {
        var manga = dataCompleted.mangaList[position]
        val intent = Intent(context, DetailMangaActivity::class.java)
        intent.putExtra("mangaId", manga.id)
        intent.putExtra("mangaDescription", manga.description)
        startActivity(intent)
    }


}