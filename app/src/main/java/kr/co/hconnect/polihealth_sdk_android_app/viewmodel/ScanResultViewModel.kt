package kr.co.hconnect.polihealth_sdk_android_app.viewmodel

import android.bluetooth.le.ScanResult
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ScanResultViewModel : ViewModel() {
    var scanResults = mutableStateListOf<ScanResult>()
    var selDevice = mutableStateOf<ScanResult?>(null)
}