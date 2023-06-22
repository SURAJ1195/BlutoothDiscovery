package com.example.bluetoothdiscovery

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

class Reciever(context: Context, val list: MutableList<BluetoothDevice>,private val onDevice : (BluetoothDevice) -> Unit): BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
      val action=intent?.action

        if(action==BluetoothDevice.ACTION_FOUND){

            val device: BluetoothDevice? =intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            if(device!=null){
                Log.d("suraj", "onReceive:${device} ")
            list.add(device)
                device.let {

                    Log.d("suraj1","1")
                    onDevice.invoke(it)
                }
                Log.d("suraj", "onReceive:${list} ")



            }
        }
      //  Log.d("suraj","$listt")
    }



    override fun peekService(myContext: Context?, service: Intent?): IBinder {
        return super.peekService(myContext, service)
    }
}