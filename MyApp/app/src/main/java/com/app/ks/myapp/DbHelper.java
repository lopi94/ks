package com.app.ks.myapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.ks.myapp.R;

/**
 * Created by KS on 2016-04-16.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String NAME = "firstapp.db";
    private static final int VERSION = 1;

    private Context context;

    public DbHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
    }

    private String getSql(int sqlId) {
        return context.getString(sqlId);
    }

    public String getSql(int sqlId, Object... args) {
        String sqlFormat = getSql(sqlId);
        return  String.format(sqlFormat,args);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getSql(R.string.sql_table_test));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
