package com.example.madcampweek1.firstTab

import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcampweek1.R
import com.example.madcampweek1.retrofitTab.PhoneInf
import com.example.madcampweek1.retrofitTab.PhoneService
import kotlinx.android.synthetic.main.activity_phone_database.*
import kotlinx.android.synthetic.main.fragment_phone_book.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhoneDatabaseActivity : AppCompatActivity() {

    lateinit var mAdapter: PhoneDatabaseAdapter
    var dblist = mutableListOf<Phone>()

    var ArrayphoneInf:Array<PhoneInf>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_database)
        setContentView()
    }

    private fun setContentView(){

        val list = mutableListOf<Phone>()
        fun getInfo() {
            var retrofit = Retrofit.Builder()
                .baseUrl("http://192.249.18.217:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            var phoneService: PhoneService = retrofit.create(PhoneService::class.java)

            Log.d("failhear1", "hear")
            phoneService.phoneDownloadAll().enqueue(object : Callback<Array<PhoneInf>> {
                override fun onFailure(call: Call<Array<PhoneInf>>, t: Throwable) {
                    Log.d("fail1", t.toString())
                }

                override fun onResponse(
                    call: Call<Array<PhoneInf>>,
                    response: Response<Array<PhoneInf>>
                ) {
                    ArrayphoneInf = response.body()
                    var i = 0
                    val len = ArrayphoneInf?.size
                    while (i < len!!) {
                        list.add(
                            Phone(
                                "id",
                                ArrayphoneInf?.get(i)?.name.toString(),
                                ArrayphoneInf?.get(i)?.number.toString()
                            )
                        )
                        Log.d("fail2", list.toString() + i.toString())
                        i++
                    }
                    Log.d("failhear333", list.toString())
                    dblist.addAll(list)
                    mAdapter = PhoneDatabaseAdapter(dblist)
                    recycler.adapter = mAdapter
                    recycler.layoutManager = LinearLayoutManager(this@PhoneDatabaseActivity)
                }
            })
        }
        getInfo()
    }
}
