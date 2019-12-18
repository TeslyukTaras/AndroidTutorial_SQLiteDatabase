package com.teslyuk.androidtutorial_sqlitedatabase.util

import android.util.Log

/**
 * Created by taras on 25.11.15.
 */
object Logger {

    fun i(TAG: String, message: String) {
        Log.i(TAG, message)
    }

    fun e(TAG: String, message: String) {
        Log.e(TAG, message)
    }

    fun d(TAG: String, message: String) {
        Log.d(TAG, message)
    }
}
