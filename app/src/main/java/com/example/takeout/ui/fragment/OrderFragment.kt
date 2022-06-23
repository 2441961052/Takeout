package com.example.takeout.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.takeout.R
import com.example.takeout.model.bean.Order
import com.example.takeout.model.utils.TakeoutApp
import com.example.takeout.presenter.OrderPresenter
import com.example.takeout.ui.adapter.OrderAdapter
import org.jetbrains.anko.find

class OrderFragment :Fragment(){
    lateinit var swipLayout:SwipeRefreshLayout
    lateinit var rvOrder:RecyclerView
    lateinit var presenter: OrderPresenter
    lateinit var adapter:OrderAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val orderView = View.inflate(activity, R.layout.fragment_order,null)
        rvOrder = orderView.find(R.id.rv_order_list)
        swipLayout=orderView.find(R.id.srl_order)
        swipLayout.setOnRefreshListener {
            val userid = TakeoutApp.suser.id
            if(userid.equals(0)){

                Toast.makeText(context, "请登录后查看订单", Toast.LENGTH_SHORT).show()
            }else{
                presenter.getOrder(userid.toString())
            }
        }
        rvOrder.layoutManager = LinearLayoutManager(activity)
        presenter = OrderPresenter(this)
        adapter=OrderAdapter(activity!!)
        rvOrder.adapter=adapter
        return orderView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val userid = TakeoutApp.suser.id
        if(userid.equals(0)){

            Toast.makeText(context, "请登录后查看订单", Toast.LENGTH_SHORT).show()
        }else{
            presenter.getOrder(userid.toString())
        }

    }

    fun getOrderSuccess(orderList: List<Order>) {
        adapter.setData(orderList)
        swipLayout.isRefreshing=false
    }

    fun getOrderFailed(){
        Toast.makeText(context, "获取失败", Toast.LENGTH_SHORT).show()
        swipLayout.isRefreshing=false
    }

}