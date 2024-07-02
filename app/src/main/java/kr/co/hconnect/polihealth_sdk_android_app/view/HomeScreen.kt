package kr.co.hconnect.polihealth_sdk_android_app.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kr.co.hconnect.bluetoothlib.HCBle
import kr.co.hconnect.permissionlib.PermissionManager
import kr.co.hconnect.polihealth_sdk_android_app.Permissions

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    Scaffold(

        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            Column(
                Modifier
                    .padding(padding)
                    .background(color = Color.Yellow)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    OutlinedButton(onClick = { HCBle.init(context = context) }) {
                        Text(text = "BLE Init")
                    }
                    OutlinedButton(onClick = {
                        val blePermissions =
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) Permissions.PERMISSION_SDK_31
                            else Permissions.PERMISSION_SDK_30

                        try {
                            PermissionManager.launchPermissions(blePermissions) {
                                val deniedItems = it.filter { p -> !p.value }
                                if (deniedItems.isEmpty()) {
                                    HCBle.scanLeDevice {
                                        println("Scan result: $it")
                                    }
                                } else {
                                    println("Permission denied: $deniedItems")
                                }
                            }
                            HCBle.scanLeDevice {
                                println("Scan result: $it")
                            }
                        } catch (e: Exception) {
                            println("Error: $e")
                        }
                    }) {
                        Text(text = "BLE Scan")
                    }
                }
            }
        }
    )
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)

fun HomeScreenPreview() {
    HomeScreen()
}