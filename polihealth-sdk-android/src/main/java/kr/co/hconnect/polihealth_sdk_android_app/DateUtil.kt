package kr.co.hconnect.polihealth_sdk_android_app

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object DateUtil {
    fun getCurrentDateTime(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            current.format(formatter)
        } else {
            val current = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
            formatter.format(current)
        }
    }
}