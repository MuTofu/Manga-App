package com.example.mangaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mangaapp.R
import com.example.mangaapp.datamodel.chapter.Image

class ReadPageAdapter : RecyclerView.Adapter<ReadPageAdapter.ReadViewHolder>()  {
    inner class ReadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pageImage = itemView.findViewById<ImageView>(R.id.imgPage)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, diffCallback)
    var list : List<Image>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ReadViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.fetch_imgpage, viewGroup, false)
        return ReadViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ReadViewHolder, position: Int) {
        holder.apply {
            val imagePage = list[position]
            holder.pageImage.load(imagePage.image)
        }
    }


}