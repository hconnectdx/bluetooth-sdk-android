package kr.co.hconnect.polihealth_sdk_android_app.api

import kotlinx.serialization.Serializable

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