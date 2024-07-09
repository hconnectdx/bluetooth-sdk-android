package kr.co.hconnect.polihealth_sdk_android_app.view.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.hconnect.permissionlib.PermissionManager
import kr.co.hconnect.polihealth_sdk_android_app.Permissions
import kr.co.hconnect.polihealth_sdk_android_app.PoliBLE
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol06API
import kr.co.hconnect.polihealth_sdk_android_app.api.SleepRepository
import kr.co.hconnect.polihealth_sdk_android_app.view.home.compose.BLEScanButton
import kr.co.hconnect.polihealth_sdk_android_app.view.home.compose.BondedList
import kr.co.hconnect.polihealth_sdk_android_app.view.home.compose.ScanList
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.BondedDevicesViewModel
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.DeviceViewModel
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.ScanResultViewModel

@Composable
fun RequestPermissions(bondedDevicesViewModel: BondedDevicesViewModel = viewModel()) {
    val context = LocalContext.current
    val permissions = Permissions.PERMISSION_SDK_31

    LaunchedEffect(Unit) {
        if (PermissionManager.isGrantedPermissions(context, permissions)) {
            bondedDevicesViewModel.bondedDevices.value = PoliBLE.getBondedDevices()
        } else {
            // 권한이 부여되지 않은 경우 권한 요청
            PermissionManager.launchPermissions(permissions) { grantedPermissions ->
                if (grantedPermissions.all { it.value }) {
                    bondedDevicesViewModel.bondedDevices.value = PoliBLE.getBondedDevices()
                } else {

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeScreen(
    navController: NavController,
    bondedDevicesViewModel: BondedDevicesViewModel = viewModel(),
    deviceViewModel: DeviceViewModel = viewModel(),
    scanViewModel: ScanResultViewModel = viewModel()
) {
    val context = LocalContext.current
    RequestPermissions(bondedDevicesViewModel = bondedDevicesViewModel)
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
                    Button(onClick = { SleepProtocol06API.requestPost(context = context) }) {

                    }
                    Button(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val a = SleepRepository().requestStartBand()
                            Log.d("HomeScreen", a.retCd!!)
                            Log.d("HomeScreen", a.resDate!!)
                            Log.d("HomeScreen", a.retMsg!!)
                            a.data?.sessionId?.let { Log.d("HomeScreen", it) }
                            Log.d("HomeScreen", a.toString())
                        }

                    }) {

                    }
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