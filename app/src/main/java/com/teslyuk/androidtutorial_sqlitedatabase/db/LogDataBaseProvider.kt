package com.teslyuk.androidtutorial_sqlitedatabase.db

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

import com.teslyuk.androidtutorial_sqlitedatabase.util.Logger

class LogDataBaseProvider : ContentProvider() {

    lateinit var _dbHelper: LogSQLiteOpenHelper

    override fun getType(url: Uri): String? {
        var tb: String? = null
        val match = sURIMatcher.match(url)
        when (match) {
            LogSQLiteOpenHelper.TABLE_LOG_ID -> tb = LogSQLiteOpenHelper.TABLE_LOG
        }
        return tb
    }

    override fun onCreate(): Boolean {
        _dbHelper = LogSQLiteOpenHelper(context)
        return false
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        Logger.i("Provider", "delete")
        val _db = _dbHelper.writableDatabase
        val rowsDeleted = _db.delete(getType(uri), selection, null)
        context!!.contentResolver.notifyChange(uri, null)
        return rowsDeleted
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Logger.i("Provider", "insert")
        var id: Long = 0
        val _db = _dbHelper.writableDatabase
        id = _db.insert(getType(uri), null, values)
        context!!.contentResolver.notifyChange(uri, null)
        return Uri.parse(LogSQLiteOpenHelper.BASE_PATH + "/" + id)
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        Logger.i("Provider", "query")
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = getType(uri)
        val _db = _dbHelper.readableDatabase
        val cursor = queryBuilder.query(_db, projection, selection, selectionArgs, null, null, sortOrder)
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        Logger.i("Provider", "update")
        var rowsUpdated = 0
        val _db = _dbHelper.writableDatabase
        rowsUpdated = _db.update(getType(uri), values, selection, selectionArgs)
        context!!.contentResolver.notifyChange(uri, null)
        return rowsUpdated
    }

    companion object {

        private val sURIMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sURIMatcher.addURI(LogSQLiteOpenHelper.AUTHORITY, LogSQLiteOpenHelper.TABLE_LOG, LogSQLiteOpenHelper.TABLE_LOG_ID)
        }
    }
}
