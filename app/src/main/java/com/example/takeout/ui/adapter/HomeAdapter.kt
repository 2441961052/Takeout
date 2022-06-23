package com.example.takeout.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takeout.R
import com.example.takeout.model.bean.Seller
import com.example.takeout.ui.activity.BussinessActivity
import org.jetbrains.anko.find

class HomeAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        val TYPE_TITLE = 0
        val TYPE_SELLER = 1
        lateinit var sSeller:Seller
        var hasCashInfo:Boolean = false
    }

    var mDatas:ArrayList<Seller> = ArrayList()
    fun setDatas(data: ArrayList<Seller>){
        this.mDatas = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if(position==0) TYPE_TITLE else TYPE_SELLER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return when(viewType){
            TYPE_TITLE-> TitleItemHolder(View.inflate(context, R.layout.item_title,null))
            TYPE_SELLER->SellerItemHolder(View.inflate(context, R.layout.item_seller,null))
            else-> TitleItemHolder(View.inflate(context, R.layout.item_title,null))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = getItemViewType(position)
        when(type){
            TYPE_TITLE -> (holder as TitleItemHolder).bindData("sadasdasdsadasas")
            TYPE_SELLER -> (holder as SellerItemHolder).bindData(mDatas[position-1])
        }
    }

    override fun getItemCount(): Int {
        return if(mDatas.size==0) 0 else mDatas.size+1
    }


    inner class TitleItemHolder(item:View) : RecyclerView.ViewHolder(item){
        init {

        }
        fun bindData(data: String) {

        }
    }
    inner class SellerItemHolder(var item:View) : RecyclerView.ViewHolder(item){
        //val textView:TextView = item.findViewById(R.id.item_home_common_tv)
        var tv_text:TextView = item.find(R.id.tv_title)
        var icon:ImageView = item.find(R.id.seller_logo)
        var rbScore:RatingBar = item.find(R.id.ratingBar)
        var tvsale:TextView =item.find(R.id.tv_home_sale)
        var tvSendPrice:TextView=item.find(R.id.tv_home_send_price)
        var tvSendStance:TextView=item.find(R.id.tv_home_distance)
        init {
            item.setOnClickListener {
                var intent:Intent = Intent(context, BussinessActivity::class.java)
//                var count =
//                    TakeoutApp.sInstance.queryCacheSelectedInfoBySellerId(sSeller.id.toInt())
//                if(count>0){
//                    hasCashInfo=true
//                }
                intent.putExtra("seller",sSeller)
                //intent.putExtra("hasCashInfo",hasCashInfo)
                context.startActivity(intent)
            }
        }
        fun bindData(seller: Seller) {
            sSeller = seller
            tv_text.text = seller.name
           // Picasso.with(context).load(seller.icon).into(icon)
            rbScore.rating = seller.score.toFloat()
            tvsale.text= "月售${seller.sale}单"
            tvSendPrice.text="￥${seller.sendPrice}起送/配送费￥${seller.deliveryFee}"
            tvSendStance.text=seller.distance
        }
    }
}