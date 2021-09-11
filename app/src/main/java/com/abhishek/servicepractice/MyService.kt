package com.abhishek.servicepractice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.lang.Exception
import kotlin.random.Random

class MyService : Service() {
    private var randomNumber: Int = 0
    private var isRandomNumberGeneratorOn: Boolean = false
    private val MIN: Int = 0
    private val MAX: Int = 100
    private var iBinder: IBinder = MyBinder()

    companion object {
        private const val TAG = "MyService"
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e(TAG, "In onBind: ")
        return iBinder
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.e(TAG, "Service Started")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRandomNumberGeneratorOn = true
        Thread(Runnable {
            startRandomNumberGeneration()
        }).start()
        return START_NOT_STICKY
    }

    private fun startRandomNumberGeneration() {
        while (isRandomNumberGeneratorOn) {
            try {
                Thread.sleep(1000)
                if (isRandomNumberGeneratorOn) {
                    randomNumber = (MIN..MAX).random()
                    Log.e(TAG, "Random_Number: $randomNumber")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun stopRandomNumberGeneration() {
        isRandomNumberGeneratorOn = false
    }

    fun getRandomNumber(): Int {
        return randomNumber
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRandomNumberGeneration()
        Log.e(TAG, "Service Destroyed")
    }

    inner class MyBinder : Binder() {
        fun getService(): MyService {
            return this@MyService
        }
    }
}