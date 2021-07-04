package com.nirwal.messmanager.database;

import com.google.protobuf.UninitializedMessageException;
import com.nirwal.messmanager.MyApp;

public class Database {
    // under implementation

    private MyApp _app;
    public Database(MyApp app){
        this._app = app;

    }

    public void get_Groups(){
        Object groups = this._app.getFirebaseFireStoreDB().collection("Groups");
        // return groups;
    }

}
