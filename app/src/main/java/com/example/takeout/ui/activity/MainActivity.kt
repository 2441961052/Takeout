package com.example.takeout.ui.activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.takeout.R
import com.example.takeout.ui.fragment.HomeFragment
import com.example.takeout.ui.fragment.MoreFragment
import com.example.takeout.ui.fragment.OrderFragment
import com.example.takeout.ui.fragment.SelfFragment
import com.igexin.sdk.PushManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomBar()
        changeIndex(0)
        PushManager.getInstance().initialize(this)
    }
    val fragments:List<Fragment> = listOf(HomeFragment(),OrderFragment(),SelfFragment(),MoreFragment())
    private fun initBottomBar() {
        val bottomBar = findViewById<LinearLayout>(R.id.bottomBar)
        for(i in 0 until bottomBar.childCount){
            bottomBar.getChildAt(i).setOnClickListener{
                changeIndex(i)
            }
        }
    }

    private fun changeIndex(index: Int) {
        val bottomBar = findViewById<LinearLayout>(R.id.bottomBar)
        for (i in 0 until bottomBar.childCount){
            val child =  bottomBar.getChildAt(i)
            if(i==index){
                setEnable(child,false)
            }else{
                setEnable(child,true)
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, fragments[index]).commit()
    }

    private fun setEnable(child: View, isEnable: Boolean) {
        child.isEnabled = isEnable
        if(child is ViewGroup){
            for(i in 0 until child.childCount){
                child.getChildAt(i).isEnabled = isEnable
            }
        }
    }


}


