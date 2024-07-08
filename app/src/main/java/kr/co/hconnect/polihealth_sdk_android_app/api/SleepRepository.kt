package kr.co.hconnect.polihealth_sdk_android_app.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepSessionAPI

class SleepRepository {
    fun requestStartBand() {
        CoroutineScope(Dispatchers.IO).launch {
            SleepSessionAPI.requestStartBand()
        }
    }
}