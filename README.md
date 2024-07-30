
# PoliHealth SDK for Android

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## 목차
- [PoliHealth SDK](#polihealth-sdk)
  - [주요 기능](#주요-기능)
  - [설치](#설치)
  - [초기화](#초기화)
  - [스캔 사용 예시](#스캔-사용)
  - [연결하기 사용 예시](#연결-및-통신)


## PoliHealth SDK

### 주요 기능
- **데이터 수집:** Poli 밴드를 통해 데이터를 수집합니다.
- **데이터 전송:** 수집된 데이터를 서버로 전송합니다.

### 설치
```groovy

repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/hconnectdx/bluetooth-sdk-android")
            credentials {
                username = "hconnectdx"
                password = "****" // 이메일로 문의를 주시면 토큰을 발급해드립니다.
            
            }
        }
    }

dependencies {
    implementation 'com.polihealth:sdk:0.0.5'
}
```

### 초기화
```kotlin
override fun onStart() {
    super.onStart()

    PoliBLE.init(this)
    PoliClient.init(
        baseUrl = BuildConfig.API_URL,
        clientId = BuildConfig.CLIENT_ID,
        clientSecret = BuildConfig.CLIENT_SECRET
    )
    PermissionManager.registerPermissionLauncher(this)
    PoliClient.userAge = 111
    PoliClient.userSno = 999
}
```

### 스캔 사용
```kotlin

PoliBLE.startScan { scanItem ->
    if (scanItem.device.name.isNullOrEmpty()) return@startScan
        deviceList.find { device -> device.address == scanItem.device.address }?: run {
            deviceList.add(scanItem.device)
            deviceListAdapter.notifyDataSetChanged()
        }
}
```

### 연결 및 통신
```kotlin
private fun connectToDevice(device: BluetoothDevice) {
    PoliBLE.connectDevice(
        this,
        device,
        onReceive = { type: ProtocolType, response: PoliResponse? ->
            Log.d("DeviceDetailActivity", "onReceive: $type, $response")
        },
        onConnState = { state ->
         
        },
        onGattServiceState = { gatt ->
            CoroutineScope(Dispatchers.Main).launch {
                when (gatt) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        tvStatus.text = "Status: GATT Success"
                        serviceList.clear()
                        // 장치의 모든 서비스를 추가
                        serviceList.addAll(PoliBLE.getGattServiceList())
                        // 각 서비스의 특성을 매핑
                        for (service in serviceList) {
                            characteristicMap[service.uuid.toString()] = service.characteristics
                        }
                        expandableListAdapter.notifyDataSetChanged()
                    }

                    else -> {
                        tvStatus.text = "Status: GATT Failure"
                    }
                }
            }
        },
        onBondState = { bondState ->
          
        },
        onSubscriptionState = { state ->
           
        }
    )
}
```

## 문의
문의 사항이 있으시면 [이메일](kmwdev@hconnect.co.kr)로 연락해 주세요.



## Github Branch

### 1. 개발을 시작할 때
- 개발을 시작할 때는 `Issue`를 생성합니다.

![스크린샷 2024-07-07 오전 2 59 39](https://github.com/Auction-shop-project/As_FE/assets/137240956/2cd78f3a-ec5d-47f4-95c6-b97fcf59f46c)

### 2. Branch & Commit
- `develop Branch` 에서 `새로운 Branch`를 생성합니다.
- `해당 Branch`에서 작업을 합니다.
- 커밋 규칙은 다음을 따릅니다
- `(컨벤션코드)(커밋 제목)(#이슈넘버)`
  
![스크린샷 2024-07-07 오전 3 33 01](https://github.com/Auction-shop-project/As_FE/assets/137240956/2e598b93-eb2a-4434-8bb2-4977933ef5ae)

  

### 3. 개발을 종료할 때 (Pull Request)

- Title은 아래와 같은 포맷으로 PR을 요청합니다.
- `(컨벤션코드)(제목을 입력)(#이슈넘버)`
  
![image](https://github.com/Auction-shop-project/As_FE/assets/137240956/4733648b-2ea5-4ad5-a01f-92f4845b0db3)


## Convention
| Code | Description |
| --- | --- |
| Feat: | 새로운 기능을 만듭니다. |
| Bug: | 버그를 수정합니다. |


