package com.tekmob.experian_spk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    private Button btnContinue;
    private TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        txtInfo = findViewById(R.id.txtInfo);
        btnContinue = findViewById(R.id.btnContinue);

        // Set the information about the application
        txtInfo.setText(
                "EXPERIAN merupakan aplikasi sistem pendukung keputusan untuk menentukan kelayakan kredit menggunakan metode Weighted Product.\n\n" +
                        "Kriteria kelayakan kredit yang diterapkan adalah sebagai berikut:\n\n" +
                        "1. Pendapatan (C1) - Benefit (Bobot: 5)\n" +
                        "    0 = 1\n" +
                        "    > 1 - <= 4.999.999 = 2\n" +
                        "    > 5.000.000 - <= 10.000.000 = 3\n" +
                        "    > 10.000.000 = 4\n\n" +
                        "2. Tanggungan (C2) - Cost (Bobot: 4)\n" +
                        "    Jumlah 1 = 4\n" +
                        "    Jumlah 2 = 3\n" +
                        "    Jumlah 3 = 2\n" +
                        "    Jumlah >3 = 1\n\n" +
                        "3. Umur (C3) - Cost (Bobot: 2)\n" +
                        "    > 50 = 1\n" +
                        "    41 - 50 = 2\n" +
                        "    31 - 40 = 3\n" +
                        "    21 - 30 = 4\n\n" +
                        "4. Kekayaan (A4) - Benefit (Bobot: 5)\n" +
                        "    > 2 M = 4\n" +
                        "    1 M - 2 M = 3\n" +
                        "    100 JT - 1 M = 2\n" +
                        "    0 - 99.999.999 = 1"
        );

        // Set action for the Continue button
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainActivity and add transition animation
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish(); // Close this activity
            }
        });
    }
}
