package kr.co.hconnect.polihealth_sdk_android.service.daily

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kr.co.hconnect.polihealth_sdk_android.DateUtil
import kr.co.hconnect.polihealth_sdk_android.api.daily.DailyProtocol01API
import kr.co.hconnect.polihealth_sdk_android.api.daily.DailyProtocol03API
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.request.LTMModel
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.DailyProtocol02API
import kr.co.hconnect.polihealth_sdk_android.api.daily.model.HRSpO2
import kr.co.hconnect.polihealth_sdk_android.api.dto.response.Daily1Response
import kr.co.hconnect.polihealth_sdk_android.api.dto.response.Daily2Response
import kr.co.hconnect.polihealth_sdk_android.api.dto.response.Daily3Response

class DailyApiService {
    private val TAG = "DailyApiService"

    /**
     * TODO: Protocol01 전송
     *
     * @param ltmModel
     * @return SleepCommResponse
     */
    suspend fun sendProtocol01(ltmModel: LTMModel): Daily1Response {
        val response: Daily1Response = DailyProtocol01API.requestPost(
            DateUtil.getCurrentDateTime(),
            ltmModel
        )
        return response
    }

    /**
     * TODO: Protocol02 전송
     *
     * @param context : 전송 시, bin 파일을 저장하기 위한 컨텍스트. null일 경우, bin 파일 저장 X
     * @return SleepCommResponse
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun sendProtocol02(context: Context? = null): Daily2Response? {
        val protocol2Bytes = DailyProtocol02API.flush(context)
        if (protocol2Bytes.isNotEmpty()) {
            val response: Daily2Response =
                DailyProtocol02API.requestPost(
                    DateUtil.getCurrentDateTime(),
                    protocol2Bytes
                )
            return response
        } else {
            return null
        }
    }

    /**
     * TODO: Protocol03 전송
     *
     * @param hrSpo2
     * @return SleepCommResponse
     */
    suspend fun sendProtocol03(hrSpo2: HRSpO2): Daily3Response {
        val response: Daily3Response = DailyProtocol03API.requestPost(
            DateUtil.getCurrentDateTime(),
            hrSpo2
        )
        return response
    }
}