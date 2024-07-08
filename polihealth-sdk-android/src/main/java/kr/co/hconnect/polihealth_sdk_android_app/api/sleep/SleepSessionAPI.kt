package kr.co.hconnect.polihealth_sdk_android_app.api.sleep

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kr.co.hconnect.polihealth_sdk_android_app.KtorClient
import kr.co.hconnect.polihealth_sdk_android_app.api.RequestBody

object SleepSessionAPI {
    suspend fun requestStartBand() {
        val requestBody = RequestBody(
            reqDate = "20240704054513",
            userSno = 3,
        )
        try {
            val response: HttpResponse =
                KtorClient.client.post("https://mapi-stg.health-on.co.kr/poli/sleep/start") {
                    setBody(requestBody)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}