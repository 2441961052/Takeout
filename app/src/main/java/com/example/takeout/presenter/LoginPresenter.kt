package com.example.takeout.presenter

import android.util.Log
import com.example.takeout.model.bean.User
import com.example.takeout.model.dao.TakeoutOpenHelper
import com.example.takeout.model.utils.TakeoutApp
import com.example.takeout.ui.activity.LoginActivity
import com.google.gson.Gson
import com.j256.ormlite.android.AndroidDatabaseConnection
import com.j256.ormlite.dao.Dao
import java.sql.Savepoint

class LoginPresenter(var loginActivity: LoginActivity) : NetPresenter() {
    fun loginByPhone(phone: String) {
        val loginCall = service.loginByPhone(phone)
        loginCall.enqueue(callBack)
    }

    override fun parserjson(json: String) {
        var user = Gson().fromJson(json, User::class.java)
        var connection: AndroidDatabaseConnection ?= null
        var startPoint:Savepoint ?=null
        if (user != null) {
            TakeoutApp.suser = user
            try {
                val takeoutOpenHelper = TakeoutOpenHelper(loginActivity)
                val userDao: Dao<User, Int> = takeoutOpenHelper.getDao(User::class.java)
                connection =
                    AndroidDatabaseConnection(takeoutOpenHelper.writableDatabase, true)
                startPoint = connection.setSavePoint("start")
                connection.isAutoCommit = false
                //区分新老用户
                val userLiset: List<User> = userDao.queryForAll()
                var isOldUser: Boolean = false
                for (element in userLiset) {
                    if (element.id == user.id) {
                        isOldUser = true
                    }
                }
                if (isOldUser) {
                    userDao.update(user)
                    Log.i("TAG", "老用户登录")
                } else {
                    userDao.create(user)
                    Log.i("TAG", "新用户登录")
                }
                connection.commit(startPoint)
                loginActivity.loginSuccess()
            } catch (e: Exception) {
                loginActivity.loginFailed()
                Log.e("TAG", "事务异常"+e.localizedMessage)
                connection!!.rollback(startPoint)
            }
        } else {
            loginActivity.loginFailed()
        }
    }
}