package com.example.takeout.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takeout.R
import com.example.takeout.model.bean.GoodsInfo
import com.example.takeout.model.bean.GoodsTypeInfo
import com.example.takeout.presenter.GoodsPresenter
import com.example.takeout.ui.activity.BussinessActivity
import com.example.takeout.ui.adapter.GoodAdapter
import com.example.takeout.ui.adapter.GoodsTypeAdapter
import org.jetbrains.anko.find
import se.emilsjolander.stickylistheaders.StickyListHeadersListView

class GoodsFragment: Fragment() {
    lateinit var goodsPresenter: GoodsPresenter
    lateinit var recyclerView: RecyclerView
    lateinit var slh:StickyListHeadersListView
    lateinit var goodsAdapter:GoodAdapter
    lateinit var goodsTypeAdapter: GoodsTypeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflateView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_goos,container,false)
        goodsPresenter = GoodsPresenter(this)
        recyclerView=inflateView.find(R.id.rv_left)
        slh = inflateView.find<StickyListHeadersListView>(R.id.slh)
        goodsAdapter= GoodAdapter(requireContext(),this)
        slh.adapter = goodsAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        goodsTypeAdapter=GoodsTypeAdapter(activity!!,this)
        recyclerView.adapter = goodsTypeAdapter
        return inflateView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        goodsPresenter.getGoodInfo((activity as BussinessActivity).seller.id.toString())
    }

    fun onLoadDataSuccess(goodList: List<GoodsTypeInfo>, allTypeList: ArrayList<GoodsInfo>) {
        (recyclerView.adapter as GoodsTypeAdapter).setData(goodList)
        //(slh.adapter as GoodAdapter).setData(allTypeList)

        goodsAdapter.setData(allTypeList)

        slh.setOnScrollListener(object : AbsListView.OnScrollListener{
            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

            }
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                //先找出旧的类别
                val oldPosition=goodsTypeAdapter.selectPosition
                val newTypeId = goodsPresenter.allTypeList.get(firstVisibleItem).typeId
                val newPosition = goodsPresenter.getPositionByTypeId(newTypeId)
                if(oldPosition!=newPosition){
                    goodsTypeAdapter.selectPosition = newPosition
                    goodsTypeAdapter.notifyDataSetChanged()
                }
            }

        })
    }
    fun onLoadDataFailed(){
        Toast.makeText(context, "cuowu", Toast.LENGTH_SHORT).show()
    }
}