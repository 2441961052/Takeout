package com.example.takeout.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.takeout.R
import com.example.takeout.model.bean.GoodsInfo
import com.example.takeout.model.bean.Seller
import com.example.takeout.ui.adapter.CartAdapter
import com.example.takeout.ui.fragment.GoodsFragment
import com.example.takeout.ui.fragment.ShopFragment
import com.example.takeout.ui.fragment.TalkFragment
import com.flipboard.bottomsheet.BottomSheetLayout
import com.google.android.material.tabs.TabLayout
import org.jetbrains.anko.find

class BussinessActivity : AppCompatActivity(), View.OnClickListener {
    var bottomSheetView: View? = null
    lateinit var bottom:LinearLayout
    lateinit var bottomSheepLayout: BottomSheetLayout
    lateinit var rvCart:RecyclerView
    lateinit var tvSendPrice:TextView
    lateinit var tvDeliveryFee:TextView
    lateinit var tvClear:TextView
    lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business)
        initPage()

    }

    var hasExtra = false
    lateinit var seller: Seller
    private fun doIntentInfo() {
        if(intent.hasExtra("hasCashInfo")){
            hasExtra = intent.getBooleanExtra("hasCashInfo", false)
        }
        seller = intent.getSerializableExtra("seller") as Seller
        tvSendPrice.text="另需配送费￥${seller.sendPrice}"
        tvDeliveryFee.text="￥${seller.deliveryFee}起送"
    }

    var fragments: List<Fragment> =
        listOf<Fragment>(GoodsFragment(), ShopFragment(), TalkFragment())

    private fun initPage() {
        bottomSheepLayout = find(R.id.bottomSheetLayout)
        var viewPage: ViewPager = find<ViewPager>(R.id.vp)
        bottom = find(R.id.bottom)
        bottom.setOnClickListener(this)
        viewPage.adapter = BussinessFragmentAdapter()
        val tabs = find<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPage)
        tvSendPrice=find(R.id.tvSendPrice)
        tvDeliveryFee=find(R.id.tvDeliveryFee)
        doIntentInfo()
    }

    fun upDataShooppingCarUI() {
        var tvSelectNum: TextView
        var tvCountPrice: TextView
        var count = 0
        var allPrice = 0.0f
        var goodsFragment: GoodsFragment = fragments[0] as GoodsFragment
        val shoppingCarList = goodsFragment.goodsPresenter.getShoppingCarList()
        for (i in shoppingCarList.indices) {
            var goodsInfo = shoppingCarList[i]
            count += goodsInfo.count
            allPrice += (goodsInfo.newPrice).toFloat() * (goodsInfo.count).toFloat()
        }
        tvSelectNum = find(R.id.tvSelectNum)
        tvCountPrice = find(R.id.tvCountPrice)
        tvSelectNum.text = count.toString()
        tvCountPrice.text = "￥${allPrice}"

        if (count > 0) {
            tvSelectNum.visibility = View.VISIBLE
        }else{
            tvSelectNum.visibility = View.GONE
        }

        goodsFragment.goodsAdapter.notifyDataSetChanged()
    }

    inner class BussinessFragmentAdapter : FragmentPagerAdapter(supportFragmentManager) {
        var title = listOf<String>("商品", "店铺", "评论")
        override fun getCount(): Int = title.size

        override fun getPageTitle(position: Int): CharSequence? {
            return title.get(position)
        }

        override fun getItem(position: Int): Fragment {
            return fragments.get(position)
        }


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bottom-> showCart()
        }
    }

    fun showCart() {
        var shoppingCarList:ArrayList<GoodsInfo> = arrayListOf()
        if (bottomSheetView == null) {
            bottomSheetView = LayoutInflater.from(this)
                .inflate(R.layout.cart_list, window.decorView as ViewGroup, false)
            rvCart = bottomSheetView!!.find(R.id.rvCart)
            tvClear=bottomSheetView!!.find(R.id.tvClear)
            tvClear.setOnClickListener{
                var builder=AlertDialog.Builder(this)
                builder.setTitle("确认不吃了嘛？")
                builder.setPositiveButton("不吃了", DialogInterface.OnClickListener { dialog, which ->
                    (fragments[0] as GoodsFragment).goodsPresenter.clearCar()
                    cartAdapter.notifyDataSetChanged()
                    (fragments[0] as GoodsFragment).goodsAdapter.notifyDataSetChanged()
                    clearAllRedDot()
                    (fragments[0] as GoodsFragment).goodsTypeAdapter.notifyDataSetChanged()
                    upDataShooppingCarUI()
                    showCart()

                })
                builder.setNegativeButton("点错了", DialogInterface.OnClickListener { dialog, which ->

                })
                builder.show()
            }
            rvCart.layoutManager=LinearLayoutManager(this)
            cartAdapter = CartAdapter(this)
            rvCart.adapter = cartAdapter
        }
        //toast("asdadasdasdas")
        if (bottomSheepLayout.isSheetShowing) {
            bottomSheepLayout.dismissSheet()
        } else {
            var goodsFragment: GoodsFragment = fragments[0] as GoodsFragment
            shoppingCarList = goodsFragment.goodsPresenter.getShoppingCarList()
            cartAdapter.setCartData(shoppingCarList)
            if(shoppingCarList.size>0){
                bottomSheepLayout.showWithSheetView(bottomSheetView)
            }
        }
    }

    private fun clearAllRedDot() {
        val goodTypeList = (fragments[0] as GoodsFragment).goodsPresenter.goodList
        for(i in goodTypeList.indices){
            var goods = goodTypeList.get(i)
            goods.tvRedDotCount=0
        }

    }

}
