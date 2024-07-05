package kr.co.hconnect.polihealth_sdk_android_app

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpMethod
import io.ktor.util.InternalAPI
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
    val sessionId: String,
    val data: MyData,
) {
    @Serializable
    class MyData(
        val oxygenVal: Int,
        val heartRateVal: Int
    )
}

class ExampleUnitTest {
    @OptIn(InternalAPI::class)
    @Test
    fun requestPost() = runBlocking {
        try {
            val response: HttpResponse =
                KtorClient.client.post("https://mapi-stg.health-on.co.kr/poli/sleep/protocol6") {

                    body = MultiPartFormDataContent(
                        formData {
                            append("reqDate", "20240704054513")
                            append("userSno", 3)
                            append("sessionId", "123")

                            append("file", byteArrayOf(0x01, 0x02, 0x03), Headers.build {
//                                append(HttpHeaders.ContentDisposition, "filename=\"example.txt\"")
//                                append(
//                                    HttpHeaders.ContentType,
//                                    ContentType.Application.OctetStream.toString()
//                                )
                            })
                        }
                    )
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
                KtorClient.client.post("https://mapi-stg.health-on.co.kr/poli/sleep/protocol9") {
                    setBody(requestBody)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


