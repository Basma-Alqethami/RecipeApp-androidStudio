package com.example.recipeapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    @GET("recipes/")
    fun getData(): Call<recipeData>

    @POST("recipes/")
    fun postData(@Body userData: recipeDataItem): Call<recipeDataItem>
}