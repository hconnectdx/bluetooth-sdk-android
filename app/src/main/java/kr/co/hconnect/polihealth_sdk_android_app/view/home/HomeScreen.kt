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
import androidx.compose.material3.Text
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
import kotlinx.coroutines.withContext
import kr.co.hconnect.permissionlib.PermissionManager
import kr.co.hconnect.polihealth_sdk_android_app.Permissions
import kr.co.hconnect.polihealth_sdk_android_app.PoliBLE
import kr.co.hconnect.polihealth_sdk_android_app.api.dto.request.HRSpO2
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol07API
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol08API
import kr.co.hconnect.polihealth_sdk_android_app.api.sleep.SleepProtocol09API
import kr.co.hconnect.polihealth_sdk_android_app.service.sleep.SleepApiService
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
                Column {
                    Row {
                        Box(modifier = Modifier.width(10.dp))
                        BLEScanButton(scanViewModel = scanViewModel)
                        Button(onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                val response = withContext(Dispatchers.IO) {
                                    SleepApiService().sendStartSleep()
                                }

                                if (response.retCd == "0") {
                                    Log.d("HomeScreen", "수면 시작 요청 성공 response: $response")
                                } else {
                                    Log.e(
                                        "HomeScreen",
                                        "수면시작 실패! retCd: ${response.retCd}\nretMsg: ${response.retMsg}\nretDate: ${response.resDate}"
                                    )
                                }
                            }
                        }) {
                            Text(text = "Start Sleep")
                        }

                        Button(onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                val response = withContext(Dispatchers.IO) {
                                    SleepApiService().sendEndSleep()
                                }

                                if (response.retCd == "0") {
                                    Log.d("HomeScreen", "수면 종료 요청 성공 response: $response")
                                } else {
                                    Log.e(
                                        "HomeScreen",
                                        "수면종료 실패! retCd: ${response.retCd}\nretMsg: ${response.retMsg}\nretDate: ${response.resDate}"
                                    )
                                }
                            }

                        }) {
                            Text(text = "End Sleep")
                        }
                    }
                    Row {
                        Button(onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                val response = withContext(Dispatchers.IO) {
                                    SleepApiService().sendProtocol06(context = context)
                                }

                                if (response == null) {
                                    Log.e(
                                        "HomeScreen",
                                        "Protocol06: response: $response 전송할 데이터가 없습니다."
                                    )
                                } else {
                                    Log.d("HomeScreen", "Protocol06 전송 성공 response: $response")
                                }
                            }


                        }) {
                            Text(text = "Test6")
                        }
                        Button(onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                val response = withContext(Dispatchers.IO) {
                                    SleepApiService().sendProtocol07(context = context)
                                }

                                if (response == null) {
                                    Log.e(
                                        "HomeScreen",
                                        "Protocol07: response: $response 전송할 데이터가 없습니다."
                                    )
                                } else {
                                    Log.d("HomeScreen", "Protocol07 전송 성공 response: $response")
                                }
                            }
                        }) {
                            Text(text = "Test7")
                        }
                        Button(onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                val response = withContext(Dispatchers.IO) {
                                    SleepApiService().sendProtocol08(context = context)
                                }

                                if (response == null) {
                                    Log.e(
                                        "HomeScreen",
                                        "Protocol08: response: $response 전송할 데이터가 없습니다."
                                    )
                                } else {
                                    Log.d("HomeScreen", "Protocol08 전송 성공 response: $response")
                                }
                            }

                        }) {
                            Text(text = "Test8")
                        }
                        Button(onClick = {
                            SleepProtocol09API.testPost(
                                hrSpO2 = HRSpO2(
                                    100,
                                    120
                                )
                            )
                        }) {
                            Text(text = "Test9")
                        }
                    }
                }
                BondedList(
                    navController = navController,
                    deviceViewModel = deviceViewModel,
                    bondedDeviceViewModel = bondedDevicesViewModel
                )
                ScanList(
                    navController = navController,
                    scanViewModel = scanViewModel,
                    deviceViewModel = deviceViewModel
                )
            }
        }
    )
}


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)

fun HomeScreenPreview() {
    HomeScreen(navController = NavController(LocalContext.current))
}