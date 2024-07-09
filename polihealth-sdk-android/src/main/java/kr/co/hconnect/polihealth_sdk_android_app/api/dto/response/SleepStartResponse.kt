package kr.co.hconnect.polihealth_sdk_android_app.api.dto.response

import org.json.JSONObject

data class SleepStartResponse(
    val data: Data?
) : BaseResponse()

data class Data(
    val sessionId: String
)

fun String.toSleepStartResponse(): SleepStartResponse {
    val jsonObject = JSONObject(this)

    val retCd = jsonObject.optString("retCd")
    val retMsg = jsonObject.optString("retMsg")
    val resDate = jsonObject.optString("resDate")

    val dataObject = jsonObject.getJSONObject("data")
    val sessionId = dataObject.getString("sessionId")

    val data = Data(sessionId)

    return SleepStartResponse(data).apply {
        this.retCd = retCd
        this.retMsg = retMsg
        this.resDate = resDate
    }
}
