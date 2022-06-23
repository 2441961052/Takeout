package com.example.takeout.ui.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.takeout.R
import com.example.takeout.presenter.LoginPresenter
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {
    private var presenter:LoginPresenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListener()
    }

    private fun initListener() {
        val back :ImageView = find(R.id.iv_user_back)
        back.setOnClickListener {
            finish()
        }

        val login:TextView = find(R.id.login)
        val phone:EditText = find(R.id.et_user_phone)


        login.setOnClickListener {
            val myPhone=phone.text.toString()
            presenter.loginByPhone(myPhone)
        }
    }

    fun loginSuccess() {
        finish()
        toast("登录成功")
    }

    fun loginFailed() {
        toast("登录失败")
    }

}