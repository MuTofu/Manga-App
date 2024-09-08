package com.example.mangaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter(private val dataset: ArrayList<HomeData>, private val myListener : RecyclerViewEvent) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val textView: TextView
        val imageCard : ImageView


        init {
            textView = view.findViewById(R.id.fetch_title_manga)
            imageCard = view.findViewById(R.id.fetch_cardImg)
            view.setOnClickListener(this)

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                myListener.onItemClick(position)



            }

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewtype: Int) : ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.fetch_manga, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var item = dataset[position]
        viewHolder.textView.text = item.title
        viewHolder.imageCard.setImageResource(item.img)


    }

    override fun getItemCount() = dataset.size


    interface RecyclerViewEvent {
        fun onItemClick(position : Int)
    }



}