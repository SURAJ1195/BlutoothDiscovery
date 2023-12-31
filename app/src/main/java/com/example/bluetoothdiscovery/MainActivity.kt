package com.example.bluetoothdiscovery

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.bluetoothdiscovery.ui.theme.BluetoothDiscoveryTheme
import java.util.UUID

class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BluetoothDevices.listt.clear()
            val showList = remember {
                mutableStateListOf<BluetoothDevice>()
            }
            val showListDevice = remember {
                mutableStateListOf<BluetoothDevice>()
            }
            val bluetoothBR by lazy {
                Reciever(this@MainActivity, showList) {
                    showListDevice.add(it)
                }
            }
            val bluetoothPairingReceiver by lazy {
                BluetoothPairingReceiver(this@MainActivity){
                    if(it!= null){
                        Toast.makeText(this@MainActivity,"$it",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            val YOUR_UUID: UUID = UUID.randomUUID()

            //  val bluetoothBR=Reciever(this@MainActivity)
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            val bluetoothManager = BluetoothManager(this@MainActivity)
            val launcher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { isGranted ->

                    bluetoothManager.adapter.startDiscovery()


                }




            LaunchedEffect(key1 = showList) {
                Log.d(
                    "jhkj" +
                            "", "onCreate: ${showList}"
                )
            }

            LaunchedEffect(key1 = true) {

                registerReceiver(bluetoothBR, filter)
                registerReceiver(bluetoothPairingReceiver,filter)
                if (bluetoothManager.checkBluetoothEnabled()) {
                    Toast.makeText(this@MainActivity, "BluetoothEnabled", Toast.LENGTH_SHORT).show()
                    if (ActivityCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.BLUETOOTH_SCAN
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(this@MainActivity, "Allow permission", Toast.LENGTH_SHORT)
                            .show()

                        launcher.launch(
                            arrayOf(
                                Manifest.permission.BLUETOOTH_SCAN,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        )
                        return@LaunchedEffect
                    } else {
                        Toast.makeText(this@MainActivity, "permission allowed", Toast.LENGTH_SHORT)
                            .show()
                    }

                } else {
                    Toast.makeText(this@MainActivity, "BluetoothNotEnabled", Toast.LENGTH_SHORT)
                        .show()
                    Toast.makeText(this@MainActivity, "suraj", Toast.LENGTH_LONG).show()
                }

            }

            BluetoothDiscoveryTheme {

                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyColumn {
                            items(showListDevice) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .padding(
                                            start = 10.dp,
                                            end = 10.dp,
                                            top = 8.dp,
                                            bottom = 8.dp
                                        ).clickable {
                                                    it.createBond()
                                        },
                                    elevation = CardDefaults.cardElevation(8.dp)
                                ) {
                                    Text(text = it.name ?: "no name")
                                }

                            }
                        }

                    }
                }
            }
        }

    }
}



