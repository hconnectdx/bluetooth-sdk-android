package kr.co.hconnect.polihealth_sdk_android_app.api.sleep

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.util.AttributeKey
import kotlinx.coroutines.runBlocking
import kr.co.hconnect.polihealth_sdk_android_app.DateUtil
import kr.co.hconnect.polihealth_sdk_android_app.PoliClient
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.request.RequestBody
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepEndResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepStartResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.toSleepEndResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.toSleepStartResponse

object SleepSessionAPI {
    var sessionId: String = ""
    var userSno: Int = 10

    suspend fun requestSleepStart(): SleepStartResponse {
        val requestBody = RequestBody(
            reqDate = DateUtil.getCurrentDateTime(),
            userSno = userSno
        )
        val response: SleepStartResponse =
            PoliClient.client.post("/poli/sleep/start") { setBody(requestBody) }
                .call.attributes[AttributeKey("body")].toString()
                .toSleepStartResponse()

        sessionId = response.data?.sessionId ?: ""

        return response
    }

    fun testSleepStart() = runBlocking {
        requestSleepStart()
    }

    suspend fun requestSleepEnd(): SleepEndResponse {
        val requestBody = RequestBody(
            reqDate = DateUtil.getCurrentDateTime(),
            userSno = userSno,
            sessionId = sessionId
        )

        val response: SleepEndResponse =
            PoliClient.client.post("/poli/sleep/stop") { setBody(requestBody) }
                .call.attributes[AttributeKey("body")].toString()
                .toSleepEndResponse()

        return response
    }

    fun testSleepEnd() = runBlocking {
        requestSleepEnd()
    }
}