package kr.co.hconnect.polihealth_sdk_android_app

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattService
import android.bluetooth.le.ScanResult
import android.content.Context
import android.util.Log
import kr.co.hconnect.bluetoothlib.HCBle

object PoliBLE {
    fun init(context: Context) {
        HCBle.init(context)
    }

    fun startScan(scanDevice: (ScanResult) -> Unit) {
        HCBle.scanLeDevice { device ->
            scanDevice.invoke(device)
        }
    }

    fun stopScan() {
        HCBle.scanStop()
    }

    fun connectDevice(
        device: BluetoothDevice,
        onConnState: (state: Int) -> Unit,
        onGattServiceState: (gatt: Int) -> Unit,
        onBondState: (bondState: Int) -> Unit,
        onReceive: (data: ByteArray) -> Unit
    ) {
        HCBle.connectToDevice(
            device = device,
            onConnState = { state ->
                onConnState.invoke(state)
            },
            onGattServiceState = { gatt ->
                onGattServiceState.invoke(gatt)
            },
            onBondState = { bondState ->
                onBondState.invoke(bondState)
            },
            onReceive = { data ->
                Log.d("PoliBLE", "onReceive: $data")
            })
    }

    fun disconnectDevice() {
        HCBle.disconnect()
    }

    /**
     * TODO: GATT Service 리스트를 반환합니다.
     * 블루투스가 연결되어 onServicesDiscovered 콜백이 호출 돼야 사용가능합니다.
     * @return
     */
    fun getGattServiceList(): List<BluetoothGattService> {
        return HCBle.getGattServiceList()
    }

    /**
     * TODO: 서비스 UUID를 설정합니다.
     * 사용 하고자 하는 서비스 UUID를 설정합니다.
     * @param uuid
     */
    fun setServiceUUID(uuid: String) {
        HCBle.setServiceUUID(uuid)
    }

    /**
     * TODO: 캐릭터리스틱 UUID를 설정합니다.
     * 사용 하고자 하는 캐릭터리스틱 UUID를 설정합니다.
     * @param characteristicUUID
     */
    fun setCharacteristicUUID(characteristicUUID: String) {
        HCBle.setCharacteristicUUID(characteristicUUID)
    }

    /**
     * TODO: 캐릭터리스틱을 읽습니다.
     * setCharacteristicUUID로 설정된 캐릭터리스틱을 읽습니다.
     */
    fun readCharacteristic() {
        HCBle.readCharacteristic()
    }

    /**
     * TODO: 캐릭터리스틱을 쓰기합니다.
     * setCharacteristicUUID로 설정된 캐릭터리스틱에 데이터를 쓰기합니다.
     * @param data
     */
    fun writeCharacteristic(data: ByteArray) {
        HCBle.writeCharacteristic(data)
    }

    /**
     * TODO: 캐릭터리스틱 알림을 설정합니다.
     * setCharacteristicUUID로 설정된 캐릭터리스틱에 알림을 설정합니다.
     * @param isEnable
     */
    fun setCharacteristicNotification(isEnable: Boolean) {
        HCBle.setCharacteristicNotification(isEnable)
    }
}