package kr.co.hconnect.polihealth_sdk_android_app.api.sleep

import android.util.Log

object SleepProtocol07API {
    private var _byteArray: ByteArray = byteArrayOf()
    val byteArray: ByteArray
        get() = _byteArray

    fun addByte(byteArray: ByteArray) {
        _byteArray.plus(byteArray)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun flush(): ByteArray {
        val tempByteArray = _byteArray.clone()
        _byteArray = byteArrayOf()
        Log.e("07을 배출합니다.", tempByteArray.toHexString())
        return tempByteArray
    }

}