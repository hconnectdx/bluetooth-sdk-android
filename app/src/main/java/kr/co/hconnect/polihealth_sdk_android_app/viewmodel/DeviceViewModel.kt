package kr.co.hconnect.polihealth_sdk_android_app.viewmodel

import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kr.co.hconnect.polihealth_sdk_android_app.BLEState

class DeviceViewModel : ViewModel() {
    var device = mutableStateOf<BluetoothDevice?>(null)
    var connectState = mutableIntStateOf(BLEState.STATE_DISCONNECTED)
    var isBonded = mutableIntStateOf(BLEState.BOND_NONE)
    var isGattConnected = mutableIntStateOf(BLEState.GATT_FAILURE)
}