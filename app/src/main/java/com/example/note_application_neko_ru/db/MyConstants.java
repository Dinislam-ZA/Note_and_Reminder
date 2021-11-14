package com.example.note_application_neko_ru.db;

public class MyConstants {
    public static final String EDIT_STATE = "edit_state";
    public static final String LIST_ITEM_INTENT = "list_item_intent";
    public static final String TABLE_NAME = "entry";
    public static final String _ID = "ID";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public static final String COLUMN_NAME_URI = "uri";
    public static final String DB_NAME = "MyDB.db";
    public static final int DB_VERSION = 2;


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_DESCRIPTION + " TEXT," + COLUMN_NAME_URI + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
