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
import com.example.mangaapp.adapter.HomeAdapter
import com.example.mangaapp.R
import com.example.mangaapp.apiservice.RetrofitInstance
import com.example.mangaapp.datamodel.list.MangaData
import retrofit2.HttpException
import java.io.IOException

class HomeFragment : Fragment(), HomeAdapter.RecycleEvent {
//    lateinit var homedata: ArrayList<HomeData>
    private lateinit var myAdapter : HomeAdapter
    private lateinit var data : MangaData
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



        val mangaRecycle = view.findViewById<RecyclerView>(R.id.recycleView_Manga)
        val manhwa = view.findViewById<RecyclerView>(R.id.recycleView_Manhwa)
        val donghua = view.findViewById<RecyclerView>(R.id.recycleView_Donghua)

        val newUploadRecycle = view.findViewById<RecyclerView>(R.id.recycleView_NewUpload)
        val pgrBar = view.findViewById<ProgressBar>(R.id.pgrBar)

        setupRecyclerView(newUploadRecycle)

        lifecycleScope.launchWhenCreated {
            pgrBar.isVisible = true

            val respon = try {

                RetrofitInstance.api.getMangaList()

            } catch (e: IOException) {
                Log.e(TAG, "IOException ${e.message}", )
                pgrBar.isVisible = false
                return@launchWhenCreated

            } catch (e : HttpException) {
                Log.e(TAG, "HttpException ${e.message}", )
                pgrBar.isVisible = false
                return@launchWhenCreated
            }

            if(respon.isSuccessful && respon.body() != null) {
                data = respon.body()!!

                myAdapter.list = data.mangaList
                Log.e(TAG, "Success", )

            } else {
                Log.e(TAG, "Respon error", )
            }
            pgrBar.isVisible = false

        }

        return view
    }



    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val recycleKu = recyclerView

        recycleKu.apply {
            myAdapter = HomeAdapter(this@HomeFragment)
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        }
    }

    override fun onItemClick(position: Int) {
        var manga = data.mangaList[position]
        val intent = Intent(context, DetailMangaActivity::class.java)
        intent.putExtra("mangaTitle", manga.title)
        intent.putExtra("mangaImage", manga.image)
        intent.putExtra("mangaId", manga.description)
        startActivity(intent)
    }






}