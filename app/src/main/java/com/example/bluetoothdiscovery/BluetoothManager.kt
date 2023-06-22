package com.example.bluetoothdiscovery

import android.bluetooth.BluetoothManager
import android.content.Context

class BluetoothManager(context:Context) {
    val manager=context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val adapter=manager.adapter

    fun checkBluetoothEnabled(): Boolean {


        if(adapter==null){
            //device is not compatible with bluetooth
            return false

        }
        if(!adapter.isEnabled){
            //request for enabling bluetooth
            return false
        }

        return true


    }


}