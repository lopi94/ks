package com.app.ks.myapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class BaseDao<T extends IDatabaseObject> {

    protected final String mTableName;

    protected BaseDao(String tableName) {
        mTableName = tableName;
    }

    protected Cursor openRawQueryCursor(String sql) {
     //   SQLiteDatabase database = ...getDbHelper().getReadableDatabase();
        SQLiteDatabase database = MyAppApplication.getsInstance().getDbHelper().getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor;
    }

    public long insertObject(T object) {
      //  SQLiteDatabase database = ...getDbHelper().getWritableDatabase();
        SQLiteDatabase database = MyAppApplication.getsInstance().getDbHelper().getReadableDatabase();
        ContentValues values = getObjectContentValues(object);

        long objectId = database.insert(mTableName, null, values);
        object.setId(objectId);

        return objectId;
    }

    public boolean updateObject(T object) {
//        SQLiteDatabase database = ...getDbHelper().getWritableDatabase();
        SQLiteDatabase database = MyAppApplication.getsInstance().getDbHelper().getReadableDatabase();
        ContentValues values = getObjectContentValues(object);
        String whereClause = String.format(Locale.getDefault(), "id = %d", object.getId());
        return database.update(mTableName, values, whereClause, null) > 0;
    }

    public boolean deleteObject(T object) {
//        SQLiteDatabase database = ...getDbHelper().getWritableDatabase();
        SQLiteDatabase database = MyAppApplication.getsInstance().getDbHelper().getReadableDatabase();
        String whereClause = String.format(Locale.getDefault(), "id = %d", object.getId());
        return database.delete(mTableName, whereClause, null) > 0;
    }

    protected int getColumnIndex(Cursor cursor, String colName) {
        int colIndex = cursor.getColumnIndex(colName);
        if (colIndex < 0) {
            colIndex = cursor.getColumnIndex(colName.toLowerCase(Locale.getDefault()));
            if (colIndex < 0) {
                colIndex = cursor.getColumnIndex(colName.toUpperCase(Locale.getDefault()));
            }
        }
        return colIndex;
    }

    protected int getInt(Cursor cursor, String colName) {
        int colIndex = getColumnIndex(cursor, colName);
        if (colIndex >= 0) {
            return cursor.getInt(colIndex);
        }
        return 0;
    }

    protected int getInt(Cursor cursor, String colName, int defValue) {
        if (cursorIsEmpty(cursor)) {
            return defValue;
        }

        int colIndex = getColumnIndex(cursor, colName);
        if (colIndex >= 0 && !cursor.isNull(colIndex)) {
            return cursor.getInt(colIndex);
        }
        return defValue;
    }

    protected long getLong(Cursor cursor, String colName) {
        int colIndex = getColumnIndex(cursor, colName);
        if (colIndex >= 0) {
            return cursor.getLong(colIndex);
        }
        return 0;
    }

    protected long getLong(Cursor cursor, String colName, long defValue) {
        if (cursorIsEmpty(cursor)) {
            return defValue;
        }

        int colIndex = getColumnIndex(cursor, colName);
        if (colIndex >= 0 && !cursor.isNull(colIndex)) {
            return cursor.getLong(colIndex);
        }
        return defValue;
    }

    protected String getString(Cursor cursor, String colName) {
        int colIndex = getColumnIndex(cursor, colName);
        if (colIndex >= 0) {
            return cursor.getString(colIndex);
        }
        return "";
    }

    protected String getString(Cursor cursor, String colName, String defValue) {
        if (cursorIsEmpty(cursor)) {
            return defValue;
        }

        int colIndex = getColumnIndex(cursor, colName);
        if (colIndex >= 0 && !cursor.isNull(colIndex)) {
            return cursor.getString(colIndex);
        }
        return defValue;
    }

    protected double getDouble(Cursor cursor, String colName) {
        int colIndex = getColumnIndex(cursor, colName);
        if (colIndex >= 0) {
            return cursor.getDouble(colIndex);
        }
        return 0d;
    }

    protected double getDouble(Cursor cursor, String colName, double defValue) {
        if (cursorIsEmpty(cursor)) {
            return defValue;
        }

        int colIndex = getColumnIndex(cursor, colName);
        if (colIndex >= 0 && !cursor.isNull(colIndex)) {
            return cursor.getDouble(colIndex);
        }
        return defValue;
    }

    protected boolean getBoolean(Cursor cursor, String colName) {
        int colIndex = getColumnIndex(cursor, colName);
        if (colIndex >= 0) {
            return cursor.getInt(colIndex) > 0;
        }
        return false;
    }

    protected boolean getBoolean(Cursor cursor, String colName, boolean defValue) {
        if (cursorIsEmpty(cursor)) {
            return defValue;
        }

        int colIndex = getColumnIndex(cursor, colName);
        if (colIndex >= 0 && !cursor.isNull(colIndex)) {
            return cursor.getInt(colIndex) > 0;
        }
        return defValue;
    }

    private boolean cursorIsEmpty(Cursor cursor) {
        if (cursor == null || (cursor.isBeforeFirst() && cursor.isAfterLast())) {
            return true;
        }

        return false;
    }

    private boolean isNull(Cursor cursor, String colName, boolean defValue) {
        if (cursorIsEmpty(cursor)) {
            return defValue;
        }

        int colIndex = getColumnIndex(cursor, colName);
        if (colIndex >= 0) {
            return cursor.isNull(colIndex);
        }
        return defValue;
    }

    protected void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    protected String parseDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateString;
        try {
            dateString = simpleDateFormat.format(date);
        } catch (Exception e) {
            dateString = "";
        }
        return dateString;
    }

    protected Date parseDateString(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }

    public List<T> getObjectList() {
        String sql =  MyAppApplication.getsInstance().getDbHelper().getSql(R.string.sql_select_all_clients);
        Cursor cursor = openRawQueryCursor(sql);
        try {
            List<T> objectList = new ArrayList<>();

            while(cursor.moveToNext()) {
                T object = getObjectFromCursor(cursor);
                objectList.add(object);
            }
            return objectList;

        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            closeCursor(cursor);
        }
    }

    protected abstract T getObjectFromCursor(Cursor cursor);

    protected abstract ContentValues getObjectContentValues(T object);

    /**
     * Created by KS on 2016-04-16.
     */
    public static class ClientDao extends BaseDao<Client> {

        private static final String TABLE_NAME = "Client";
        private static final String COLUMN_ID = "id";
        private static final String COLUMN_NAME = "name";
        private static final String COLUMN_ADDRESS = "address";
        private static final String COLUMN_EMAIL = "email";
        private static final String COLUMN_PHONE = "phone";

        public ClientDao() {
            super(TABLE_NAME);
        }

        @Override
        protected Client getObjectFromCursor(Cursor cursor) {
            Client client = new Client();
            client.setId(getLong(cursor,COLUMN_ID));
            client.setName(getString(cursor,COLUMN_NAME));
            client.setAddress(getString(cursor, COLUMN_ADDRESS));
            client.setEmail(getString(cursor, COLUMN_EMAIL));
            client.setPhone(getString(cursor, COLUMN_PHONE));
            return client;
        }

        @Override
        protected ContentValues getObjectContentValues(Client object) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, object.getName());
            contentValues.put(COLUMN_ADDRESS, object.getAddress());
            contentValues.put(COLUMN_EMAIL, object.getEmail());
            contentValues.put(COLUMN_PHONE, object.getPhone());
            return contentValues;
        }

    }
}