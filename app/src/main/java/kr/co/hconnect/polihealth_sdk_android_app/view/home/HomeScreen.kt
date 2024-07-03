package kr.co.hconnect.polihealth_sdk_android_app.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kr.co.hconnect.polihealth_sdk_android_app.PoliBLE
import kr.co.hconnect.polihealth_sdk_android_app.view.home.compose.BLEScanButton
import kr.co.hconnect.polihealth_sdk_android_app.view.home.compose.BondedList
import kr.co.hconnect.polihealth_sdk_android_app.view.home.compose.ScanList
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.BondedDevicesViewModel
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.DeviceViewModel
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.ScanResultViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    bondedDevicesViewModel: BondedDevicesViewModel = viewModel(),
    deviceViewModel: DeviceViewModel = viewModel(),
    scanViewModel: ScanResultViewModel = viewModel()
) {
    LaunchedEffect(key1 = Unit) {
        bondedDevicesViewModel.bondedDevices.value = PoliBLE.getBondedDevices()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            Column(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Box(modifier = Modifier.width(10.dp))
                    BLEScanButton(scanViewModel = scanViewModel)
                }
                BondedList(
                    navController = navController,
                    deviceViewModel = deviceViewModel,
                    bondedDeviceViewModel = bondedDevicesViewModel
                )
                ScanList(
                    navController = navController,
                    scanViewModel = scanViewModel
                )
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
    HomeScreen(navController = NavController(LocalContext.current))
}