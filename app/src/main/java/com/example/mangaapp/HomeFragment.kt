package com.example.mangaapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(), HomeAdapter.RecyclerViewEvent {
    lateinit var homedata: ArrayList<HomeData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        homedata = ArrayList<HomeData>()

        val dataset = arrayOf("Majo no Tabi tabi", "Arknight : Endfield", "Attack on titan", "Marbel")
        val dataImg = arrayOf(R.drawable.card1, R.drawable.card2, R.drawable.card1, R.drawable.card2)

        for (i in 0 until dataset.size) {
            homedata.add(
                HomeData(
                    dataImg[i],
                    dataset[i])
            )
        }

        val newUploadRecycle = view.findViewById<RecyclerView>(R.id.recycleView_NewUpload)
        val mangaRecycle = view.findViewById<RecyclerView>(R.id.recycleView_Manga)
        val manhwa = view.findViewById<RecyclerView>(R.id.recycleView_Manhwa)
        val donghua = view.findViewById<RecyclerView>(R.id.recycleView_Donghua)

        recyclerViewInput(homedata, newUploadRecycle)
        recyclerViewInput(homedata, mangaRecycle)
        recyclerViewInput(homedata, manhwa)
        recyclerViewInput(homedata, donghua)




//        val myAdapter = HomeAdapter(data)

//        val recycleview : RecyclerView = view.findViewById(R.id.recycleView_NewUpload)
//        recycleview.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
//        recycleview.adapter = myAdapter

        return view
    }

    fun recyclerViewInput(data: ArrayList<HomeData>, view : RecyclerView) {

        val myAdapter = HomeAdapter(data, this)
        val recycleview : RecyclerView = view
        recycleview.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        recycleview.adapter = myAdapter
    }

    override fun onItemClick(position: Int) {
        var manga = homedata[position].title

        val intent = Intent(this.context, DetailMangaActivity::class.java)
        intent.putExtra("mangaTitle", manga)
        intent.putExtra("mangaImage", homedata[position].img)
        startActivity(intent)
    }

}