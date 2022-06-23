package com.example.takeout.model.utils

import android.app.Application
import android.util.Log
import com.example.takeout.model.bean.CashSelectInfo
import com.example.takeout.model.bean.User
import com.igexin.sdk.PushManager
import java.util.concurrent.CopyOnWriteArrayList


class TakeoutApp :Application(){
    var infos:CopyOnWriteArrayList<CashSelectInfo> = CopyOnWriteArrayList()
    fun queryCacheSelectedInfoByGoodsId(goodsId: Int): Int {
        var count = 0
        for (i in 0 until infos.size) {
            val info: CashSelectInfo = infos.get(i)
            if (info.goodsId === goodsId) {
                count = info.count
                break
            }
        }
        return count
    }

    fun queryCacheSelectedInfoByTypeId(typeId: Int): Int {
        var count = 0
        for (i in 0 until infos.size) {
            val info: CashSelectInfo = infos.get(i)
            if (info.goodsTypeId === typeId) {
                count = count + info.count
            }
        }
        return count
    }

    fun queryCacheSelectedInfoBySellerId(sellerId: Int): Int {
        var count = 0
        for (i in 0 until infos.size) {
            val info: CashSelectInfo = infos.get(i)
            if (info.sellerId === sellerId) {
                count = count + info.count
            }
        }
        return count
    }

    fun addCacheSelectedInfo(info: CashSelectInfo?) {
        infos.add(info)
    }

    fun clearCacheSelectedInfo(sellerId: Int) {
        for (i in 0 until infos.size) {
            val info: CashSelectInfo = infos.get(i)
            if (info.sellerId === sellerId) {
                infos.remove(info)
            }
        }
    }

    fun deleteCacheSelectedInfo(goodsId: Int) {
        for (i in 0 until infos.size) {
            val info: CashSelectInfo = infos.get(i)
            if (info.goodsId === goodsId) {
                infos.remove(info)
                break
            }
        }
    }

    fun updateCacheSelectedInfo(goodsId: Int, operation: Int) {
        for (i in 0 until infos.size) {
            val info: CashSelectInfo = infos.get(i)
            if (info.goodsId === goodsId) {
                when (operation) {
                    Constants.ADD -> info.count = info.count + 1
                    Constants.MINUS -> info.count = info.count - 1
                }
            }
        }
    }
    companion object{
        var suser:User = User()
        lateinit var sInstance:TakeoutApp
    }
    //应用程序的入口
    override fun onCreate() {
        super.onCreate()
        sInstance = this
        suser.id = -1
        PushManager.getInstance().initialize(this)
        PushManager.getInstance().setDebugLogger(
            this
        ) { s -> Log.i("TAG", s) }
    }
}
