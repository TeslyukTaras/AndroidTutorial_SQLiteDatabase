package com.teslyuk.androidtutorial_sqlitedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.teslyuk.androidtutorial_sqlitedatabase.adapter.LogListAdapter;
import com.teslyuk.androidtutorial_sqlitedatabase.db.LogContentProviderWrapper;
import com.teslyuk.androidtutorial_sqlitedatabase.db.LogDataInterface;
import com.teslyuk.androidtutorial_sqlitedatabase.db.LogDataSource;
import com.teslyuk.androidtutorial_sqlitedatabase.model.LogModel;
import com.teslyuk.androidtutorial_sqlitedatabase.util.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        LogListAdapter.LogListAdapterEventListener {

    private static final String TAG = "DatabaseStorage";

    private ListView fileListView;
    private EditText textView;
    private Button addBtn;

    private List<LogModel> logs;
    private LogListAdapter adapter;

    private LogDataInterface dataSource;
    private boolean useContentProvider = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (useContentProvider) {
            dataSource = new LogContentProviderWrapper(this);
        } else {
            dataSource = new LogDataSource(this);
        }
    }

    private void initView() {
        fileListView = findViewById(R.id.internal_file_list_lv);
        textView = findViewById(R.id.internal_file_text_et);
        addBtn = findViewById(R.id.internal_add_file_btn);

        addBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
        updateLogList();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    private void createLog() {
        Logger.d(TAG, "createLog");
        String logName = Calendar.getInstance().getTimeInMillis() + " millis";
        String text = textView.getText().toString();

        LogModel model = new LogModel(text, logName);
        dataSource.addLog(model);
    }

    private void updateLogList() {
        Logger.d(TAG, "updateLogList");
        logs = dataSource.getAll();

        if (logs == null) {
            logs = new ArrayList<>();
        }

        for (int i = 0; i < logs.size(); i++) {
            Logger.d(TAG, "i:" + i + " name: " + logs.get(i).getMessage()
                    + " datetime: " + logs.get(i).getDatetime());
        }

        if (adapter == null) {
            adapter = new LogListAdapter(this, logs);
            adapter.addListener(this);
            fileListView.setAdapter(adapter);
        } else {
            adapter.onDataUpdate(logs);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.internal_add_file_btn:
                createLog();
                updateLogList();
                break;
        }
    }

    @Override
    public void removeLog(int position) {
        if (logs != null && logs.get(position) != null) {
            dataSource.removeLog(logs.get(position));
        }
        updateLogList();
    }

}