package com.example.mangaapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class BrowseFragment : Fragment(), HomeAdapter.RecyclerViewEvent {
    lateinit var data : ArrayList<HomeData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_browse, container, false)

        data = ArrayList<HomeData>()


        val dataset = arrayOf("Majo no Tabi tabi", "Naruto", "Attack on titan", "Marbel")
        val dataImg = arrayOf(R.drawable.card1, R.drawable.card2, R.drawable.card1, R.drawable.card2)

        for (i in 0 until dataset.size) {
            data.add(
                HomeData(
                    dataImg[i],
                    dataset[i])
            )
        }

        val myAdapter = HomeAdapter(data,this)
        val recycler : RecyclerView = view.findViewById(R.id.recycleView_Browser)
        recycler.layoutManager = GridLayoutManager(this.context, 3)
        recycler.adapter = myAdapter


        return view
    }

    override fun onItemClick(position: Int) {
        var manga = data[position]
        val intent = Intent(context, DetailMangaActivity::class.java)
        intent.putExtra("mangaTitle", manga.title)
        intent.putExtra("mangaImage", manga.img)
        startActivity(intent)

    }


}