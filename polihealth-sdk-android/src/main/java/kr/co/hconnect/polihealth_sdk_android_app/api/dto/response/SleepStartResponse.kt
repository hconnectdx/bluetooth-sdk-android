package kr.co.hconnect.polihealth_sdk_android_app.api.dto.response

import org.json.JSONException
import org.json.JSONObject

data class SleepStartResponse(
    val data: Data?
) : BaseResponse() {
    data class Data(
        val sessionId: String
    )
}


fun String.toSleepStartResponse(): SleepStartResponse {
    val jsonObject = JSONObject(this)

    val retCd = jsonObject.optString("retCd")
    val retMsg = jsonObject.optString("retMsg")
    val resDate = jsonObject.optString("resDate")

    try {
        val dataObject: JSONObject? = jsonObject.getJSONObject("data")
        dataObject?.let {
            val sessionId = it.getString("sessionId")
            val data = SleepStartResponse.Data(sessionId = sessionId)
            return SleepStartResponse(data).apply {
                this.retCd = retCd
                this.retMsg = retMsg
                this.resDate = resDate
            }
        }
            ?: return SleepStartResponse(null).apply {
                this.retCd = retCd
                this.retMsg = retMsg
                this.resDate = resDate
            }
    } catch (e: JSONException) {
        return SleepStartResponse(null).apply {
            this.retCd = retCd
            this.retMsg = retMsg
            this.resDate = resDate
        }
    }
}
