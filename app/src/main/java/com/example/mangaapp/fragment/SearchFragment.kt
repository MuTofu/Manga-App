package com.example.mangaapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp.DetailMangaActivity
import com.example.mangaapp.datamodel.HomeDummyData
import com.example.mangaapp.R
import com.example.mangaapp.adapter.SearchResultAdapter

class SearchFragment : Fragment(), SearchResultAdapter.RecyclerViewEvent {
    lateinit var homedata : ArrayList<HomeDummyData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        Log.e("refresing", "onCreateView: Refresh", )

        homedata = ArrayList<HomeDummyData>()

        val dataset = arrayOf("Majo no Tabi tabi", "Arknight : Endfield", "Attack on titan", "Marbel")
        val dataImg = arrayOf(R.drawable.card1, R.drawable.card2, R.drawable.card1, R.drawable.card2)

        for (i in 0 until dataset.size) {
            homedata.add(
                HomeDummyData(
                    dataImg[i],
                    dataset[i])
            )
        }

        val adapter = SearchResultAdapter(homedata, this)
        val recycler : RecyclerView = view.findViewById(R.id.recyclerView_Search)
        recycler.layoutManager = GridLayoutManager(context, 3)
        recycler.adapter = adapter

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()
            }
        })



        return view
    }

    override fun onItemClicked(position: Int) {
        var manga = homedata[position]
        val intent = Intent(context, DetailMangaActivity::class.java)
        intent.putExtra("mangaTitle", manga.title)
        intent.putExtra("mangaImage", manga.img)
        startActivity(intent)
    }

}