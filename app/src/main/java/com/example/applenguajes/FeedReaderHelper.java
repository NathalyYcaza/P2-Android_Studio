package com.example.applenguajes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME =
            "personas.db";

    public FeedReaderHelper(Context context) {

        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                FeedReaderContract.SQL_CREATE_ENTRIES
        );
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {

        db.execSQL(
                FeedReaderContract.SQL_DELETE_ENTRIES
        );

        onCreate(db);
    }

    @Override
    public void onDowngrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {

        onUpgrade(
                db,
                oldVersion,
                newVersion
        );
    }
}