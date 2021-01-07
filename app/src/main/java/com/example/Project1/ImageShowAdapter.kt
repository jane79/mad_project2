package com.example.project1

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.image_show_item.view.*

class ImageShowAdapter (private val imageShowList : ArrayList<String>) :
    RecyclerView.Adapter<ImageShowAdapter.ImageShowViewHolder>() {
    interface ItemClick{
        fun onClick(view: View, item: String)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType :Int): ImageShowViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.image_show_item,
            parent, false
        )
        return ImageShowViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageShowViewHolder, position: Int) {
        val currentItem = imageShowList[position]
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

    override fun getItemCount(): Int = imageShowList.size

    class ImageShowViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun setItem(listener : View.OnClickListener, item: String){
            val uri = Uri.parse(item)
            itemView.show_view.setImageURI(uri)
            itemView.setOnClickListener(listener)
            //val decode = ImageDecoder.createSource(context.contentResolver, uri)
            //val bitmap = ImageDecoder.decodeBitmap(decode)
            //itemView.show_view.setImageBitmap(bitmap)
            //itemView.show_view.setImageResource(Uri.parse(item))
        }
    }
}