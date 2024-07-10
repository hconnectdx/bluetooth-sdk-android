package kr.co.hconnect.polihealth_sdk_android_app.service.sleep

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.hconnect.polihealth_sdk_android_app.DateUtil
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.request.HRSpO2
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.BaseResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.Sleep06Response
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepEndResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepStartResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol06API
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol07API
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol08API
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol09API
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepSessionAPI

class SleepApiService {
    private val TAG = "SleepApiService"

    fun sendStartSleep() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: SleepStartResponse = SleepSessionAPI.requestSleepStart()
                if (response.retCd == "0") {
                    Log.d(TAG, "수면 시작 요청 성공")
                } else {
                    Log.e(TAG, "수면 시작 요청 실패")
                }
            } catch (e: Exception) {
                Log.e(TAG, "통신에 실패했습니다. ${e.message}")
            }
        }
    }

    suspend fun sendEndSleep(): SleepEndResponse {
        return SleepSessionAPI.requestSleepEnd()
    }

    fun sendProtocol06(context: Context? = null) {
        val protocol6Bytes = SleepProtocol06API.flush(context)
        if (protocol6Bytes.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response: BaseResponse =
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
                    val response: Sleep06Response =
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
                val response: Sleep06Response = SleepProtocol09API.requestPost(
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