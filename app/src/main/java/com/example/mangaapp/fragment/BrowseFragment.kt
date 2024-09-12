package com.example.mangaapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp.DetailMangaActivity
import com.example.mangaapp.datamodel.HomeDummyData
import com.example.mangaapp.adapter.HomeDummyAdapter
import com.example.mangaapp.R
import com.example.mangaapp.adapter.BrowserAdapter
import com.example.mangaapp.apiservice.RetrofitInstance
import com.example.mangaapp.datamodel.list.Manga
import com.example.mangaapp.datamodel.list.MangaData
import retrofit2.HttpException
import java.io.IOException


class BrowseFragment : Fragment(), BrowserAdapter.RecycleEvent {
//    lateinit var data : ArrayList<HomeDummyData>
    private lateinit var data : MangaData
    private lateinit var pgrBar : ProgressBar
    private lateinit var browserAdapter : BrowserAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var layout : NestedScrollView

    private var totalPage = 0
    private var pageNumber : Int = 0
    private var TAG = "BrowseFragment"





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_browse, container, false)

        pgrBar = view.findViewById(R.id.pgrBarBrowser)
        recycler  = view.findViewById(R.id.recycleView_Browser)
        layout = view.findViewById(R.id.layoutBrowser)


        
        if (pageNumber == 0) {
            pageNumber = 1
        }

        var currentPage = view.findViewById<TextView>(R.id.currentPage)
        currentPage.text = pageNumber.toString()

        apiCall(pageNumber)

        val btnBack = view.findViewById<ImageButton>(R.id.btnPageBack)
        val btnNext = view.findViewById<ImageButton>(R.id.btnPageForward)

        btnBack.setOnClickListener {
            if (pageNumber != 0) {
                pageNumber--
                apiCall(pageNumber)
                currentPage.text = pageNumber.toString()
            }
        }

        btnNext.setOnClickListener {
            if (pageNumber < totalPage) {
                pageNumber++
                apiCall(pageNumber)
                currentPage.text = pageNumber.toString()
            }
        }














        return view
    }

    private fun apiCall(page : Int) {
        setupRecyclerView()
        var halaman = page

        lifecycleScope.launchWhenCreated {
            layout.isVisible = false
            pgrBar.isVisible = true

            val respon = try {

                RetrofitInstance.api.getMangaPage(halaman)

            } catch (e: IOException) {
                Log.e(TAG, "IOException ${e.message}", )
                pgrBar.isVisible = false
                return@launchWhenCreated
            } catch (e : HttpException) {
                Log.e(TAG, "HttpException ${e.message}", )
                pgrBar.isVisible = false
                return@launchWhenCreated
            }

            if (respon.isSuccessful && respon.body() != null) {
                data = respon.body()!!
                totalPage = data.metaData.totalPages
                browserAdapter.list = data.mangaList
                Log.e(TAG, "apiCall: Succsess", )
            } else {
                Log.e(TAG, "apiCall: Server Error", )
            }

            pgrBar.isVisible = false
            layout.isVisible = true

        }
    }

    private fun setupRecyclerView() {
        browserAdapter = BrowserAdapter(this@BrowseFragment)

        recycler.layoutManager = GridLayoutManager(this.context, 3)
        recycler.adapter = browserAdapter
    }

    override fun onItemClick(position: Int) {
        var manga = data.mangaList[position]
        val intent = Intent(context, DetailMangaActivity::class.java)
        intent.putExtra("mangaId", manga.id)
        intent.putExtra("mangaDescription", manga.description)
        startActivity(intent)

    }


}