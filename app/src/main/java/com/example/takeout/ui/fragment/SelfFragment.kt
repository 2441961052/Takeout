package com.example.takeout.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.takeout.R
import com.example.takeout.model.utils.TakeoutApp
import com.example.takeout.ui.activity.LoginActivity
import org.jetbrains.anko.find

class SelfFragment :Fragment(){

    lateinit var iv_login:ImageView
    lateinit var info:LinearLayout
    lateinit var username:TextView
    lateinit var phone: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(activity, R.layout.fragment_self,null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        iv_login =view.find(R.id.login1)
        info=view.find(R.id.ll_userinfo)
        username=view.find(R.id.username)
        phone=view.find(R.id.phone)
        iv_login.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
                activity!!.startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if(TakeoutApp.suser.id==0){
            iv_login.visibility =View.VISIBLE
            info.visibility =View.GONE
        }else{
            iv_login.visibility =View.GONE
            info.visibility =View.VISIBLE
            username.text = "欢迎你：${TakeoutApp.suser.name}"
            phone.text = TakeoutApp.suser.phone
        }
    }
}