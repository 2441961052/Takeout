package com.example.takeout.model.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.takeout.model.bean.User
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

class TakeoutOpenHelper(val context: Context) :
    OrmLiteSqliteOpenHelper(context, "takeout_db", null, 1) {

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTable(connectionSource, User::class.java)
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {

    }
}