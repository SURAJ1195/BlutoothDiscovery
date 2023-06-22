package com.example.bluetoothdiscovery

import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class BluetoothVM:ViewModel() {
    val list= mutableStateListOf<BluetoothDevice>()

}