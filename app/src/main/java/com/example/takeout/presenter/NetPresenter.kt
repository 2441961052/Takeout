package com.example.takeout.presenter

import android.util.Log
import com.example.takeout.model.net.ResponseInfo
import com.example.takeout.model.net.TakeoutService
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit


abstract class NetPresenter {
    val service: TakeoutService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.119:8080/Takeout/")
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(RxJavaCallAdapterFactory.create() as CallAdapter.Factory)
            .build()
        service = retrofit.create(TakeoutService::class.java)
    }
    abstract fun parserjson(json: String)

    val callBack = object : Callback<ResponseInfo> {
        override fun onResponse(response: Response<ResponseInfo>, retrofit: Retrofit?) {
            if (response.isSuccess) {
                val body = response.body()
                if (body.code.equals("0")) {
                    val json = body.data
                    parserjson(json)
                } else {
                    Log.i("NetPresenter", "onResponse: 代码错误")
                }
            } else {
                Log.i("NetPresenter", "onResponseFail ")
            }
        }
        override fun onFailure(t: Throwable?) {
            Log.i("TAG", "onFailure: ")
        }
    }
}

