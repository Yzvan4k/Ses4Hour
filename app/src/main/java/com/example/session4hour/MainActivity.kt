package com.example.session4hour

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.session4hour.Modules.Lesson
import com.example.session4hour.Modules.ModelError
import com.example.session4hour.databinding.ActivityMainBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

// Данный класс опиcывает Гл
// 15.04.23
// Yzvan
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    //private lateinit var delaySes: DelayAdapter
    //private lateinit var data: List<Lesson>
    private lateinit var adapter: DelayAdapter
    private lateinit var planAdapter: PlanAdapter

    private var date:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = DelayAdapter(listOf())
        planAdapter = PlanAdapter(listOf())

        binding.delaySes.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.recPlan.apply {
            planAdapter = this@MainActivity.planAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.prev.setOnClickListener {
            refreshContent(direction = "next")
        }
        binding.next.setOnClickListener {
            refreshContent(direction = "next")
        }

        fillDelaySes()
    }

    private fun refreshContent(direction: String? = null) {
        Connection.api.getPlan(date!!,direction).enqueue(object :retrofit2.Callback<List<Lesson>>{
            override fun onResponse(call: Call<List<Lesson>>, response: Response<List<Lesson>>) {
                if (response.body() != null){
                    val lessons = response.body()!!
                    if (lessons.isNotEmpty()){
                        val dateStr = lessons[0].datetime.substringBefore(' ')
                        date = dateStr
                        setTimeStamp(dateStr)
                        binding.date.text = dateStr
                        planAdapter.setData(lessons)
                        binding.emptyPlan.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<List<Lesson>>, t: Throwable) {
                showError(t.localizedMessage ?: t.message ?: "Неизвестная ошибка")
            }
        })

    }

    private fun fillPlan(){
        Connection.api.getPlan("6",null,null).enqueue(object :retrofit2.Callback<List<Lesson>>{
            override fun onResponse(call: Call<List<Lesson>>, response: Response<List<Lesson>>) {
                if (response.body() != null){
                    val lessons = response.body()!!
                    if (lessons.isNotEmpty()){
                        planAdapter.setData(lessons)
                        binding.emptyPlan.visibility = View.GONE
                    }else{
                        binding.emptyPlan.visibility = View.VISIBLE
                    }

                    }
            }

            override fun onFailure(call: Call<List<Lesson>>, t: Throwable) {
                showError(t.localizedMessage ?: t.message ?: "Неизвестная ошибка")
            }
        })
    }

    private fun fillDelaySes() {
        Connection.api.getDelay(6).enqueue(object:retrofit2.Callback<List<Lesson>>{
            override fun onResponse(call: Call<List<Lesson>>, response: Response<List<Lesson>>) {
                if (response.body() != null){
                    val lessons = response.body()!!
                    if (lessons.isNotEmpty()){
                        adapter.setData(lessons)
                        binding.linearLayout.visibility = View.VISIBLE
                    }else{
                        binding.linearLayout.visibility = View.GONE
                    }
                }else{
                    if (response.errorBody() != null){
                        val error = Gson().fromJson(response.errorBody()!!.toString(),ModelError::class.java)
                        showError(error.error)
                    }else
                        showError("Body bull")
                }
            }

            override fun onFailure(call: Call<List<Lesson>>, t: Throwable) {
                showError(t.localizedMessage ?: t.message ?: "Неизвестная ошибка")
            }
        })
    }
    private fun setTimeStamp(dateStr:String){
        val date : Date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(dateStr)!!
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)
        val nowDate: Date = calendar.time
        val diffUnixTimeMells = date.time - nowDate.time
        val diffSeconds = diffUnixTimeMells/ 1000
        val diffMinutes = diffSeconds / 60
        val diffHours = diffMinutes / 60
        val diffDays = (diffHours / 24).toInt()

        binding.timeStamp.visibility = View.VISIBLE

        when (diffDays){
            0 -> binding.timeStamp.text = "Cегодня"
            1 -> binding.timeStamp.text = "Завтра"
            else -> binding.timeStamp.visibility = View.GONE
        }
    }
}
fun Context.showError(error: String){
    AlertDialog.Builder(this)
        .setTitle("Ошибка")
        .setMessage(error)
        .setNegativeButton("Повторить еще раз", null)
        .create()
        .show()
}