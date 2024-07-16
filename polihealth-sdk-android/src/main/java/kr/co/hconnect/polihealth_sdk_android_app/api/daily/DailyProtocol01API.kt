package kr.co.hconnect.polihealth_sdk_android_app.api.daily

import android.os.Build
import androidx.annotation.RequiresApi
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.util.AttributeKey
import kotlinx.coroutines.runBlocking
import kr.co.hconnect.polihealth_sdk_android_app.DateUtil
import kr.co.hconnect.polihealth_sdk_android_app.PoliClient
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.request.HRSpO2
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.request.HRSpO2Request
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.request.LTMModel
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.request.LTMRequest
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.SleepResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.response.toSleepCommResponse
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepSessionAPI

object DailyProtocol01API {
    /**
     * TODO: 조도값, 피부온도값, 활동량값을 서버로 전송하는 API
     *
     * @param reqDate ex) 20240704054513 (yyyyMMddHHmmss)
     * @param LTMModel
     *
     * @return SleepCommResponse
     * */
    suspend fun requestPost(
        reqDate: String,
        ltmModel: LTMModel
    ): SleepResponse.SleepCommResponse {

        val requestBody = LTMRequest(
            reqDate = reqDate,
            userSno = SleepSessionAPI.userSno,
            data = ltmModel
        )

        val response = PoliClient.client.post("poli/day/protocol1") {
            setBody(requestBody)
        }.call.attributes[AttributeKey("body")].toString().toSleepCommResponse()

        return response
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    fun testPost(
        hrSpO2: HRSpO2
    ) = runBlocking {
        try {
            val requestBody = HRSpO2Request(
                reqDate = "20240704054513",
                userSno = SleepSessionAPI.userSno,
                data = HRSpO2Request.Data(
                    oxygenVal = hrSpO2.spo2,
                    heartRateVal = hrSpO2.heartRate
                )
            )

            PoliClient.client.post("poli/day/protocol1") {
                setBody(requestBody)
            }.call.attributes[AttributeKey("body")].toString()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun parseLTMData(data: ByteArray): LTMModel {
        val header = data[0]
        val dataNum = data[1]

        val sampleSize = 16
        val totalSamples = 12
        var offset = 2 // Skip header and dataNum

        val luxList = mutableListOf<LTMModel.Lux>()
        val skinTempList = mutableListOf<LTMModel.SkinTemp>()
        val metsList = mutableListOf<LTMModel.Mets>()

        for (i in 0 until totalSamples) {
            val time = DateUtil.getCurrentDateTime()

            // METs 데이터 추출 (2Bytes * 5EA)
            for (j in 0 until 5) {
                val metsValue =
                    ((data[offset + 2 * j].toInt() shl 8) or (data[offset + 2 * j + 1].toInt() and 0xFF)).toShort()
                        .toInt()
                metsList.add(LTMModel.Mets(time, metsValue))
            }

            // Temp 데이터 추출 (4Bytes)
            val tempValue =
                ((data[offset + 10].toInt() shl 24) or (data[offset + 11].toInt() and 0xFF shl 16) or (data[offset + 12].toInt() and 0xFF shl 8) or (data[offset + 13].toInt() and 0xFF))
            skinTempList.add(LTMModel.SkinTemp(time, tempValue))

            // Lux 데이터 추출 (2Bytes)
            val luxValue =
                ((data[offset + 14].toInt() shl 8) or (data[offset + 15].toInt() and 0xFF)).toShort()
                    .toInt()
            luxList.add(LTMModel.Lux(time, luxValue))

            // 오프셋 증가
            offset += sampleSize
        }

        val ltmModel = LTMModel(
            lux = luxList.toTypedArray(),
            skinTemp = skinTempList.toTypedArray(),
            mets = metsList.toTypedArray()
        )

        return ltmModel
    }

    val testRawData = byteArrayOf(
        0x01,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x42,
        0x12,
        0x00,
        0x00,
        0xff.toByte(),
        0xff.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x41,
        0xc8.toByte(),
        0x00,
        0x00,
        0x80.toByte(),
        0x00,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x42,
        0x34,
        0x00,
        0x00,
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x42,
        0x12,
        0x00,
        0x00,
        0xff.toByte(),
        0xff.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x41,
        0xc8.toByte(),
        0x00,
        0x00,
        0x80.toByte(),
        0x00,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x42,
        0x34,
        0x00,
        0x00,
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x42,
        0x12,
        0x00,
        0x00,
        0xff.toByte(),
        0xff.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x41,
        0xc8.toByte(),
        0x00,
        0x00,
        0x80.toByte(),
        0x00,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x42,
        0x34,
        0x00,
        0x00,
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x42,
        0x12,
        0x00,
        0x00,
        0xff.toByte(),
        0xff.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x41,
        0xc8.toByte(),
        0x00,
        0x00,
        0x80.toByte(),
        0x00,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x75,
        0x30,
        0x3a,
        0x98.toByte(),
        0x00,
        0x00,
        0x42,
        0x34,
        0x00,
        0x00,
        0x00,
        0x00
    )
}