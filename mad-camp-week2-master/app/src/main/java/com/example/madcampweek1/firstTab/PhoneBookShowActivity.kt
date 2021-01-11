package com.example.madcampweek1.firstTab

import android.animation.Animator
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.madcampweek1.R
import com.example.madcampweek1.retrofitTab.PhoneInf
import com.example.madcampweek1.retrofitTab.PhoneService
import kotlinx.android.synthetic.main.activity_phone_book_show.*
import kotlinx.android.synthetic.main.activity_phone_book_show.phone_show_image
import kotlinx.android.synthetic.main.activity_phone_book_show.phone_show_name
import kotlinx.android.synthetic.main.activity_phone_book_show.phone_show_phone_btn
import kotlinx.android.synthetic.main.activity_phone_book_show.phone_show_phone_number
import kotlinx.android.synthetic.main.activity_phone_book_show.view.*
import kotlinx.android.synthetic.main.activity_phone_db_show.*
import kotlinx.android.synthetic.main.fragment_phone_book.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhoneBookShowActivity : AppCompatActivity() {

    var phoneInf:PhoneInf? = null

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

        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.249.18.225:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var phoneService: PhoneService = retrofit.create(PhoneService::class.java)


        phone_show_upload.setOnClickListener {
            var text1 = name.toString()
            var text2 = phoneNumber.toString()
            phoneService.phoneUpload(text1,text2).enqueue(object: Callback<PhoneInf> {
                override fun onFailure(call: Call<PhoneInf>, t: Throwable) {
                    Log.d("fail", t.toString())
                    var dialog = AlertDialog.Builder(this@PhoneBookShowActivity)
                    dialog.setMessage("호출 실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(call: Call<PhoneInf>, response: Response<PhoneInf>) {
                    phoneInf = response.body()
                    Log.d("fail", phoneInf.toString())
                    var dialog = AlertDialog.Builder(this@PhoneBookShowActivity)
                    dialog.setMessage("업로드 성공!")
                    dialog.show()
                }
            })
        }
    }
}
