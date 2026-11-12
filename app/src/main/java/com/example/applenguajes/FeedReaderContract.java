package com.example.applenguajes;

import android.provider.BaseColumns;

public class FeedReaderContract {

    private FeedReaderContract() {
    }

    public static class FeedEntry implements BaseColumns {

        public static final String nameTable = "personas";

        public static final String column1 = "nombre";
        public static final String column2 = "cedula";
        public static final String column3 = "telefono";
        public static final String column4 = "correo";
        public static final String column5 = "ciudad";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " +
                    FeedEntry.nameTable + " (" +

                    FeedEntry._ID +
                    " INTEGER PRIMARY KEY," +

                    FeedEntry.column1 +
                    " TEXT," +

                    FeedEntry.column2 +
                    " TEXT," +

                    FeedEntry.column3 +
                    " TEXT," +

                    FeedEntry.column4 +
                    " TEXT," +

                    FeedEntry.column5 +
                    " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " +
                    FeedEntry.nameTable;
}