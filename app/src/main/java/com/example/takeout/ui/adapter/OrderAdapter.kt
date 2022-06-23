package com.example.takeout.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takeout.R
import com.example.takeout.model.bean.Order
import com.example.takeout.model.utils.OrderObservable
import org.jetbrains.anko.find
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class OrderAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),Observer{
    init {
        OrderObservable.instance.addObserver(this)
    }
    override fun update(obsever: Observable?, data: Any?) {
        Log.i("TAG", "update: $data")
        val jsonObject = JSONObject(data as String)
        val pushId =jsonObject.getString("orderId")
        val pushStatus = jsonObject.getString("type")
        for (i in orderList.indices){
            val order =orderList.get(i)
            if(order.id.equals(pushId)){
                order.type=pushStatus
            }
        }
        notifyDataSetChanged()
    }
    private var orderList:List<Order> = ArrayList()
    fun setData(orders:List<Order>){
        this.orderList=orders
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Holder控制ItemView 1:先找到itemView 2:针对itemView创建Holder

        //此方法不能填充满
        // val itemView = View.inflate(context, R.layout.item_order_item,null)
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_order_item,parent,false)
        return orderItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as orderItemHolder).bindData(orderList.get(position))
    }

    override fun getItemCount(): Int = orderList.size

    inner class orderItemHolder(var item:View):RecyclerView.ViewHolder(item) {
        var orderName:TextView
        var orderStatus:TextView
        init {
            orderName = item.find(R.id.tv_order_item_seller_name)
            orderStatus=item.find(R.id.tv_order_item_type)
        }
        fun bindData(order: Order) {
            orderName.text =order.seller.name
            orderStatus.text = getOrderTypeInfo(order.type)
        }
    }

    private fun getOrderTypeInfo(type: String): String? {
        /**
         * 订单状态
         * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单
         */
//            public static final String ORDERTYPE_UNPAYMENT = "10";
//            public static final String ORDERTYPE_SUBMIT = "20";
//            public static final String ORDERTYPE_RECEIVEORDER = "30";
//            public static final String ORDERTYPE_DISTRIBUTION = "40";
//            public static final String ORDERTYPE_SERVED = "50";
//            public static final String ORDERTYPE_CANCELLEDORDER = "60";
        var typeInfo = ""
        when (type) {
            OrderObservable.ORDERTYPE_UNPAYMENT -> typeInfo = "未支付"
            OrderObservable.ORDERTYPE_SUBMIT -> typeInfo = "已提交订单"
            OrderObservable.ORDERTYPE_RECEIVEORDER -> typeInfo = "商家接单"
            OrderObservable.ORDERTYPE_DISTRIBUTION -> typeInfo = "配送中"
            OrderObservable.ORDERTYPE_SERVED -> typeInfo = "已送达"
            OrderObservable.ORDERTYPE_CANCELLEDORDER -> typeInfo = "取消的订单"
        }
        return typeInfo
    }


}