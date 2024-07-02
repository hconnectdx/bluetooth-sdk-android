package kr.co.hconnect.polihealth_sdk_android_app.view.home.compose

import android.bluetooth.le.ScanResult
import android.os.Build
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.co.hconnect.permissionlib.PermissionManager
import kr.co.hconnect.polihealth_sdk_android_app.Permissions
import kr.co.hconnect.polihealth_sdk_android_app.PoliBLE
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.ScanResultViewModel

@Composable
fun BLEScanButton(scanViewModel: ScanResultViewModel = viewModel()) {
    OutlinedButton(onClick = {
        val blePermissions =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) Permissions.PERMISSION_SDK_31
            else Permissions.PERMISSION_SDK_30
        try {
            PermissionManager.launchPermissions(blePermissions) {
                val deniedItems = it.filter { p -> !p.value }
                if (deniedItems.isEmpty()) {
                    PoliBLE.startScan { scanItem ->
                        val device = scanItem.scanRecord
                        if (device?.deviceName.isNullOrEmpty()) return@startScan

                        val scanResult: ScanResult? =
                            scanViewModel.scanResults.find { vmItem ->
                                val address = vmItem.device.address
                                scanItem.device.address == address
                            }
                        if (scanResult == null) {
                            scanViewModel.scanResults.add(scanItem)
                        }
                    }
                } else {
                    println("Permission denied: $deniedItems")
                }
            }

        } catch (e: Exception) {
            println("Error: $e")
        }
    }) {
        Text(text = "BLE Scan")
    }
}

@Composable
@Preview(showSystemUi = true)
fun HomeBLEScanButtonPreview() {
    BLEScanButton()
}