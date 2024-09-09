package com.example.mangaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp.datamodel.HomeDummyData
import com.example.mangaapp.R

class SearchResultAdapter(private val data : ArrayList<HomeDummyData>, private val listener : RecyclerViewEvent) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    interface RecyclerViewEvent {
        fun onItemClicked(position : Int)
    }


    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val title : TextView
        val image : ImageView

        init {
            title = view.findViewById(R.id.fetch_title_manga)
            image = view.findViewById(R.id.fetch_cardImg)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClicked(position)
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.fetch_manga, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var item = data[position]
        viewHolder.title.text = item.title
        viewHolder.image.setImageResource(item.img)
    }

    override fun getItemCount(): Int {
        return  data.size
    }
}