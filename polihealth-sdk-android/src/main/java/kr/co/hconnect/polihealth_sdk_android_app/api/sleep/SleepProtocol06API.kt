package kr.co.hconnect.polihealth_sdk_android_app.api.sleep

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.util.AttributeKey
import io.ktor.util.InternalAPI
import kotlinx.coroutines.runBlocking
import kr.co.hconnect.polihealth_sdk_android.PoliClient
import kr.co.hconnect.polihealth_sdk_android.api.BaseProtocolHandler
import kr.co.hconnect.polihealth_sdk_android.api.dto.response.SleepResponse
import kr.co.hconnect.polihealth_sdk_android.api.dto.response.toSleepResponse

object SleepProtocol06API : BaseProtocolHandler() {

    /**
     * TODO: Protocol06을 서버로 전송하는 API
     *
     * @param reqDate ex) 20240704054513 (yyyyMMddHHmmss)
     * @param byteArray
     * */
    @OptIn(InternalAPI::class)
    suspend fun requestPost(
        reqDate: String,
        byteArray: ByteArray
    ): SleepResponse {
        val response: SleepResponse =
            PoliClient.client.post("poli/sleep/protocol6") {
                body = MultiPartFormDataContent(
                    formData {
                        append("reqDate", reqDate)
                        append("userSno", SleepSessionAPI.userSno)
                        append("sessionId", SleepSessionAPI.sessionId)
                        append("file", byteArray, Headers.build {
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=\"\"", // 필수 헤더
                            )
                        })
                    }
                )
            }.call.attributes[AttributeKey("body")].toString().toSleepResponse()

        return response
    }


    @OptIn(InternalAPI::class)
    @RequiresApi(Build.VERSION_CODES.Q)
    fun testPost(context: Context) = runBlocking {
        try {
            val byteArray = readBytesFromDownload(context, "protocol08.bin")

            val response: HttpResponse =
                PoliClient.client.post("https://mapi-stg.health-on.co.kr/poli/sleep/protocol6") {
                    body = MultiPartFormDataContent(
                        formData {
                            append("reqDate", "20240704054513")
                            append("userSno", SleepSessionAPI.userSno)
                            append("sessionId", SleepSessionAPI.sessionId)
                            append("file", byteArray!!, Headers.build {
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "filename=\"\""
                                )
//                                append(
//                                    HttpHeaders.ContentType,
//                                    ContentType.Application.OctetStream.toString()
//                                )
                            })
                        }
                    )
                }
            Log.d("SleepProtocol06API", "userSno: ${SleepSessionAPI.userSno}")
            Log.d("SleepProtocol06API", "SessionId: ${SleepSessionAPI.sessionId}")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun readBytesFromDownload(context: Context, fileName: String): ByteArray? {
        val uri: Uri? = getUriFromFileName(context, fileName)
        return uri?.let {
            readBytesFromUri(context, it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getUriFromFileName(context: Context, fileName: String): Uri? {
        val projection = arrayOf(MediaStore.Files.FileColumns._ID)
        val selection = "${MediaStore.Files.FileColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fileName)

        val cursor = context.contentResolver.query(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                return Uri.withAppendedPath(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
            }
        }
        return null
    }

    private fun readBytesFromUri(context: Context, uri: Uri): ByteArray? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.readBytes()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}