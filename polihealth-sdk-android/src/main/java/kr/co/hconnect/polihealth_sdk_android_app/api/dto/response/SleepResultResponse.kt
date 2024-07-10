package kr.co.hconnect.polihealth_sdk_android_app.api.dto.response

import org.json.JSONException
import org.json.JSONObject

data class SleepResultResponse(
    val data: Data?
) : BaseResponse() {

    data class Data(
        val sleepQuality: String
    )
}


fun String.toSleepEndResponse(): SleepResultResponse {
    val jsonObject = JSONObject(this)

    val retCd = jsonObject.optString("retCd")
    val retMsg = jsonObject.optString("retMsg")
    val resDate = jsonObject.optString("resDate")

    try {
        val dataObject: JSONObject? = jsonObject.getJSONObject("data")
        dataObject?.let {
            val sleepQuality = it.getString("sleepQuality")
            val data = SleepResultResponse.Data(sleepQuality = sleepQuality)
            return SleepResultResponse(data).apply {
                this.retCd = retCd
                this.retMsg = retMsg
                this.resDate = resDate
            }
        }
            ?: return SleepResultResponse(null).apply {
                this.retCd = retCd
                this.retMsg = retMsg
                this.resDate = resDate
            }
    } catch (e: JSONException) {
        return SleepResultResponse(null).apply {
            this.retCd = retCd
            this.retMsg = retMsg
            this.resDate = resDate
        }
    }
}
