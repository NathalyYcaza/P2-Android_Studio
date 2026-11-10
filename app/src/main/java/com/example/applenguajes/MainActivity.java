package com.example.applenguajes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText txtid, txtNombre, txtCedula, txtTelefono, txtCorreo, txtCiudad;

    private FeedReaderHelper dbHelper;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //txtid = findViewById(R.id.txtid);
        txtNombre = findViewById(R.id.etNombre);
        txtCedula = findViewById(R.id.etCedula);
        txtTelefono = findViewById(R.id.etTelefono);
        txtCorreo = findViewById(R.id.etCorreo);
        txtCiudad = findViewById(R.id.etCiudad);
        btnGuardar = findViewById(R.id.btnGuardar);

        dbHelper = new FeedReaderHelper(this);

        btnGuardar.setOnClickListener(v -> {

            String nombre = txtNombre.getText().toString();
            String cedula = txtCedula.getText().toString();
            String telefono = txtTelefono.getText().toString();
            String correo = txtCorreo.getText().toString();
            String ciudad = txtCiudad.getText().toString();

            boolean guardado = dbHelper.insertarPersona(
                    nombre,
                    cedula,
                    telefono,
                    correo,
                    ciudad
            );

            if (guardado) {
                Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void onDestroy(){
        dbHelper.close();
        super.onDestroy();
    }
    public void Listar(View vista) {
        startActivity(new Intent(this, listado.class));
    }
    public void Guardar(View vista) {
        String nombre = txtNombre.getText().toString().trim();
        String cedula = txtCedula.getText().toString().trim();
        String telefono = txtTelefono.getText().toString().trim();
        String correo = txtCorreo.getText().toString().trim();
        String ciudad = txtCiudad.getText().toString().trim();

        if(nombre.isEmpty()){
            mostrarMensaje("Ingrese nombre y apellido");
            return;
        }
        if(cedula.isEmpty()){
            mostrarMensaje("Ingrese su numero de cedula");
            return;
        }
        if(telefono.isEmpty()){
            mostrarMensaje("Ingrese su numero de telefono");
            return;
        }
        if(ciudad.isEmpty()){
            mostrarMensaje("Ingrese la ciudad en donde vive");
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.column1, nombre);
        values.put(FeedReaderContract.FeedEntry.column2, cedula);
        values.put(FeedReaderContract.FeedEntry.column1, telefono);
        values.put(FeedReaderContract.FeedEntry.column1, correo);
        values.put(FeedReaderContract.FeedEntry.column1, ciudad);
        long newRowID = db.insert(FeedReaderContract.FeedEntry.nameTable, null, values);
        if (newRowID == -1){
            mostrarMensaje("No se pudo guardar el registro");
        } else {
            txtid.setText(String.valueOf(newRowID));
            mostrarMensaje("Se guardo el registro con clave: " + newRowID);
        }
    }

    public void Buscar(View vista) {
        String id = leerId();
        if ( id == null) return;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.column1,
                FeedReaderContract.FeedEntry.column2,
                FeedReaderContract.FeedEntry.column3,
                FeedReaderContract.FeedEntry.column4,
                FeedReaderContract.FeedEntry.column5
        };
        String selection = FeedReaderContract.FeedEntry._ID + " = ? ";
        String[] selectionArgs = {id};
        try ( Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.nameTable,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                txtNombre.setText(cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.column1)));
                txtCedula.setText(cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.column1)));
                txtTelefono.setText(cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.column1)));
                txtCorreo.setText(cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.column1)));
                txtCiudad.setText(cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.column1)));
            } else {
                txtNombre.setText("");
                txtCiudad.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                txtCiudad.setText("");
                mostrarMensaje("No existe un registro Id " + id);
            }
        }
    }

    public void Actualizar(View vista) {
        String id = leerId();
        if(id == null) return;
        String nombre = txtNombre.getText().toString().trim();
        String cedula = txtCedula.getText().toString().trim();
        String telefono = txtTelefono.getText().toString().trim();
        String correo = txtCorreo.getText().toString().trim();
        String ciudad = txtCiudad.getText().toString().trim();
        if (nombre.isEmpty()) {
            mostrarMensaje("Ingrese nombre y apellido");
            return;
        }
        if(cedula.isEmpty()){
            mostrarMensaje("Ingrese su numero de cedula");
            return;
        }
        if(telefono.isEmpty()){
            mostrarMensaje("Ingrese su numero de telefono");
            return;
        }
        if(ciudad.isEmpty()){
            mostrarMensaje("Ingrese la ciudad en donde vive");
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.column1, nombre);
        values.put(FeedReaderContract.FeedEntry.column2, cedula);
        values.put(FeedReaderContract.FeedEntry.column1, telefono);
        values.put(FeedReaderContract.FeedEntry.column1, correo);
        values.put(FeedReaderContract.FeedEntry.column1, ciudad);
        String selection = FeedReaderContract.FeedEntry._ID + " = ?";
        String[] selectionArgs = {id};
        int count = db.update(FeedReaderContract.FeedEntry.nameTable, values, selection, selectionArgs);
        mostrarMensaje("Se actualizo " + count + " registro(s)");
    }
}