package kr.co.hconnect.polihealth_sdk_android_app.service.sleep

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.hconnect.polihealth_sdk_android_app.DateUtil
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.request.HRSpO2
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.BaseResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepResultResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepCommResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol06API
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol07API
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol08API
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol09API
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepSessionAPI

class SleepApiService {
    private val TAG = "SleepApiService"

    suspend fun sendStartSleep(): SleepCommResponse {
        return SleepSessionAPI.requestSleepStart()
    }

    suspend fun sendEndSleep(): SleepResultResponse {
        return SleepSessionAPI.requestSleepEnd()
    }

    suspend fun sendProtocol06(context: Context? = null): SleepCommResponse? {
        val protocol6Bytes = SleepProtocol06API.flush(context)
        if (protocol6Bytes.isNotEmpty()) {
            val response: SleepCommResponse =
                SleepProtocol06API.requestPost(
                    DateUtil.getCurrentDateTime(),
                    protocol6Bytes
                )
            return response
        } else {
            return null
        }
    }

    fun sendProtocol07(context: Context?) {
        val protocol7Bytes = SleepProtocol07API.flush(context)
        if (protocol7Bytes.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response: BaseResponse =
                        SleepProtocol07API.requestPost(
                            DateUtil.getCurrentDateTime(),
                            protocol7Bytes
                        )
                    when (response.retCd) {
                        "0" -> {
                            Log.d(TAG, "Protocol07 전송 성공")
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
        val protocol8Bytes = SleepProtocol08API.flush(context)
        if (protocol8Bytes.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response: SleepCommResponse =
                        SleepProtocol08API.requestPost(
                            DateUtil.getCurrentDateTime(),
                            protocol8Bytes
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

    fun sendProtocol09(hrSpo2: HRSpO2) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: SleepCommResponse = SleepProtocol09API.requestPost(
                    DateUtil.getCurrentDateTime(),
                    hrSpo2
                )
                when (response.retCd) {
                    "0" -> {
                        Log.d(TAG, "Protocol09 전송 성공")
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