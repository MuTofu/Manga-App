package com.example.mangaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp.datamodel.detail.DetailMangaDummyData
import com.example.mangaapp.R

class DetailChapterAdapter(private val data: ArrayList<DetailMangaDummyData>, private val listener : RecyclerListener) : RecyclerView.Adapter<DetailChapterAdapter.ViewHolder>() {


        inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val chapter : TextView
        val release : TextView

        init {
            chapter = view.findViewById(R.id.chapterEps)
            release = view.findViewById(R.id.releaseCh)
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }

    }
//
//    override fun onCreateViewHolder(grupView: ViewGroup, tipeView: Int): HomeAdapter.ViewHolder {
//        val view = LayoutInflater.from(grupView.context).inflate(R.layout.fetch_chapter, grupView, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(viewHolder: HomeAdapter.ViewHolder, posisi: Int) {
//        var item = data[posisi]
//        viewHolder.textView.text = item.chapter
//        viewHolder.textView.text = item.chapter
//    }
//
//
//    override fun getItemCount(): Int {
//        return data.size
//    }

    interface RecyclerListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.fetch_chapter, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, posisi: Int) {
        var item = data[posisi]
        viewHolder.chapter.text = item.chapter
        viewHolder.release.text = item.rilis
    }

    override fun getItemCount(): Int {
        return data.size
    }


}