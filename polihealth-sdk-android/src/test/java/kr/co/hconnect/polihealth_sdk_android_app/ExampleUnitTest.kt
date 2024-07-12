package kr.co.hconnect.polihealth_sdk_android_app

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Serializable
data class RequestBody(
    val reqDate: String,
    val userSno: Int,
    val sessionId: String? = null,
    val data: MyData? = null,
) {
    @Serializable
    class MyData(
        val oxygenVal: Int,
        val heartRateVal: Int
    )
}

class ExampleUnitTest {

    @Test
    fun requestStartBand() = runBlocking {
        val requestBody = RequestBody(
            reqDate = "20240704054513",
            userSno = 3,
        )
        try {
            val response: HttpResponse =
                PoliClient.client.post("https://mapi-stg.health-on.co.kr/poli/sleep/start") {
                    setBody(requestBody)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @Test
    fun requestProtocol09() = runBlocking {
        val requestBody = RequestBody(
            reqDate = "20240704054513",
            userSno = 3,
            sessionId = "123",
            data = RequestBody.MyData(
                oxygenVal = 98,
                heartRateVal = 60
            )
        )
        try {
            val response: HttpResponse =
                PoliClient.client.post("https://mapi-stg.health-on.co.kr/poli/sleep/protocol9") {
                    setBody(requestBody)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


