package com.example.takeout.model.utils

import android.content.Context
import android.util.Log
import com.igexin.sdk.GTIntentService
import com.igexin.sdk.message.GTCmdMessage
import com.igexin.sdk.message.GTNotificationMessage
import com.igexin.sdk.message.GTTransmitMessage





/**
 * 继承 GTIntentService 接收来自个推的消息，所有消息在主线程中回调，如果注册了该服务，则务必要在 AndroidManifest 中声明，否则无法接受消息
 */
class DemoService : GTIntentService() {
    override fun onReceiveServicePid(context: Context, pid: Int) {

    }

    // 处理透传消息
    override fun onReceiveMessageData(context: Context, msg: GTTransmitMessage) {
        // 透传消息的处理，详看 SDK demo
        val payload = msg.payload

        if (payload == null) {
            Log.i("TAG", "receiver payload = null")
        } else {
            var data = String(payload)
            OrderObservable.instance.newMsgComing(data)

        }
    }

    // 接收 cid
    override fun onReceiveClientId(context: Context, clientid: String) {
        Log.i("TAG", "onReceiveClientId -> clientid = $clientid")
    }


    // 各种事件处理回执
    override fun onReceiveCommandResult(context: Context, cmdMessage: GTCmdMessage) {}

    // 通知到达，只有个推通道下发的通知会回调此方法
    override fun onNotificationMessageArrived(context: Context, msg: GTNotificationMessage) {
        val title:String = msg.title
        var messageInfo:String = msg.content
        Log.i("TAG", "收到信息 5$messageInfo:$title")
    }

    // 通知点击，只有个推通道下发的通知会回调此方法
    override fun onNotificationMessageClicked(context: Context, msg: GTNotificationMessage) {
    }
}
