package com.example.takeout.presenter

import com.example.takeout.model.bean.GoodsInfo
import com.example.takeout.model.bean.GoodsTypeInfo
import com.example.takeout.ui.fragment.GoodsFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class GoodsPresenter(var goodsFragment: GoodsFragment) : NetPresenter() {
    val allTypeList: ArrayList<GoodsInfo> = arrayListOf()
    var goodList: List<GoodsTypeInfo> = arrayListOf()
    fun getGoodInfo(sellerId: String) {
        val goodsCallBack = service.getGoodsInfo(sellerId)
        goodsCallBack.enqueue(callBack)
    }

    override fun parserjson(json: String) {
        val gson = Gson()
        val jsonObject = JSONObject(json)
        val allList = jsonObject.getString("list")
       // val hasCashInfo = (goodsFragment.activity as BussinessActivity).hasExtra

        goodList =
            gson.fromJson(allList, object : TypeToken<List<GoodsTypeInfo>>() {}.type)
        for (i in goodList.indices) {
            val aType = goodList.get(i).list
            var atypeCount=0
//            if(hasCashInfo){
//                atypeCount =
//                    TakeoutApp.sInstance.queryCacheSelectedInfoByTypeId(goodList.get(i).id)
//                goodList[i].tvRedDotCount=atypeCount
//            }
            for (j in aType.indices) {
                val agoodsInfo = aType.get(j)
//                if(atypeCount>0){
//                    val count =
//                        TakeoutApp.sInstance.queryCacheSelectedInfoByGoodsId(agoodsInfo.id)
//                    agoodsInfo.count=count
//                }
                agoodsInfo.typeName = goodList.get(i).name
                agoodsInfo.typeId = goodList.get(i).id

            }
            allTypeList.addAll(aType)

            // Log.i("TAG_GoodPresenter", "第${i}个list的元素为${goodList.get(i).list}")
        }

        //  Log.i("TAG_GoodPresenter", "一共有${goodList.size}种类")

        goodsFragment.onLoadDataSuccess(goodList, allTypeList)
        //goodsFragment.onLoadDataFailed()
    }

    fun getGoodsPosition(typeId: Int): Int {
        var position = -1
        for (k in allTypeList.indices) {
            val goodInfo = allTypeList[k]
            if (goodInfo.typeId == typeId) {
                position = k
                break
            }
        }
        return position
    }

    fun getPositionByTypeId(newTypeId: Int): Int {
        var position = -1
        for (m in goodList.indices) {
            val goodInfo = goodList[m]
            if (goodInfo.id == newTypeId) {
                position = m
                break
            }
        }
        return position
    }

    fun getShoppingCarList(): ArrayList<GoodsInfo> {
        var shoppingList: ArrayList<GoodsInfo> = arrayListOf()
        for (k in allTypeList.indices) {
            val goodInfo = allTypeList[k]
            if (goodInfo.count > 0) {
                shoppingList.add(goodInfo)
            }
        }
        return shoppingList
    }

    fun clearCar() {
        var shoppingList: ArrayList<GoodsInfo> = arrayListOf()
        for (k in allTypeList.indices) {
            val goodInfo = allTypeList[k]
            goodInfo.count = 0
        }
    }
}