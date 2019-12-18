package com.teslyuk.androidtutorial_sqlitedatabase.db

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor

import com.teslyuk.androidtutorial_sqlitedatabase.model.LogModel

import java.util.ArrayList

class LogContentProviderWrapper(activity: Activity) : LogDataInterface {

    private val contentResolver: ContentResolver = activity.contentResolver

    override fun getAll(): List<LogModel> {
        val projection = arrayOf(LogSQLiteOpenHelper.TB_ID, LogSQLiteOpenHelper.TB_MESSAGE, LogSQLiteOpenHelper.TB_EXCEPTION, LogSQLiteOpenHelper.TB_DATETIME)
        val data = contentResolver.query(LogSQLiteOpenHelper.LOG_URI, projection, null, null, null)
        var logItems: MutableList<LogModel> = mutableListOf()

        if (data != null && data.count != 0) {
            if (data.moveToFirst()) {
                do {
                    val id = data.getInt(data.getColumnIndex(LogSQLiteOpenHelper.TB_ID))
                    val message = data.getString(data.getColumnIndex(LogSQLiteOpenHelper.TB_MESSAGE))
                    val exception = data.getString(data.getColumnIndex(LogSQLiteOpenHelper.TB_EXCEPTION))
                    val datetime = data.getString(data.getColumnIndex(LogSQLiteOpenHelper.TB_DATETIME))

                    val item = LogModel()
                    item.id = id
                    item.message = message
                    item.exception = exception
                    item.datetime = datetime

                    logItems.add(item)
                } while (data.moveToNext())
            }
        }
        data!!.close()
        return logItems
    }

    override fun addLog(errorLog: LogModel) {
        val values = ContentValues()
        values.put(LogSQLiteOpenHelper.TB_MESSAGE, errorLog.message)
        values.put(LogSQLiteOpenHelper.TB_EXCEPTION, errorLog.exception)
        values.put(LogSQLiteOpenHelper.TB_DATETIME, errorLog.datetime)

        contentResolver.insert(LogSQLiteOpenHelper.LOG_URI, values)
    }

    override fun removeLog(model: LogModel) {
        removeLogById(model.id)
    }

    override fun open() {
        
    }

    override fun close() {

    }

    private fun removeLogById(id: Int) {
        val selection = LogSQLiteOpenHelper.TB_ID + " == " + id
        contentResolver.delete(LogSQLiteOpenHelper.LOG_URI, selection, null)
    }
}
