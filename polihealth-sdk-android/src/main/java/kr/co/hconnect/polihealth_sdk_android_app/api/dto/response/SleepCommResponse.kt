package kr.co.hconnect.polihealth_sdk_android_app.api.dto.response

import org.json.JSONException
import org.json.JSONObject

data class SleepCommResponse(
    val data: Data?
) : BaseResponse() {
    data class Data(
        val sessionId: String
    )
}


fun String.toSleepCommResponse(): SleepCommResponse {
    val jsonObject = JSONObject(this)

    val retCd = jsonObject.optString("retCd")
    val retMsg = jsonObject.optString("retMsg")
    val resDate = jsonObject.optString("resDate")

    try {
        val dataObject: JSONObject? = jsonObject.getJSONObject("data")
        dataObject?.let {
            val sessionId = it.getString("sessionId")
            val data = SleepCommResponse.Data(sessionId = sessionId)
            return SleepCommResponse(data).apply {
                this.retCd = retCd
                this.retMsg = retMsg
                this.resDate = resDate
            }
        }
            ?: return SleepCommResponse(null).apply {
                this.retCd = retCd
                this.retMsg = retMsg
                this.resDate = resDate
            }
    } catch (e: JSONException) {
        return SleepCommResponse(null).apply {
            this.retCd = retCd
            this.retMsg = retMsg
            this.resDate = resDate
        }
    }
}
