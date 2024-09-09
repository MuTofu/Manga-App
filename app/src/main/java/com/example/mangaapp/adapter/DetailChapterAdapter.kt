package com.example.mangaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp.datamodel.detail.DetailMangaDummyData
import com.example.mangaapp.R
import com.example.mangaapp.datamodel.detail.Chapter

class DetailChapterAdapter : RecyclerView.Adapter<DetailChapterAdapter.ChapterViewHolder>() {

    inner class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleChapter = itemView.findViewById<TextView>(R.id.chapterEps)
        val chapterUpdate = itemView.findViewById<TextView>(R.id.releaseCh)

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Chapter>() {
        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)
    var list : List<Chapter>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fetch_chapter, parent, false)
        return ChapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.apply {
            val manga = list[position]
            holder.titleChapter.text = manga.name
            holder.chapterUpdate.text = manga.createdAt
        }
    }



}