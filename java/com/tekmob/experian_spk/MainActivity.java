package com.tekmob.experian_spk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNama, etPendapatan, etTanggungan, etUmur, etKekayaan;
    private DBHelper dbHelper;
    private Button btnSubmit, btnLihatHasil;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        etNama = findViewById(R.id.etNama);
        etPendapatan = findViewById(R.id.etPendapatan);
        etTanggungan = findViewById(R.id.etTanggungan);
        etUmur = findViewById(R.id.etUmur);
        etKekayaan = findViewById(R.id.etKekayaan);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnLihatHasil = findViewById(R.id.btnLihatHasil);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input from user
                String nama = etNama.getText().toString();

                // Check for empty fields
                if (nama.isEmpty() || etPendapatan.getText().toString().isEmpty() ||
                        etTanggungan.getText().toString().isEmpty() || etUmur.getText().toString().isEmpty() ||
                        etKekayaan.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert user input to double
                double pendapatanAsli, tanggunganAsli, umurAsli, kekayaanAsli;
                try {
                    pendapatanAsli = Double.parseDouble(etPendapatan.getText().toString());
                    tanggunganAsli = Double.parseDouble(etTanggungan.getText().toString());
                    umurAsli = Double.parseDouble(etUmur.getText().toString());
                    kekayaanAsli = Double.parseDouble(etKekayaan.getText().toString());
                } catch (NumberFormatException e) {
                    // Handle input error
                    Toast.makeText(MainActivity.this, "Input tidak valid. Masukkan angka yang benar.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert data into the database
                dbHelper.insertData(nama, pendapatanAsli, tanggunganAsli, umurAsli, kekayaanAsli);

                // Calculate and store the weighted product result
                WeightedProduct wp = new WeightedProduct(dbHelper);
                wp.calculateAndStoreWP();

                Toast.makeText(MainActivity.this, "Data berhasil disimpan dan WP dihitung", Toast.LENGTH_SHORT).show();
            }
        });

        btnLihatHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HasilActivity.class);
                startActivity(intent);
            }
        });
    }
}
