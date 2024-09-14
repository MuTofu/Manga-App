package com.example.mangaapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mangaapp.DetailMangaActivity
import com.example.mangaapp.adapter.HomeOngoingAdapter
import com.example.mangaapp.R
import com.example.mangaapp.ReadActivity
import com.example.mangaapp.adapter.HomeCompletedAdapter
import com.example.mangaapp.adapter.HomeNewUploadAdapter
import com.example.mangaapp.apiservice.RetrofitInstance
import com.example.mangaapp.datamodel.list.MangaData
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class HomeFragment : Fragment(), HomeOngoingAdapter.RecycleEvent, HomeNewUploadAdapter.RecycleEvent, HomeCompletedAdapter.RecycleEvent {

    private lateinit var onGoingAdapter : HomeOngoingAdapter
    private lateinit var newUploadAdapter : HomeNewUploadAdapter
    private lateinit var completedApapter : HomeCompletedAdapter
    private lateinit var dataNewUpload : MangaData
    private lateinit var dataCompleted : MangaData
    private lateinit var txtLastReadTitle : TextView
    private lateinit var cardLastRead : CardView

    private lateinit var lastReadTitle :TextView
    private lateinit var lastReadCh : TextView
    private lateinit var lastReadImg : ImageView


    private var contentId = "first-launch"
    private var contentTitle = "first-launch"
    private var contentImg = "first-launch"
    private var contentCh = "first-launch"
    private var contentChId = "first-launch"
    private var contentPosition = 0


    private val TAG = "HomeFragment"

    override fun onResume() {
        super.onResume()

        lastReadDataCall()

        if (contentId != "first-launch") {
            Log.e(TAG, "onResume: Tru bang", )

            lastReadTitle.text = contentTitle
            lastReadCh.text = contentCh
            lastReadImg.load(contentImg)

            txtLastReadTitle.isVisible = true
            cardLastRead.isVisible = true


        } else {
            txtLastReadTitle.isVisible = false
            cardLastRead.isVisible = false
        }




    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        txtLastReadTitle = view.findViewById(R.id.layoutLastReadTitle)
        cardLastRead = view.findViewById(R.id.layoutLastReadCard)
        lastReadTitle = view.findViewById(R.id.txtLastReadTitle)
        lastReadCh = view.findViewById(R.id.txtLastReadCh)
        lastReadImg = view.findViewById(R.id.imgLastRead)


        lastReadDataCall()


        if (contentId != "first-launch") {
            Log.e(TAG, "onResume: Tru bang", )

            lastReadTitle.text = contentTitle
            lastReadCh.text = contentCh
            lastReadImg.load(contentImg)
            Log.e(TAG, "$contentPosition", )



            txtLastReadTitle.isVisible = true
            cardLastRead.isVisible = true

        } else {
            txtLastReadTitle.isVisible = false
            cardLastRead.isVisible = false
        }

        cardLastRead.setOnClickListener {
            val idManga = contentId
            val position = contentPosition
            val intent = Intent(context, ReadActivity::class.java)
            intent.putExtra("ChapterId", contentChId)
            intent.putExtra("MangaId", idManga)
            intent.putExtra("position",position)
            startActivity(intent)
        }

        





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


    private fun lastReadDataCall() {
        try {

            contentId = requireActivity().openFileInput("lastReadId.txt").bufferedReader().use { it.readText() }
            contentTitle = requireActivity().openFileInput("lastReadTitle.txt").bufferedReader().use { it.readText() }
            contentImg = requireActivity().openFileInput("lastReadImg.txt").bufferedReader().use { it.readText() }
            contentCh = requireActivity().openFileInput("lastReadCh.txt").bufferedReader().use { it.readText() }
            contentChId = requireActivity().openFileInput("lastReadChId.txt").bufferedReader().use { it.readText() }
            contentPosition = requireActivity().openFileInput("lastReadPosition.txt").bufferedReader().use { it.readText() }.toInt()

//            Log.e(TAG, "lastRead: ${contentId.toString()}", )
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "lastRead: ${e.message}", )
        }
    }

    private fun writeFile() {

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