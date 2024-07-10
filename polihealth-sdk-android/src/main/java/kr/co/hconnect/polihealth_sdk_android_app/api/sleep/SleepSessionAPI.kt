package kr.co.hconnect.polihealth_sdk_android_app.api.sleep

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.util.AttributeKey
import kotlinx.coroutines.runBlocking
import kr.co.hconnect.polihealth_sdk_android_app.DateUtil
import kr.co.hconnect.polihealth_sdk_android_app.PoliClient
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.request.RequestBody
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepResultResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepCommResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.toSleepEndResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.toSleepCommResponse

object SleepSessionAPI {
    var sessionId: String = ""
    var userSno: Int = 10

    /**
     * TODO: 수면 시작 요청 API
     *
     * @return SleepStartResponse (sessionId)
     */
    suspend fun requestSleepStart(): SleepCommResponse {
        val requestBody = RequestBody(
            reqDate = DateUtil.getCurrentDateTime(),
            userSno = userSno
        )
        val response: SleepCommResponse =
            PoliClient.client.post("/poli/sleep/start") { setBody(requestBody) }
                .call.attributes[AttributeKey("body")].toString()
                .toSleepCommResponse()

        sessionId = response.data?.sessionId ?: ""

        return response
    }

    fun testSleepStart() = runBlocking {
        requestSleepStart()
    }

    /**
     * TODO: 수면 종료 요청 API
     *
     * @return SleepEndResponse (sleepQuality)
     */
    suspend fun requestSleepEnd(): SleepResultResponse {
        val requestBody = RequestBody(
            reqDate = DateUtil.getCurrentDateTime(),
            userSno = userSno,
            sessionId = sessionId
        )

        val response: SleepResultResponse =
            PoliClient.client.post("/poli/sleep/stop") { setBody(requestBody) }
                .call.attributes[AttributeKey("body")].toString()
                .toSleepEndResponse()

        return response
    }

    fun testSleepEnd() = runBlocking {
        requestSleepEnd()
    }
}