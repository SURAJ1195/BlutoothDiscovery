package com.example.bluetoothdiscovery

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

class BluetoothPairingReceiver(context:Context,private val onPaired:(Int?) -> Unit = {}): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action: String? = intent?.action
        when (action) {
            BluetoothDevice.ACTION_PAIRING_REQUEST -> {
                val device: BluetoothDevice? =
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

                if (context?.let {
                        ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.BLUETOOTH_CONNECT
                        )
                    } != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                onPaired.invoke(device?.bondState)
                val pin: String = "1234" // Set your PIN for pairing (this is an example)
                device?.setPin(pin.toByteArray())
               device?.bondState
            }
            BluetoothDevice.ACTION_BOND_STATE_CHANGED ->{
                Toast.makeText(context?.applicationContext,"Bond state change",Toast.LENGTH_LONG).show()
            }
        }
    }
}