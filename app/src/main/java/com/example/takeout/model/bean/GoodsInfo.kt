package com.example.takeout.model.bean

class GoodsInfo {
    var id = 0//商品id

    var name : String=""//商品名称

    var icon : String=""//商品图片

    var form : String=""//组成

    var monthSaleNum =0//月销售量

    var isBargainPrice =false//特价

    var isNew=false //是否是新产品

    var newPrice: String="" //新价

    var oldPrice=0 //原价

    var sellerId = 0
    var typeId = 0
    var typeName: String=""
    var count=0

    constructor() : super() {}
    constructor(
        sellerId: Int,
        id: Int,
        name: String,
        icon: String,
        form: String,
        monthSaleNum: Int,
        bargainPrice: Boolean,
        isNew: Boolean,
        newPrice: String,
        oldPrice: Int,
        count:Int
    ) : super() {
        this.id = id
        this.name = name
        this.icon = icon
        this.form = form
        this.monthSaleNum = monthSaleNum
        isBargainPrice = bargainPrice
        this.isNew = isNew
        this.newPrice = newPrice
        this.oldPrice = oldPrice
        this.sellerId = sellerId
        this.count = count
    }

    override fun toString(): String {
        return ("GoodsInfo [id=" + id + ", name=" + name + ", icon=" + icon + ", form=" + form + ", monthSaleNum="
                + monthSaleNum + ", bargainPrice=" + isBargainPrice + ", isNew=" + isNew + ", newPrice=" + newPrice
                + ", oldPrice=" + oldPrice + ", count=" + count +"]")
    }
}