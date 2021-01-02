package com.example.madcampweek1.firstTab

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madcampweek1.R
import kotlinx.android.synthetic.main.contact_item.view.*

data class Phone(val id:String?, val name:String?, val phone:String?)

class PhoneAdapter(val list:List<Phone>) : RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val phone = list[position]
        holder.setPhone(phone)
    }
}

@SuppressLint("MissingPermission")
class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mPhone: Phone? = null
    init {
        itemView.btnPhone.setOnClickListener {
            mPhone?.phone.let { phoneNumber ->
                val uri = Uri.parse("tel:${phoneNumber.toString()}")
                val intent = Intent(Intent.ACTION_CALL, uri)
                itemView.context.startActivity(intent)
            }
        }
    }
    fun setPhone(phone: Phone) {
        this.mPhone = phone
        var num = 0
        try {
            num = Character.getNumericValue(phone.phone.toString().last())
        } catch (nfe: NumberFormatException) {
            // not a valid int
        }

        when(num%3) {
            0 -> itemView.image_view.setColorFilter(Color.parseColor("#db6f48"))
            1 -> itemView.image_view.setColorFilter(Color.parseColor("#d8db48"))
            else -> itemView.image_view.setColorFilter(Color.parseColor("#48d5cc"))
        }
        itemView.textName.text = phone.name
        itemView.textPhone.text = phone.phone
    }
}