package kr.co.hconnect.polihealth_sdk_android_app.api.dto

import org.json.JSONObject

class Sleep06Response : BaseResponse()

fun String.toSleep06Response(): Sleep06Response {
    val jsonObject = JSONObject(this)
    return Sleep06Response().apply {
        this.retCd = jsonObject.optString("retCd")
        this.retMsg = jsonObject.optString("retMsg")
        this.resDate = jsonObject.optString("resDate")
    }
}
