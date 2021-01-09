package com.example.madcampweek1.firstTab

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.madcampweek1.R
import com.example.madcampweek1.retrofitTab.RetrofitClient
import com.example.madcampweek1.retrofitTab.RetrofitManager
import com.example.madcampweek1.utils.RESPONSE_STATE
import kotlinx.android.synthetic.main.activity_phone_book_show.*
import kotlinx.android.synthetic.main.activity_phone_book_show.view.*

class PhoneBookShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_book_show)

        val phoneBookShowImage: ImageView = phone_show_image
        val phoneBookShowName: TextView = phone_show_name
        val phoneBookShowNum: TextView = phone_show_phone_number

        val bundle: Bundle = intent.extras!!

        val profileImage = bundle.getInt("ProfileImage")
        val colorFilter = bundle.getString("ColorFilter")
        val name = bundle.getString("Name")
        val phoneNumber = bundle.getString("PhoneNumber")

        phoneBookShowImage.setImageResource(profileImage)
        phoneBookShowImage.setColorFilter(Color.parseColor(colorFilter))
        phoneBookShowName.text = name
        phoneBookShowNum.text = phoneNumber

        phone_show_phone_btn.setOnClickListener{
            val uri = Uri.parse("tel:${phoneNumber.toString()}")
            val intent = Intent(Intent.ACTION_CALL, uri)
            startActivity(intent)
        }

        phone_show_upload.setOnClickListener{
            RetrofitManager.instance.searchPhotos(phoneNumber.toString(), completion = {
                responseState, s ->
                when(responseState) {
                    RESPONSE_STATE.OKAY -> {
                        Log.d("test", "api upload 성공")
                    }
                    RESPONSE_STATE.FAIL -> {
                        Log.d("test", "api upload 실패")
                    }
                }
            })
        }


        phone_show_delete.setOnClickListener{
            RetrofitManager.instance.postToken(phoneNumber.toString(), completion = {
                    responseState, s ->
                when(responseState) {
                    RESPONSE_STATE.OKAY -> {
                        Log.d("test", "api delete 성공")
                    }
                    RESPONSE_STATE.FAIL -> {
                        Log.d("test", "api delete 실패")
                    }
                }
            })
        }

    }
}
