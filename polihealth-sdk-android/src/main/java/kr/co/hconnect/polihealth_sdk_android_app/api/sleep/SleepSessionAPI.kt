package kr.co.hconnect.polihealth_sdk_android_app.api.sleep

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kr.co.hconnect.polihealth_sdk_android_app.PoliClient
import kr.co.hconnect.polihealth_sdk_android_app.api.RequestBody

object SleepSessionAPI {
    suspend fun requestStartBand(
        reqDate: String,
        userSno: Int
    ) {
        val requestBody = RequestBody(
            reqDate = reqDate,
            userSno = userSno
        )
        try {
            val response: HttpResponse =
                PoliClient.client.post("/poli/sleep/start") {
                    setBody(requestBody)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}