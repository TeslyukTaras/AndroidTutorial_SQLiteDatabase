package com.teslyuk.androidtutorial_sqlitedatabase.db;

import com.teslyuk.androidtutorial_sqlitedatabase.model.LogModel;

import java.util.List;

public interface LogDataInterface {
    List<LogModel> getAll();

    void addLog(LogModel model);

    void removeLog(LogModel model);

    void open();

    void close();
}
