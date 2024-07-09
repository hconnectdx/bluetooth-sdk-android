package kr.co.hconnect.polihealth_sdk_android_app.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepStartResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepSessionAPI

class SleepRepository {
    suspend fun requestStartBand(): SleepStartResponse {
        val deferred = CoroutineScope(Dispatchers.IO).async {
            SleepSessionAPI.requestSleepStart(
                reqDate = "20240708074048",
                userSno = 1
            )
        }

        return deferred.await()
    }
}