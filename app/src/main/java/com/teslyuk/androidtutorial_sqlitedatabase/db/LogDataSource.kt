package com.teslyuk.androidtutorial_sqlitedatabase.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

import com.teslyuk.androidtutorial_sqlitedatabase.model.LogModel

/**
 * Created by taras.teslyuk on 11/26/15.
 */
class LogDataSource(context: Context) : LogDataInterface {
    // Database fields
    private var database: SQLiteDatabase? = null
    private val dbHelper: LogSQLiteOpenHelper = LogSQLiteOpenHelper(context)
    private val allColumns = arrayOf(LogSQLiteOpenHelper.TB_ID, LogSQLiteOpenHelper.TB_MESSAGE)

    override fun getAll(): List<LogModel>
        {
            val logs = mutableListOf<LogModel>()

            val cursor = database!!.query(LogSQLiteOpenHelper.TABLE_LOG,
                    allColumns, null, null, null, null, null)

            if (cursor != null && cursor.count != 0) {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    val log = cursorToLog(cursor)
                    logs.add(log)
                    cursor.moveToNext()
                }
            }
            cursor!!.close()
            return logs
        }

    @Throws(SQLException::class)
    override fun open() {
        database = dbHelper.writableDatabase
    }

    override fun close() {
        dbHelper.close()
        database = null
    }

    override fun addLog(model: LogModel) {
        val values = ContentValues()
        values.put(LogSQLiteOpenHelper.TB_MESSAGE, model.message)
        values.put(LogSQLiteOpenHelper.TB_DATETIME, model.datetime)
        val insertId = database!!.insert(LogSQLiteOpenHelper.TABLE_LOG, null,
                values)
    }

    override fun removeLog(log: LogModel) {
        val id = log.id
        println("Log deleted with id: $id")
        database!!.delete(LogSQLiteOpenHelper.TABLE_LOG,
                LogSQLiteOpenHelper.TB_ID + " = " + id, null)
    }

    private fun cursorToLog(cursor: Cursor): LogModel {
        val id = cursor.getInt(cursor.getColumnIndex(LogSQLiteOpenHelper.TB_ID))
        val message = cursor.getString(cursor.getColumnIndex(LogSQLiteOpenHelper.TB_MESSAGE))

        val item = LogModel()
        item.id = id
        item.message = message
        return item
    }
}
