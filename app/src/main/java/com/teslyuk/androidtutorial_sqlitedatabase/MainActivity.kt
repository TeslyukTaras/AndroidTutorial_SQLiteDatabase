package com.teslyuk.androidtutorial_sqlitedatabase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

import com.teslyuk.androidtutorial_sqlitedatabase.adapter.LogListAdapter
import com.teslyuk.androidtutorial_sqlitedatabase.db.LogContentProviderWrapper
import com.teslyuk.androidtutorial_sqlitedatabase.db.LogDataInterface
import com.teslyuk.androidtutorial_sqlitedatabase.db.LogDataSource
import com.teslyuk.androidtutorial_sqlitedatabase.model.LogModel
import com.teslyuk.androidtutorial_sqlitedatabase.util.Logger

import java.util.Calendar

class MainActivity : AppCompatActivity(),
        LogListAdapter.LogListAdapterEventListener {

    private lateinit var fileListView: ListView
    private lateinit var textView: EditText
    private lateinit var addBtn: Button

    private lateinit var adapter: LogListAdapter

    private lateinit var dataSource: LogDataInterface
    private val useContentProvider = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        if (useContentProvider) {
            dataSource = LogContentProviderWrapper(this)
        } else {
            dataSource = LogDataSource(this)
        }
    }

    private fun initView() {
        fileListView = findViewById(R.id.internal_file_list_lv)
        textView = findViewById(R.id.internal_file_text_et)
        addBtn = findViewById(R.id.internal_add_file_btn)

        addBtn.setOnClickListener{
            createLog()
            updateLogList()
        }

        adapter = LogListAdapter(this)
        adapter.addListener(this)
        fileListView.adapter = adapter
    }

    override fun onResume() {
        dataSource.open()
        super.onResume()
        updateLogList()
    }

    override fun onPause() {
        dataSource.close()
        super.onPause()
    }

    private fun createLog() {
        Logger.d(TAG, "createLog")
        val logName = Calendar.getInstance().timeInMillis.toString() + " millis"
        val text = textView.text.toString()

        val model = LogModel(text, logName)
        dataSource.addLog(model)
    }

    private fun updateLogList() {
        Logger.d(TAG, "updateLogList")
        adapter.onDataUpdate(dataSource.getAll())
    }

    override fun removeLog(position: Int) {
        if (dataSource.getAll().size > position) {
            dataSource.removeLog(dataSource.getAll()[position])
        }
        updateLogList()
    }

    companion object {

        private val TAG = "DatabaseStorage"
    }

}