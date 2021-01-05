package com.example.madcampweek1.secondTab

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.madcampweek1.R
import kotlinx.android.synthetic.main.gallery_show_item.view.*

class GalleryShowAdapter(private val galleryShowList: ArrayList<GalleryItem>) : RecyclerView.Adapter<GalleryShowAdapter.GalleryShowViewHolder>() {

    interface  ItemClick {
        fun onClick(view: View, item: GalleryItem)
    }
    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryShowViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gallery_show_item,
            parent, false
        )
        return GalleryShowViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GalleryShowViewHolder, position: Int) {
        val currentItem = galleryShowList[position]

//        holder.setItem(currentItem)
        val listener = View.OnClickListener { it ->
//            Toast.makeText(it.context, "Clicked: ${currentItem.title}", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            setItem(listener, currentItem)
            itemView.tag = currentItem
        }

        if(itemClick != null) {
            holder?.itemView?.setOnClickListener { v ->
                itemClick?.onClick(v, currentItem)
            }
        }
    }

    override fun getItemCount() = galleryShowList.size

    class GalleryShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setItem(listener: View.OnClickListener, item: GalleryItem) {
            itemView.show_view.setImageResource(item.imageResource)
            itemView.setOnClickListener(listener)
        }
    }
}
