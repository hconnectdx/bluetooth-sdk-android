package kr.co.hconnect.polihealth_sdk_android_app.protocol_session

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

open class ByteController {
    private var _byteArray: ByteArray = byteArrayOf()
    val byteArray: ByteArray
        get() = _byteArray

    // addByte 함수: 바이트 배열을 추가
    fun addByte(byteArray: ByteArray) {
        _byteArray += byteArray // 기존의 _byteArray에 새로운 byteArray를 추가
    }

    // flush 함수: 데이터를 반환하고 _byteArray를 비움
    fun flush(context: Context): ByteArray? {
        return if (_byteArray.isNotEmpty()) {
            val tempByteArray = _byteArray.clone() // 현재 _byteArray를 클론
            _byteArray = byteArrayOf() // _byteArray를 빈 배열로 초기화
            Log.d("RepositoryProtocol06", tempByteArray.toHexString()) // 클론한 데이터를 로그로 출력
            saveToFile(context, tempByteArray, "protocol08.bin") // 클론한 데이터를 파일로 저장
            tempByteArray // 클론한 데이터를 반환
        } else {
            null // 데이터가 없으면 null 반환
        }
    }

    // 바이트 배열을 16진수 문자열로 변환하는 헬퍼 함수
    private fun ByteArray.toHexString(): String =
        joinToString(separator = " ") { byte -> "%02x".format(byte) }

    // flush 결과를 파일로 저장하는 함수
    private fun saveToFile(context: Context, data: ByteArray?, fileName: String) {
        data?.let {
            try {
                val outputStream: OutputStream?
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/octet-stream")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/")
                }

                val uri = context.contentResolver.insert(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                    contentValues
                )
                outputStream = uri?.let { context.contentResolver.openOutputStream(it) }

                if (outputStream != null) {
                    outputStream.write(it)
                    outputStream.close()
                    Log.d("ByteController", "Data saved to file: $fileName in Download folder")
                } else {
                    Log.e("ByteController", "Failed to create OutputStream")
                }
            } catch (e: Exception) {
                Log.e("ByteController", "Error saving data to file", e)
            }
        } ?: run {
            Log.d("ByteController", "No data to save")
        }
    }
}