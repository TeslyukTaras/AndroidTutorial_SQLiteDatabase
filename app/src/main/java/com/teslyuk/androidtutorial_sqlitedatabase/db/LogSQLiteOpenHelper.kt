package com.teslyuk.androidtutorial_sqlitedatabase.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

import com.teslyuk.androidtutorial_sqlitedatabase.util.Logger

class LogSQLiteOpenHelper : SQLiteOpenHelper {

    constructor(context: Context, name: String, factory: CursorFactory, version: Int) : super(context, DB_NAME, null, DB_VERSION) {}

    constructor(context: Context) : super(context, DB_NAME, null, DB_VERSION) {}

    override fun onCreate(db: SQLiteDatabase) {
        Logger.i(TAG, "onCreate")
        db.execSQL(CREATE_TABLE_LOG)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Logger.i(TAG, "onUpgrade")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LOG")

        onCreate(db)
    }

    companion object {

        private val DB_NAME = "log.db"

        private val DB_VERSION = 1

        val TAG = "LogSQLiteOpenHelper"

        val AUTHORITY = "com.teslyuk.android.androidtutorial.db.log"
        val BASE_PATH = "log_database"

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //- - - - - - - TABLE NAMES - - - - - - - - - - - - - - - - -
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        val TABLE_LOG = "LOG"

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //- - - - - - - TABLE URI - - - - - - - - - - - - - - - - -
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        val LOG_URI = Uri.parse("content://$AUTHORITY/$TABLE_LOG")

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //- - - - - - - TABLE IDS - - - - - - - - - - - - - - - - -
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        val TABLE_LOG_ID = 1

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //- - - - - - - TABLE FIELDS- - - - - - - - - - - - - - - -
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        val TB_ID = "ID"

        //TABLE_LOG
        val TB_MESSAGE = "MESSAGE"
        val TB_EXCEPTION = "EXCEPTION"
        val TB_DATETIME = "DATETIME"

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //- - - - - - - CREATE TABLE- - - - - - - - - - - - - - - -
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        private val CREATE_TABLE_LOG = "CREATE TABLE " + TABLE_LOG + "\n" +
                "                 ( " + TB_ID + " integer primary key\n" +
                "                 , " + TB_MESSAGE + " varchar not NULL\n" +
                "                 , " + TB_EXCEPTION + " varchar NULL\n" +
                "                 , " + TB_DATETIME + " varchar not NULL)"
    }
}