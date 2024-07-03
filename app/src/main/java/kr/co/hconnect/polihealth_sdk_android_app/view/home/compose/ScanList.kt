package kr.co.hconnect.polihealth_sdk_android_app.view.home.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kr.co.hconnect.polihealth_sdk_android_app.PoliBLE
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.ScanResultViewModel

@SuppressLint("MissingPermission")
@Composable
fun ScanList(
    navController: NavController,
    scanViewModel: ScanResultViewModel = viewModel()
) {
    LazyColumn {
        items(scanViewModel.scanResults.size) { item ->
            Box(
                Modifier.clickable {
                    PoliBLE.stopScan()
                    scanViewModel.selDevice.value = scanViewModel.scanResults[item]
                    navController.navigate("deviceDetail")
                }
            ) {
                ListItem(
                    headlineContent = {
                        Text(
                            text = scanViewModel.scanResults[item].device.name
                                ?: "Unknown Device"
                        )
                    }

                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HomeScanListPreview() {
    ScanList(navController = rememberNavController())
}