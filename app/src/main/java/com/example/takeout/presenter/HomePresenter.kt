package com.example.takeout.presenter

import com.example.takeout.model.bean.Seller
import com.example.takeout.ui.fragment.HomeFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject


class HomePresenter(private val homeFragment: HomeFragment):NetPresenter() {
    fun getHomeInfo() {
        val homeInfo = service.getHomeInfo()
        homeInfo.enqueue(callBack)
    }

    override fun parserjson(json: String) {
        val gson = Gson()
        val jsonObject = JSONObject(json)
        val nearbySellerList = jsonObject.getString("nearbySellerList")
        val nearbyList: List<Seller> =
            gson.fromJson(nearbySellerList, object : TypeToken<List<Seller>>() {}.type)
        val otherSellerList = jsonObject.getString("otherSellerList")
        val otherList: List<Seller> =
            gson.fromJson(otherSellerList, object : TypeToken<List<Seller>>() {}.type)

        if (nearbyList.isNotEmpty() || otherList.isNotEmpty()) homeFragment.onSuccess(nearbyList,otherList)
        else homeFragment.onFalied()

    }
}
