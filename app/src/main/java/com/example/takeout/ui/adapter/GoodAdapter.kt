package com.example.takeout.ui.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.takeout.R
import com.example.takeout.model.bean.GoodsInfo
import com.example.takeout.ui.activity.BussinessActivity
import com.example.takeout.ui.fragment.GoodsFragment
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter

class GoodAdapter(var context: Context,val goodsFragment: GoodsFragment) : BaseAdapter(), StickyListHeadersAdapter {
    /*------------BaseAdapter----------*/
    inner class ItemViewHolder(itemView: View) : View.OnClickListener {
        var iv_icon: ImageView = itemView.find(R.id.iv_icon)
        var tv_name: TextView = itemView.find(R.id.tv_name)
        var tv_form: TextView = itemView.find(R.id.tv_form)
        var tv_monthSale: TextView = itemView.find(R.id.tv_month_sale)
        var tv_newPrice: TextView = itemView.find(R.id.tv_newprice)
        var tv_oldPrice: TextView = itemView.find(R.id.tv_oldprice)
        var tv_count: TextView = itemView.find(R.id.tv_count)
        var btnAdd: TextView = itemView.findViewById(R.id.ib_add)
        var btnReduce:TextView = itemView.findViewById(R.id.ib_minus)
        lateinit var goods:GoodsInfo

        init {
            btnAdd.setOnClickListener(this)
            btnReduce.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            var isAdd:Boolean=true
            when(view!!.id){
                R.id.ib_add->{
                    isAdd=true
                    doAdd()
                }
                R.id.ib_minus-> {
                    isAdd=false
                    doReduce()
                }
            }
            changeGoodsnum(isAdd)
            (goodsFragment.activity as BussinessActivity).upDataShooppingCarUI()
        }

        private fun changeGoodsnum(isAdd: Boolean) {
            //找到此商品的类别
            val typeId = goods.typeId
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

        private fun doReduce() {
//            if(goods.count==1){
//                TakeoutApp.sInstance.deleteCacheSelectedInfo(goods.id)
//            }else{
//                TakeoutApp.sInstance.updateCacheSelectedInfo(goods.id,Constants.MINUS)
//            }
            goods.count--
            notifyDataSetChanged()
        }

        private fun doAdd() {
//            if(goods.count==0){
//                TakeoutApp.sInstance.addCacheSelectedInfo(CashSelectInfo(goods.sellerId,38,goods.typeId,goods.id,1))
//            }else{
//                TakeoutApp.sInstance.updateCacheSelectedInfo(goods.id,Constants.ADD)
//            }
            goods.count++
            notifyDataSetChanged()
        }

        fun bindData(goods: GoodsInfo) {
            this.goods=goods
            Picasso.with(context).load(goods.icon).into(iv_icon)
            tv_name.text = goods.name
            tv_form.text = goods.form
            tv_monthSale.text = "月售${goods.monthSaleNum}份"
            tv_newPrice.text = "￥${goods.newPrice}"
            tv_oldPrice.text ="￥${goods.oldPrice}"
            if(goods.oldPrice==0) tv_oldPrice.visibility=View.GONE
            tv_count.text=goods.count.toString()
            if(goods.count>0){
                tv_count.visibility = View.VISIBLE
                btnReduce.visibility=View.VISIBLE
            }else{
                btnReduce.visibility=View.GONE
                tv_count.visibility=View.GONE
            }
        }


    }

    var goodsList: List<GoodsInfo> = ArrayList()
    fun setData(goodsListInfo: List<GoodsInfo>) {
        this.goodsList = goodsListInfo
        goodsList.forEach {
            Log.e("test","${it.name} ${it.icon}---")

        }
        notifyDataSetChanged()
    }

    override fun getCount(): Int = goodsList.size

    override fun getItem(position: Int): Any = goodsList.get(position)

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView: View
        var viewHolder: ItemViewHolder
        if (convertView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false)
            viewHolder = ItemViewHolder(itemView)
            itemView.tag = viewHolder
        } else {
            itemView = convertView
            viewHolder = itemView.tag as ItemViewHolder
        }
        viewHolder.bindData(goodsList.get(position))
        return itemView
    }

    /*------------StickyListHeadersAdapter----------*/

    override fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var textView: TextView =
            LayoutInflater.from(context)
                .inflate(R.layout.item_type_header, parent, false) as TextView
        val typeName = goodsList[position].typeName
        textView.text = typeName
        textView.setTextColor(Color.BLACK)
        return textView
    }

    override fun getHeaderId(position: Int): Long {
        val typeId = goodsList.get(position).typeId
        return typeId.toLong()
    }


}