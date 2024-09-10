package com.example.mangaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mangaapp.R
import com.example.mangaapp.datamodel.list.Manga

class HomeOngoingAdapter(private val listener : RecycleEvent) : RecyclerView.Adapter<HomeOngoingAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title = itemView.findViewById<TextView>(R.id.fetch_title_manga)
        val cardImage = itemView.findViewById<ImageView>(R.id.fetch_cardImg)

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Manga>() {
        override fun areItemsTheSame(oldItem: Manga, newItem: Manga): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Manga, newItem: Manga): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)
    var list : List<Manga>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(view.context).inflate(R.layout.fetch_manga, view, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.itemView.apply {
            val manga = list[position]
            holder.title.text = manga.title
            holder.cardImage.load(manga.image)
        }
    }

    interface RecycleEvent{
        fun onItemClick(position: Int)
    }
}