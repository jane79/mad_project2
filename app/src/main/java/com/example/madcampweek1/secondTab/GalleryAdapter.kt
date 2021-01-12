package com.example.madcampweek1.secondTab

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.Target
import com.example.madcampweek1.R
import kotlinx.android.synthetic.main.gallery_item.view.*


data class Image(val name: String?, val file: String?)

class GalleryAdapter(private val galleryList: ArrayList<GalleryItem>) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.gallery_item,
            parent, false
        )
        return GalleryViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val currentItem = galleryList[position]
        val listener = View.OnClickListener { it ->
            val intent = Intent(it.context, GalleryShowActivity::class.java)
            intent.putExtra("Uri", currentItem.imageResource.toString())
            intent.putExtra("Name", currentItem.title)
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
            view.thumbnail.setImageURI(item.imageResource)
            view.setOnClickListener(listener)
        }
    }

    
}
