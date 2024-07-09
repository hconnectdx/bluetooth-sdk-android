package kr.co.hconnect.polihealth_sdk_android_app.viewmodel

import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BondedDevicesViewModel : ViewModel() {
    val bondedDevices = mutableStateOf<List<BluetoothDevice>>(emptyList())
}