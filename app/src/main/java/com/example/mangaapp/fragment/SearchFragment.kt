package com.example.mangaapp.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp.DetailMangaActivity
import com.example.mangaapp.MainActivity
import com.example.mangaapp.datamodel.HomeDummyData
import com.example.mangaapp.R
import com.example.mangaapp.adapter.SearchResultAdapter
import com.example.mangaapp.apiservice.RetrofitInstance
import com.example.mangaapp.datamodel.list.MangaData
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException

class SearchFragment : Fragment(), SearchResultAdapter.RecyclerViewEvent {
    private lateinit var searchAdapter : SearchResultAdapter
    private lateinit var data : MangaData
    private lateinit var recycler : RecyclerView
    private lateinit var pgrBar : ProgressBar
    private lateinit var layout : NestedScrollView

    private var totalPage = 0
    private var pageNumber = 0
    private var TAG = "SearchFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        var myQuery = arguments?.getString("query")
        recycler = view.findViewById(R.id.recyclerView_Search)
        layout = view.findViewById(R.id.layoutSearch)
        pgrBar = view.findViewById(R.id.pgrBarSearch)

        apiCall(myQuery.toString(), totalPage)

        val btnBack = view.findViewById<ImageButton>(R.id.btnSearchPageBack)
        val btnNext = view.findViewById<ImageButton>(R.id.btnSearchPageForward)
        val currentPage = view.findViewById<TextView>(R.id.currentSearchPage)

        if (pageNumber == 0) {
            pageNumber = 1
        }

        btnBack.setOnClickListener {
            if (pageNumber != 0) {
                pageNumber--
                apiCall(myQuery.toString(), pageNumber)
                currentPage.text = pageNumber.toString()
            }
        }

        btnNext.setOnClickListener {
            if (pageNumber < totalPage) {
                pageNumber++
                apiCall(myQuery.toString(), pageNumber)
                currentPage.text = pageNumber.toString()
            }
        }






        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()
            }
        })



        return view
    }

    private fun apiCall(query : String, page : Int) {
        setupRecyclerView()
        var queryInput = query
        var pageInput = page

        lifecycleScope.launchWhenCreated {
            layout.isVisible = false
            pgrBar.isVisible = true

            val respon = try {
                RetrofitInstance.api.searchManga(queryInput, pageInput)
            } catch (e: IOException) {
                Log.e(TAG, "IOException ${e.message}", )
                pgrBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException ${e.message}", )
                pgrBar.isVisible = false
                return@launchWhenCreated
            }

            if (respon.isSuccessful && respon.body() != null) {
                data = respon.body()!!
                totalPage = data.metaData.totalPages

                searchAdapter.list = data.mangaList

                Log.e(TAG, "apiCall: ${data.mangaList}", )
                Log.e(TAG, "apiCall: Succses", )
            } else {
                Log.e(TAG, "apiCall: Server Error", )
            }

            pgrBar.isVisible = false
            layout.isVisible = true
        }



    }

    private fun setupRecyclerView() {
        searchAdapter = SearchResultAdapter(this@SearchFragment)
        recycler.layoutManager = GridLayoutManager(context, 3)
        recycler.adapter = searchAdapter

    }





    override fun onItemClicked(position: Int) {
        var manga = data.mangaList[position]
        val intent = Intent(context, DetailMangaActivity::class.java)
        intent.putExtra("mangaId", manga.id)
        intent.putExtra("mangaDescription", manga.description)
        startActivity(intent)
    }



}