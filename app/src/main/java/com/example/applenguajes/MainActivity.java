package com.example.applenguajes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText txtid;
    private EditText txtNombre;
    private EditText txtCedula;
    private EditText txtTelefono;
    private EditText txtCorreo;
    private EditText txtCiudad;

    private FeedReaderHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtid = findViewById(R.id.txtid);
        txtNombre = findViewById(R.id.etNombre);
        txtCedula = findViewById(R.id.etCedula);
        txtTelefono = findViewById(R.id.etTelefono);
        txtCorreo = findViewById(R.id.etCorreo);
        txtCiudad = findViewById(R.id.etCiudad);

        dbHelper = new FeedReaderHelper(this);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public void Guardar(View vista) {

        String nombre = txtNombre.getText().toString().trim();
        String cedula = txtCedula.getText().toString().trim();
        String telefono = txtTelefono.getText().toString().trim();
        String correo = txtCorreo.getText().toString().trim();
        String ciudad = txtCiudad.getText().toString().trim();

        if (nombre.isEmpty()) {
            mostrarMensaje("Ingrese nombre y apellido");
            return;
        }

        if (cedula.isEmpty()) {
            mostrarMensaje("Ingrese su número de cédula");
            return;
        }

        if (telefono.isEmpty()) {
            mostrarMensaje("Ingrese su número de teléfono");
            return;
        }

        if (correo.isEmpty()) {
            mostrarMensaje("Ingrese su correo electrónico");
            return;
        }

        if (ciudad.isEmpty()) {
            mostrarMensaje("Ingrese la ciudad en donde vive");
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FeedReaderContract.FeedEntry.column1, nombre);
        values.put(FeedReaderContract.FeedEntry.column2, cedula);
        values.put(FeedReaderContract.FeedEntry.column3, telefono);
        values.put(FeedReaderContract.FeedEntry.column4, correo);
        values.put(FeedReaderContract.FeedEntry.column5, ciudad);

        long newRowId = db.insert(
                FeedReaderContract.FeedEntry.nameTable,
                null,
                values
        );

        if (newRowId == -1) {
            mostrarMensaje("No se pudo guardar el registro");
        } else {
            txtid.setText(String.valueOf(newRowId));

            mostrarMensaje(
                    "Se guardó el registro con clave: " + newRowId
            );
        }
    }

    public void Buscar(View vista) {

        String id = leerId();

        if (id == null) {
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.column1,
                FeedReaderContract.FeedEntry.column2,
                FeedReaderContract.FeedEntry.column3,
                FeedReaderContract.FeedEntry.column4,
                FeedReaderContract.FeedEntry.column5
        };

        String selection =
                FeedReaderContract.FeedEntry._ID + " = ?";

        String[] selectionArgs = {id};

        try (Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.nameTable,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {

            if (cursor.moveToFirst()) {

                txtNombre.setText(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        FeedReaderContract.FeedEntry.column1
                                )
                        )
                );

                txtCedula.setText(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        FeedReaderContract.FeedEntry.column2
                                )
                        )
                );

                txtTelefono.setText(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        FeedReaderContract.FeedEntry.column3
                                )
                        )
                );

                txtCorreo.setText(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        FeedReaderContract.FeedEntry.column4
                                )
                        )
                );

                txtCiudad.setText(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        FeedReaderContract.FeedEntry.column5
                                )
                        )
                );

                mostrarMensaje("Registro encontrado");

            } else {

                txtNombre.setText("");
                txtCedula.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                txtCiudad.setText("");

                mostrarMensaje(
                        "No existe un registro con Id " + id
                );
            }
        }
    }

    public void Actualizar(View vista) {

        String id = leerId();

        if (id == null) {
            return;
        }

        String nombre = txtNombre.getText().toString().trim();
        String cedula = txtCedula.getText().toString().trim();
        String telefono = txtTelefono.getText().toString().trim();
        String correo = txtCorreo.getText().toString().trim();
        String ciudad = txtCiudad.getText().toString().trim();

        if (nombre.isEmpty()) {
            mostrarMensaje("Ingrese nombre y apellido");
            return;
        }

        if (cedula.isEmpty()) {
            mostrarMensaje("Ingrese su número de cédula");
            return;
        }

        if (telefono.isEmpty()) {
            mostrarMensaje("Ingrese su número de teléfono");
            return;
        }

        if (correo.isEmpty()) {
            mostrarMensaje("Ingrese su correo electrónico");
            return;
        }

        if (ciudad.isEmpty()) {
            mostrarMensaje("Ingrese la ciudad en donde vive");
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FeedReaderContract.FeedEntry.column1, nombre);
        values.put(FeedReaderContract.FeedEntry.column2, cedula);
        values.put(FeedReaderContract.FeedEntry.column3, telefono);
        values.put(FeedReaderContract.FeedEntry.column4, correo);
        values.put(FeedReaderContract.FeedEntry.column5, ciudad);

        String selection =
                FeedReaderContract.FeedEntry._ID + " = ?";

        String[] selectionArgs = {id};

        int count = db.update(
                FeedReaderContract.FeedEntry.nameTable,
                values,
                selection,
                selectionArgs
        );

        if (count > 0) {
            mostrarMensaje("Registro actualizado correctamente");
        } else {
            mostrarMensaje("No existe un registro con ese ID");
        }
    }

    public void Eliminar(View vista) {

        String id = leerId();

        if (id == null) {
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection =
                FeedReaderContract.FeedEntry._ID + " = ?";

        String[] selectionArgs = {id};

        int deletedRows = db.delete(
                FeedReaderContract.FeedEntry.nameTable,
                selection,
                selectionArgs
        );

        if (deletedRows > 0) {

            limpiarCampos();

            mostrarMensaje(
                    "Registro eliminado correctamente"
            );

        } else {

            mostrarMensaje(
                    "No existe un registro con ese ID"
            );
        }
    }

    public void Listar(View vista) {

        Intent intent =
                new Intent(this, listado.class);

        startActivity(intent);
    }

    private String leerId() {

        String id =
                txtid.getText().toString().trim();

        if (id.isEmpty()) {

            mostrarMensaje(
                    "Ingrese el ID del registro"
            );

            return null;
        }

        return id;
    }

    private void limpiarCampos() {

        txtid.setText("");
        txtNombre.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtCiudad.setText("");
    }

    private void mostrarMensaje(String mensaje) {

        Toast.makeText(
                this,
                mensaje,
                Toast.LENGTH_LONG
        ).show();
    }
}