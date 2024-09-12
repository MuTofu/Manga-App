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
import com.example.mangaapp.datamodel.HomeDummyData
import com.example.mangaapp.R
import com.example.mangaapp.datamodel.list.Manga
import com.example.mangaapp.datamodel.list.MangaData

class BrowserAdapter(private val listener : RecycleEvent) : RecyclerView.Adapter<BrowserAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val title : TextView
        val imageCard : ImageView

        init {
            title = view.findViewById(R.id.fetch_title_manga)
            imageCard = view.findViewById(R.id.fetch_cardImg)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
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
        set(value) = differ.submitList(value)




    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.fetch_manga, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(viewHolder : ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            var manga  = list[position]
            viewHolder.title.text = manga.title
            viewHolder.imageCard.load(manga.image)
        }
    }



    interface RecycleEvent {
        fun onItemClick(position: Int)
    }
}