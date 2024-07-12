package kr.co.hconnect.polihealth_sdk_android_app.api.sleep

import android.util.Log
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.util.AttributeKey
import kotlinx.coroutines.runBlocking
import kr.co.hconnect.polihealth_sdk_android_app.DateUtil
import kr.co.hconnect.polihealth_sdk_android_app.PoliClient
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.request.RequestBody
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepStartResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.toSleepStartResponse

object SleepSessionAPI {
    var sessionId: String = ""
    var userSno: Int = 5

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

        sessionId = response.data?.sessionId ?: ""
        Log.d("SleepSessionAPI", "userSno: $userSno")
        Log.d("SleepSessionAPI", "sessionId: $sessionId")


        return response
    }

    fun testSleepStart() = runBlocking {
        requestSleepStart(
            reqDate = DateUtil.getCurrentDateTime(),
            userSno = userSno
        )
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

    fun testSleepEnd() = runBlocking {
        requestSleepEnd(
            reqDate = "20240704054513",
            userSno = userSno,
            sessionId = sessionId
        )
    }
}