package kr.co.hconnect.polihealth_sdk_android_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kr.co.hconnect.permissionlib.PermissionManager
import kr.co.hconnect.polihealth_sdk_android_app.ui.theme.PolihealthsdkandroidTheme
import kr.co.hconnect.polihealth_sdk_android_app.view.detail.CharacteristicDetail
import kr.co.hconnect.polihealth_sdk_android_app.view.detail.DeviceDetailScreen
import kr.co.hconnect.polihealth_sdk_android_app.view.home.HomeScreen
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.BondedDevicesViewModel
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.DeviceViewModel
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.ScanResultViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolihealthsdkandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        PermissionManager.registerPermissionLauncher(this)
        PoliBLE.init(this)
        PoliClient.init(
            baseUrl = BuildConfig.API_URL,
            clientId = BuildConfig.CLIENT_ID,
            clientSecret = BuildConfig.CLIENT_SECRET
        )
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val scanViewmodel = viewModel<ScanResultViewModel>()
    val deviceViewModel = viewModel<DeviceViewModel>()
    val bondedDevicesViewModel = viewModel<BondedDevicesViewModel>()

    MaterialTheme {
        Surface {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        scanViewModel = scanViewmodel,
                        bondedDevicesViewModel = bondedDevicesViewModel,
                        deviceViewModel = deviceViewModel,
                        navController = navController
                    )
                }
                composable("deviceDetail") {
                    DeviceDetailScreen(
                        navController = navController,
                        deviceViewModel = deviceViewModel
                    )
                }
                composable("charDetail") {
                    CharacteristicDetail(
                        navController = navController,
                        deviceViewModel = deviceViewModel
                    )
                }
            }
        }
    }
}