package com.example.applenguajes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class listado extends AppCompatActivity {

    private final String[] cabecera = {"Id", "Nombre", "Cedula", "Telefono", "Correo", "ciudad"};
    private final ArrayList<String[]> datos = new ArrayList<>();
    private FeedReaderHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        dbHelper = new FeedReaderHelper(this);
        TableLayout tblistado = findViewById(R.id.tblistado);
        TextView txtvacio = findViewById(R.id.txtvacio);
        TraerDatos();
        if(datos.isEmpty()) {
            txtvacio.setVisibility(View.VISIBLE);
        } else {
            DynamicTable creaTabla = new DynamicTable(tblistado, this);
            creaTabla.setCabecera(cabecera);
            creaTabla.setDatos(datos);
            creaTabla.crearCabecera();
            creaTabla.crearFilas();
        }
    }
    protected void onDestroy(){
        dbHelper.close();
        super.onDestroy();
    }

    private void TraerDatos(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.column1,
                FeedReaderContract.FeedEntry.column2,
                FeedReaderContract.FeedEntry.column3,
                FeedReaderContract.FeedEntry.column4,
                FeedReaderContract.FeedEntry.column5
        };
        String sortOrder = FeedReaderContract.FeedEntry.column1 + " ASC";
        try(Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.nameTable, //Tabla
                projection,
                null,
                null,
                null,
                null,
                sortOrder)) {
            while (cursor.moveToNext()) {
                String[] fila = new String[6];
                fila[0] = String.valueOf(cursor.getLong(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID)));
                fila[1] = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.column1));
                fila[2] = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.column2));
                fila[3] = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.column3));
                fila[4] = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.column4));
                fila[5] = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.column5));
                datos.add(fila);
            }
        }
    }
    public void Regresar(View view){finish();}
}