package com.example.takeout.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takeout.R
import com.example.takeout.model.bean.GoodsInfo
import com.example.takeout.ui.activity.BussinessActivity
import com.example.takeout.ui.fragment.GoodsFragment
import org.jetbrains.anko.find

class CartAdapter(var context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var goodsFragment:GoodsFragment
    init {
        goodsFragment = (context as BussinessActivity).fragments[0] as GoodsFragment
    }
    var list:ArrayList<GoodsInfo> = arrayListOf()
    fun setCartData(list:ArrayList<GoodsInfo>){
        this.list=list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false)
        return CartItemView(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CartItemView).bindData(list.get(position))
    }

    override fun getItemCount(): Int =list.size

    inner class CartItemView(item:View):RecyclerView.ViewHolder(item), View.OnClickListener {
        fun bindData(goodsInfo: GoodsInfo) {
            this.goodsInfo=goodsInfo
            tvName.text=goodsInfo.name
            tvAllPrice.text=(goodsInfo.newPrice.toFloat() * goodsInfo.count).toString()
            tvCount.text=goodsInfo.count.toString()
        }

        lateinit var goodsInfo: GoodsInfo
        var tvName:TextView
        var tvAllPrice:TextView
        var tvCount:TextView
        var btnAdd:ImageButton
        var btnReduce:ImageButton
        init {
            tvName=item.find(R.id.tv_name)
            tvAllPrice=item.find(R.id.tv_type_all_price)
            tvCount=item.find(R.id.tv_count)
            btnAdd=item.find(R.id.ib_add)
            btnReduce=item.find(R.id.ib_minus)
            btnAdd.setOnClickListener(this)
            btnReduce.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var isAdd:Boolean=true
            when(v?.id){
                R.id.ib_add->{
                    doAddOperation()
                    isAdd=true
                }
                R.id.ib_minus->{
                    doReduceOperation()
                    isAdd=false
                }

            }
            changeGoodsnum(isAdd)
            (context as BussinessActivity).upDataShooppingCarUI()
        }
        private fun changeGoodsnum(isAdd: Boolean) {
            //找到此商品的类别
            val typeId = goodsInfo.typeId
            //找到在左侧的位置
            val positionByTypeId = goodsFragment.goodsPresenter.getPositionByTypeId(typeId)
            val redDot=goodsFragment.goodsPresenter.goodList.get(positionByTypeId)
            var count =redDot.tvRedDotCount
            if(isAdd){
                count++
            }else{
                count--
            }
            redDot.tvRedDotCount=count
            goodsFragment.goodsTypeAdapter.notifyDataSetChanged()

        }
        private fun doReduceOperation() {
            var count = goodsInfo.count
            if(count==1){
                list.remove(goodsInfo)
                if(list.size==0){
                    (context as BussinessActivity).showCart()
                }
            }
            count--
            goodsInfo.count=count
            notifyDataSetChanged()
            goodsFragment.goodsAdapter.notifyDataSetChanged()
        }


        private fun doAddOperation() {
            var count = goodsInfo.count
            count++
            goodsInfo.count=count
            notifyDataSetChanged()
            goodsFragment.goodsAdapter.notifyDataSetChanged()
        }
    }
}