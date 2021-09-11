package com.abhishek.servicepractice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var myService: MyService? = null
    private var isServiceBound: Boolean = false

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e(TAG, "onServiceConnected: ")
            val myBinder: MyService.MyBinder = service as MyService.MyBinder
            myService = myBinder.getService()
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e(TAG, "onServiceDisconnected: ")
            isServiceBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, MyService::class.java)
        startService.setOnClickListener {
            startService(intent)
        }
        bindService.setOnClickListener {
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        unbindService.setOnClickListener {
            unBindService()
        }

        stopService.setOnClickListener {
            stopService(intent)
        }

        getRandomNumber.setOnClickListener {
            getRandomNumberFromService()
        }
    }

    private fun unBindService() {
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
        }
    }

    private fun getRandomNumberFromService() {
        if (isServiceBound)
            Log.e(TAG, "RandomNumber:-${myService?.getRandomNumber()}")
    }
}