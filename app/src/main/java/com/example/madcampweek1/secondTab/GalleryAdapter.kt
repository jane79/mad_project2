package com.example.madcampweek1

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.gallery_item.view.*
import java.io.ByteArrayOutputStream

class GalleryAdapter(private val galleryList: ArrayList<GalleryItem>) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryAdapter.GalleryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.gallery_item,
            parent, false
        )
        return GalleryAdapter.GalleryViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: GalleryAdapter.GalleryViewHolder, position: Int) {
        val currentItem = galleryList[position]

        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "Clicked: ${currentItem.title}", Toast.LENGTH_SHORT).show()

            val intent = Intent(it.context, GalleryShowActivity::class.java)
            intent.putExtra("Drawable", currentItem.imageResource)
            it.context.startActivity(intent)
        }
        holder.apply {
            bind(listener, currentItem)
            itemView.tag = currentItem
        }
    }
    override fun getItemCount() = galleryList.size

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView
        fun bind(listener: View.OnClickListener, item: GalleryItem){
            view.thumbnail.setImageResource(item.imageResource)
            view.setOnClickListener(listener)
        }
    }
}