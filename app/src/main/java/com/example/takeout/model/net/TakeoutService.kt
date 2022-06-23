package com.example.takeout.model.net

import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query


interface TakeoutService {
//    @GET("users/{user}/repos")
//    fun listRepos(@Path("user") user: String?): Call<List<Repo?>?>?

    @GET("home")
    fun getHomeInfo():Call<ResponseInfo>
    @GET("login")
    fun loginByPhone(@Query("phone") phone: String):Call<ResponseInfo>
    @GET("order")
    fun getOrder(@Query("id")userId: String):Call<ResponseInfo>
    @GET("business")
    fun getGoodsInfo(@Query("sellerId")sellerId: String):Call<ResponseInfo>




}