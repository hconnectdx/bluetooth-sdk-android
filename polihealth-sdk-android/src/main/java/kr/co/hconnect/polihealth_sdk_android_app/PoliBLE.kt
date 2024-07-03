package kr.co.hconnect.polihealth_sdk_android_app

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattService
import android.bluetooth.le.ScanResult
import android.content.Context
import android.util.Log
import kr.co.hconnect.bluetoothlib.HCBle
import kr.co.hconnect.polihealth_sdk_android_app.protocol_session.RepositoryProtocol06
import kr.co.hconnect.polihealth_sdk_android_app.protocol_session.RepositoryProtocol07
import kr.co.hconnect.polihealth_sdk_android_app.protocol_session.RepositoryProtocol08

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
        onSubscriptionState: (state: Boolean) -> Unit,
        onReceive: (byteArray: ByteArray) -> Unit
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
            onSubscriptionState = { state ->
                onSubscriptionState.invoke(state)
            },
            onCharacteristicChanged = { byteArray ->
                byteArray?.let {

                    when (it[0]) {
                        0x06.toByte() -> {
                            RepositoryProtocol06.addByte(removeFrontTwoBytes(it))
                        }

                        0x07.toByte() -> {
                            RepositoryProtocol08.flush()
                            RepositoryProtocol07.addByte(removeFrontTwoBytes(it))
                        }

                        0x08.toByte() -> {
                            RepositoryProtocol06.flush()
                            RepositoryProtocol08.addByte(removeFrontTwoBytes(it))
                        }

                        0x09.toByte() -> {
                            Log.d("PoliBLE", "RepositoryProtocol09 를 전송하였습니다.")
                        }
                    }

                    onReceive.invoke(it)
                } ?: run {
                    Log.e("PoliBLE", "byteArray is null")
                }
            }
        )
    }

    private fun removeFrontTwoBytes(byteArray: ByteArray): ByteArray {
        // 배열의 길이가 2 이상인 경우에만 앞의 2바이트를 제거
        if (byteArray.size > 2) {
            return byteArray.copyOfRange(2, byteArray.size)
        }
        // 배열의 길이가 2 이하인 경우 빈 배열 반환
        return ByteArray(0)
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

    fun getBondedDevices(): List<BluetoothDevice> {
        return HCBle.getBondedDevices()
    }
}