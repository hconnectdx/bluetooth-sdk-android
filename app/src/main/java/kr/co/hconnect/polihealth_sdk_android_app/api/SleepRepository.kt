package kr.co.hconnect.polihealth_sdk_android_app.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepSessionAPI

class SleepRepository {
    suspend fun requestStartBand(): SleepResponse.SleepCommResponse {
        val deferred = CoroutineScope(Dispatchers.IO).async {
            SleepSessionAPI.requestSleepStart()
        }

        return deferred.await()
    }
}