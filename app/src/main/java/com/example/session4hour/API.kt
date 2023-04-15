package com.example.session4hour

import com.example.session4hour.Modules.INFO
import com.example.session4hour.Modules.Lesson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface API {
    @GET("workSpace/plan")
    fun getPlan(@Header("idUser")id:String = INFO.idUser,@Query("date")date:String? = null,@Query("direction")direction:String? = null): Call<List<Lesson>>

    @POST("workSpace/confirmLesson")
    fun postConfirm(@Header("idUser")id:Int,@Query("idLesson")idLesson:String,@Query("commentFile")commentFile:String,@Query("direction")direction:String?):Call<Boolean>

    @GET("workSpace/delayLessons")
    fun getDelay(@Header("idUser")id:Int,):Call<List<Lesson>>
}