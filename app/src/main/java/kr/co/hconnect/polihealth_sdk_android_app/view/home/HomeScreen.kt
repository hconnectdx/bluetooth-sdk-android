package kr.co.hconnect.polihealth_sdk_android_app.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kr.co.hconnect.polihealth_sdk_android_app.PoliBLE
import kr.co.hconnect.polihealth_sdk_android_app.view.home.compose.BLEScanButton
import kr.co.hconnect.polihealth_sdk_android_app.view.home.compose.ScanList
import kr.co.hconnect.polihealth_sdk_android_app.viewmodel.ScanResultViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    scanViewModel: ScanResultViewModel = viewModel()
) {
    val context = LocalContext.current
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
                    OutlinedButton(onClick = {
                        PoliBLE.init(context = context)
                    }) {
                        Text(text = "BLE Init")
                    }
                    Box(modifier = Modifier.width(10.dp))
                    BLEScanButton(scanViewModel = scanViewModel)
                }
                ScanList(navController, scanViewModel = scanViewModel)
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