package kr.co.hconnect.polihealth_sdk_android_app.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class LTMRequest(
    val reqDate: String,
    val userSno: Int,
    val data: LTMModel
)