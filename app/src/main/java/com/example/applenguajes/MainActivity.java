package com.example.applenguajes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etCedula, etTelefono, etCorreo, etCiudad;
    Button btnGuardar;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etCedula = findViewById(R.id.etCedula);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        etCiudad = findViewById(R.id.etCiudad);
        btnGuardar = findViewById(R.id.btnGuardar);

        db = new DatabaseHelper(this);

        btnGuardar.setOnClickListener(v -> {

            String nombre = etNombre.getText().toString();
            String cedula = etCedula.getText().toString();
            String telefono = etTelefono.getText().toString();
            String correo = etCorreo.getText().toString();
            String ciudad = etCiudad.getText().toString();

            boolean guardado = db.insertarPersona(
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
}