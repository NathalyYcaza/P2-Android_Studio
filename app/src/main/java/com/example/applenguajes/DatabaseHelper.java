package com.example.applenguajes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "personas.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE personas (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT, " +
                        "cedula TEXT, " +
                        "telefono TEXT, " +
                        "correo TEXT, " +
                        "ciudad TEXT)"
        );
    }
    public boolean insertarPersona(String nombre, String cedula, String telefono,
                                   String correo, String ciudad) {

        SQLiteDatabase db = this.getWritableDatabase();

        android.content.ContentValues valores = new android.content.ContentValues();

        valores.put("nombre", nombre);
        valores.put("cedula", cedula);
        valores.put("telefono", telefono);
        valores.put("correo", correo);
        valores.put("ciudad", ciudad);

        long resultado = db.insert("personas", null, valores);

        return resultado != -1;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS personas");
        onCreate(db);
    }
}