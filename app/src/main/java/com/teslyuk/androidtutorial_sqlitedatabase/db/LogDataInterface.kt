package com.teslyuk.androidtutorial_sqlitedatabase.db

import com.teslyuk.androidtutorial_sqlitedatabase.model.LogModel

interface LogDataInterface {
    fun getAll(): List<LogModel>

    fun addLog(model: LogModel)

    fun removeLog(model: LogModel)

    fun open()

    fun close()
}
