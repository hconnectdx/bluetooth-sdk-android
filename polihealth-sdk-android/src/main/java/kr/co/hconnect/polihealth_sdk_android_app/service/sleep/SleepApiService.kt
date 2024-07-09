package kr.co.hconnect.polihealth_sdk_android_app.service.sleep

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.hconnect.polihealth_sdk_android_app.DateUtil
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.Sleep06Response
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol06API
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol08API

class SleepApiService {
    private val TAG = "SleepApiService"
    fun sendProtocol06(context: Context? = null) {
        val protocol6Bytes = SleepProtocol06API.flush(context)
        if (protocol6Bytes.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response: Sleep06Response =
                        SleepProtocol06API.requestPost(
                            DateUtil.getCurrentDateTime(),
                            protocol6Bytes
                        )
                    when (response.retCd) {
                        "0" -> {
                            Log.d(TAG, "Protocol06 전송 성공")
                        }

                        else -> {

                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "통신에 실패했습니다. ${e.message}")
                }
            }
        }
    }

    fun sendProtocol08(context: Context? = null) {
        val protocol6Bytes = SleepProtocol08API.flush(context)
        if (protocol6Bytes.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response: Sleep06Response =
                        SleepProtocol06API.requestPost(
                            DateUtil.getCurrentDateTime(),
                            protocol6Bytes
                        )
                    when (response.retCd) {
                        "0" -> {
                            Log.d(TAG, "Protocol06 전송 성공")
                        }

                        else -> {

                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "통신에 실패했습니다. ${e.message}")
                }
            }
        }
    }
}