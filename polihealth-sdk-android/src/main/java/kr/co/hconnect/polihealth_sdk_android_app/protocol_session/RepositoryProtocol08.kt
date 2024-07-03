package kr.co.hconnect.polihealth_sdk_android_app.protocol_session

import android.util.Log

object RepositoryProtocol08 {
    private var _byteArray: ByteArray = byteArrayOf()
    val byteArray: ByteArray
        get() = _byteArray

    // addByte 함수: 바이트 배열을 추가
    fun addByte(byteArray: ByteArray) {
        _byteArray += byteArray // 기존의 _byteArray에 새로운 byteArray를 추가
    }

    // flush 함수: 데이터를 반환하고 _byteArray를 비움
    fun flush(): ByteArray? {
        return if (_byteArray.isNotEmpty()) {
            val tempByteArray = _byteArray.clone() // 현재 _byteArray를 클론
            _byteArray = byteArrayOf() // _byteArray를 빈 배열로 초기화
            Log.d("RepositoryProtocol06", tempByteArray.toHexString()) // 클론한 데이터를 로그로 출력
            tempByteArray // 클론한 데이터를 반환
        } else {
            null // 데이터가 없으면 null 반환
        }
    }

    // 바이트 배열을 16진수 문자열로 변환하는 헬퍼 함수
    private fun ByteArray.toHexString(): String =
        joinToString(separator = " ") { byte -> "%02x".format(byte) }
}