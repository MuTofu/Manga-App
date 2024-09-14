package com.example.mangaapp

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp.adapter.SearchResultAdapter
import com.example.mangaapp.datamodel.HomeDummyData

class SearchActivityNotUsed : AppCompatActivity(), SearchResultAdapter.RecyclerViewEvent {

    lateinit var data : ArrayList<HomeDummyData>

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //importan to all toolbar
        val actionToolbar = findViewById<Toolbar>(R.id.toolbar_search)
        setSupportActionBar(actionToolbar)
        getSupportActionBar()?.setTitle("Manga App")



        val query = intent.getStringExtra("searchQuery")

        data = ArrayList<HomeDummyData>()

        val dataset = arrayOf("Majo no Tabi tabi", "Naruto", "Attack on titan", "Marbel")
        val dataImg = arrayOf(R.drawable.card1, R.drawable.card2, R.drawable.card1, R.drawable.card2)

        for (i in 0 until dataset.size) {
            data.add(
                HomeDummyData(
                    dataImg[i],
                    dataset[i])
            )
        }

//        recyclerViewInput(data)




    }
//
//    fun recyclerViewInput(data : ArrayList<HomeDummyData>){
//        val recycle : RecyclerView = findViewById(R.id.recycleView_SearchResult)
//        val adapter = SearchResultAdapter(data, this)
//        recycle.layoutManager = GridLayoutManager(this, 3)
//        recycle.adapter = adapter
//
//    }

    override fun onItemClicked(position: Int) {
        var manga = data[position]
        val intent = Intent(this, DetailMangaActivity::class.java)
        intent.putExtra("mangaTitle", manga.title)
        intent.putExtra("mangaImage", manga.img)
        startActivity(intent)

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent : Intent) {
        if(Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show()

        }
    }

}