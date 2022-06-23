package com.example.takeout.presenter

import com.example.takeout.model.bean.Order
import com.example.takeout.ui.fragment.OrderFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OrderPresenter(var orderFragment:OrderFragment): NetPresenter() {

    fun getOrder(userId:String){
        val order = service.getOrder(userId)
        order.enqueue(callBack)
    }
    override fun parserjson(json: String) {
        val orderList:List<Order> = Gson().fromJson(json, object : TypeToken<List<Order>>() {}.type)
        if(orderList.isNotEmpty()){
            orderFragment.getOrderSuccess(orderList)
        }else{
            orderFragment.getOrderFailed()
        }
    }
}