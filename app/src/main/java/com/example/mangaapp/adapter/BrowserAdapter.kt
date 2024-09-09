package com.example.mangaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp.datamodel.HomeDummyData
import com.example.mangaapp.R

class BrowserAdapter(private val dataset: ArrayList<HomeDummyData>, private val listener : RecycleEvent) : RecyclerView.Adapter<BrowserAdapter.ViewHolder>() {

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

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.fetch_manga, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder : ViewHolder, position: Int) {
        var item = dataset[position]
        viewHolder.title.text = item.title
        viewHolder.imageCard.setImageResource(item.img)
    }

    override fun getItemCount() = dataset.size

    interface RecycleEvent {
        fun onItemClick(position: Int)
    }
}