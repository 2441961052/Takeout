package com.example.takeout.model.bean

import java.io.Serializable
import java.util.*

data class Seller(
    var id: Float,
    var pic: String,
    var name: String,
    var score: String,
    var sale: String,
    var ensure: String,
    var invoice: String,
    var sendPrice: String,
    var deliveryFee: String,
    var recentVisit: String,
    var distance: String,
    var time: String,
    var icon: String,
    var activityList: ArrayList<ActivityInfo>
):Serializable
