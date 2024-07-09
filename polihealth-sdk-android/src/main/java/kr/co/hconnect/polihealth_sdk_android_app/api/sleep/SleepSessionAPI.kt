package kr.co.hconnect.polihealth_sdk_android_app.api.sleep

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.AttributeKey
import io.ktor.util.InternalAPI
import kr.co.hconnect.polihealth_sdk_android_app.PoliClient
import kr.co.hconnect.polihealth_sdk_android_app.api.RequestBody
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.SleepStartResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.toSleepStartResponse

object SleepSessionAPI {
    var sessionId: String = ""
    var userSno: Int = 10

    suspend fun requestSleepStart(
        reqDate: String,
        userSno: Int
    ): SleepStartResponse {
        val requestBody = RequestBody(
            reqDate = reqDate,
            userSno = userSno
        )
        val response: SleepStartResponse =
            PoliClient.client.post("/poli/sleep/start") { setBody(requestBody) }
                .call.attributes[AttributeKey("body")].toString()
                .toSleepStartResponse()

        response.data?.let {
            sessionId = it.sessionId
        } ?: run { sessionId = "" }

        return response
    }

    suspend fun requestSleepEnd(
        reqDate: String,
        userSno: Int,
        sessionId: String,
    ) {
        val requestBody = RequestBody(
            reqDate = reqDate,
            userSno = userSno,
            sessionId = sessionId
        )
        try {
            val response: HttpResponse =
                PoliClient.client.post("/poli/sleep/stop") {
                    setBody(requestBody)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}