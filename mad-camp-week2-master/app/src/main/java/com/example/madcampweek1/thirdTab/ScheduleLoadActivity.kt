package com.example.madcampweek1.thirdTab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcampweek1.R
import com.example.madcampweek1.firstTab.Phone
import com.example.madcampweek1.firstTab.PhoneDatabaseAdapter
import com.example.madcampweek1.retrofitTab.ScheduleInf
import com.example.madcampweek1.retrofitTab.ScheduleService
import kotlinx.android.synthetic.main.activity_phone_database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScheduleLoadActivity : AppCompatActivity() {

    lateinit var mAdapter: PhoneDatabaseAdapter
    var ArrscheduleInf: Array<ScheduleInf>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_load)

        val bundle: Bundle = intent.extras!!
        val groupName = bundle.getString("name")



        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.249.18.225:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var scheduleService: ScheduleService = retrofit.create(ScheduleService::class.java)

        if (groupName != null) {
            scheduleService.scheduleDownloadAll(groupName)
                .enqueue(object : Callback<Array<ScheduleInf>> {

                    override fun onFailure(call: Call<Array<ScheduleInf>>, t: Throwable) {
                        Log.d("fail", t.toString())
                        var dialog = AlertDialog.Builder(this@ScheduleLoadActivity)
                        dialog.setMessage("로드 실패했습니다.")
                        dialog.show()
                    }

                    override fun onResponse(
                        call: Call<Array<ScheduleInf>>,
                        response: Response<Array<ScheduleInf>>
                    ) {
                        var schedulelist = mutableListOf<Phone>()
                        ArrscheduleInf = response.body()
                        Log.d("fail333", ArrscheduleInf.toString())
                        //                var dialog = AlertDialog.Builder(requireContext())
                        //                dialog.setMessage("로드 성공!")
                        //                dialog.show()

                        var i = 0
                        val len = ArrscheduleInf?.size
                        while (i < len!!) {
                            schedulelist.add(
                                Phone(
                                    "id",
                                    ArrscheduleInf?.get(i)?.name.toString(),
                                    ArrscheduleInf?.get(i)?.name.toString()
                                )
                            )
                            Log.d("hearhear", i.toString())
                            i++
                        }
                        mAdapter = PhoneDatabaseAdapter(schedulelist)
                        recycler.adapter = mAdapter
                        recycler.layoutManager = LinearLayoutManager(this@ScheduleLoadActivity)
                    }
                })
        }
    }
}