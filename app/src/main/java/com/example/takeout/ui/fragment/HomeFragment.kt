package com.example.takeout.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takeout.R
import com.example.takeout.model.bean.Seller
import com.example.takeout.presenter.HomePresenter
import com.example.takeout.ui.adapter.HomeAdapter


class HomeFragment :Fragment(){
    lateinit var homeAdapter:HomeAdapter
    lateinit var rv_home: RecyclerView
    lateinit var container1:LinearLayout
    lateinit var presenter: HomePresenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View =  View.inflate(activity, R.layout.fragment_home,null)
        rv_home = view.findViewById<RecyclerView>(R.id.rv_home)
        container1=view.findViewById(R.id.ll_title_container)
        rv_home.layoutManager = LinearLayoutManager(activity) //    从上到下的列表试图
        homeAdapter = HomeAdapter(activity!!)
        rv_home.adapter = homeAdapter
        distence = dip2px(context!!,120f)
        presenter = HomePresenter(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
    }

    fun dip2px(context: Context,dpValue:Float):Int{
        val scale:Float = context.resources.displayMetrics.density
        return (dpValue*scale+0.5f).toInt()
    }

    var sum:Int =0
    var distence:Int =0
    var alpha = 55
    val data:ArrayList<String> = ArrayList()
    private fun initData() {
//模拟数据
//        for(i in 0 until 100){
//            data.add("我是商家：$i")
//        }

        //获取网络数据
        presenter.getHomeInfo()
       // homeAdapter.setDatas(data)

    }
    val allList:ArrayList<Seller> = ArrayList()
    fun onSuccess(nearbyList: List<Seller>, otherList: List<Seller>) {
        allList.clear()
        allList.addAll(nearbyList)
        allList.addAll(otherList)
        homeAdapter.setDatas(allList)
        Toast.makeText(context, "获取数据成功", Toast.LENGTH_SHORT).show()
        rv_home.setOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                sum+=dy
                if (sum>distence){
                    alpha=255
                }else{
                    alpha=sum*200/distence
                    alpha+55
                }
                container1.setBackgroundColor(Color.argb(alpha,0x31,0x90,0xe8))
                Log.i("home", "sum:$sum")
            }
        })
    }

    fun onFalied() {
        Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show()
    }
}