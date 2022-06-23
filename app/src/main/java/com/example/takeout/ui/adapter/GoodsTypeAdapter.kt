package com.example.takeout.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takeout.R
import com.example.takeout.model.bean.GoodsTypeInfo
import com.example.takeout.ui.fragment.GoodsFragment
import org.jetbrains.anko.find

class GoodsTypeAdapter(var context: Context,var goodsFragment: GoodsFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var goodTypeList:List<GoodsTypeInfo> = listOf()
    fun setData(list:List<GoodsTypeInfo>){
        this.goodTypeList=list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var itemView =LayoutInflater.from(context).inflate(R.layout.item_type,parent,false)
        return GoodsTypeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GoodsTypeViewHolder).bindData(goodTypeList.get(position),position)
    }

    override fun getItemCount(): Int =goodTypeList.size
    var selectPosition:Int=0
    inner class GoodsTypeViewHolder(var item:View) :RecyclerView.ViewHolder(item){
        val textView:TextView
        var tvRedDotCount:TextView
        lateinit var goodsTypeInfoPosition:GoodsTypeInfo
        var mPosition:Int=0
        init {
            textView = item.find<TextView>(R.id.type)
            tvRedDotCount =item.find(R.id.tvRedDotCount)
            item.setOnClickListener {
                selectPosition=mPosition
                notifyDataSetChanged()
                //使右侧与左侧关联
                val TypeId = goodsTypeInfoPosition.id
                val goodsPosition = goodsFragment.goodsPresenter.getGoodsPosition(TypeId)
                goodsFragment.slh.setSelection(goodsPosition)
            }
        }
        fun bindData(goodsTypeInfo: GoodsTypeInfo, position: Int) {
            goodsTypeInfoPosition=goodsTypeInfo
            mPosition=position
            if(position==selectPosition){
                item.setBackgroundColor(Color.WHITE)
                textView.setTextColor(Color.BLACK)
                textView.setTypeface(Typeface.DEFAULT_BOLD)
            }else{
                item.setBackgroundColor(Color.GRAY)
                textView.setTextColor(Color.parseColor("#b9dedcdc"))
                textView.setTypeface(Typeface.DEFAULT)
            }
            textView.text = goodsTypeInfo.name
            tvRedDotCount.text = goodsTypeInfo.tvRedDotCount.toString()
            if(goodsTypeInfo.tvRedDotCount>0) tvRedDotCount.visibility =View.VISIBLE
            else tvRedDotCount.visibility =View.GONE
        }
    }
}