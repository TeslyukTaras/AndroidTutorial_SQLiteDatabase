package com.teslyuk.androidtutorial_sqlitedatabase.model

import java.util.Calendar

/**
 * Created by taras on 25.11.15.
 */
class LogModel {
    var id: Int = 0
    lateinit var message: String
    var exception: String? = null
    var datetime: String

    constructor() {
        datetime = Calendar.getInstance().timeInMillis.toString()
    }

    constructor(message: String, exception: String) {
        this.message = message
        this.exception = exception
        datetime = Calendar.getInstance().timeInMillis.toString()
    }
}
