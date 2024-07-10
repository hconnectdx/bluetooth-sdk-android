package kr.co.hconnect.polihealth_sdk_android_app.api.dto.response

import org.json.JSONException
import org.json.JSONObject

data class SleepEndResponse(
    val data: Data?
) : BaseResponse() {

    data class Data(
        val sleepQuality: String
    )
}


fun String.toSleepEndResponse(): SleepEndResponse {
    val jsonObject = JSONObject(this)

    val retCd = jsonObject.optString("retCd")
    val retMsg = jsonObject.optString("retMsg")
    val resDate = jsonObject.optString("resDate")

    try {
        val dataObject: JSONObject? = jsonObject.getJSONObject("data")
        dataObject?.let {
            val sleepQuality = it.getString("sleepQuality")
            val data = SleepEndResponse.Data(sleepQuality = sleepQuality)
            return SleepEndResponse(data).apply {
                this.retCd = retCd
                this.retMsg = retMsg
                this.resDate = resDate
            }
        }
            ?: return SleepEndResponse(null).apply {
                this.retCd = retCd
                this.retMsg = retMsg
                this.resDate = resDate
            }
    } catch (e: JSONException) {
        return SleepEndResponse(null).apply {
            this.retCd = retCd
            this.retMsg = retMsg
            this.resDate = resDate
        }
    }
}
