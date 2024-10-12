package com.tekmob.experian_spk;

import android.database.Cursor;
import android.util.Log;

public class WeightedProduct {
    private DBHelper dbHelper;
    private static final String TAG = "WeightedProduct"; // For logging

    // Bobot kriteria
    private final double bobotPendapatan = 5;
    private final double bobotTanggungan = 4;
    private final double bobotUmur = 2;
    private final double bobotKekayaan = 5;

    public WeightedProduct(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void calculateAndStoreWP() {
        Cursor cursor = dbHelper.getAllData();
        int jumlahNasabah = cursor.getCount();

        if (jumlahNasabah == 0) {
            Log.e(TAG, "No data found in data_kriteria table.");
            cursor.close();
            return;
        }

        double totalBobot = bobotPendapatan + bobotTanggungan + bobotUmur + bobotKekayaan;

        // Perbaikan Bobot
        double pbPendapatan = bobotPendapatan / totalBobot;
        double pbTanggungan = bobotTanggungan / totalBobot;
        double pbUmur = bobotUmur / totalBobot;
        double pbKekayaan = bobotKekayaan / totalBobot;

        double[] vektorS = new double[jumlahNasabah];
        double totalS = 0;
        int i = 0;

        // Menghitung vektor S untuk setiap nasabah
        while (cursor.moveToNext()) {
            String nama = cursor.getString(cursor.getColumnIndex("nama"));
            double pendapatan = cursor.getDouble(cursor.getColumnIndex("pendapatan"));
            double tanggungan = cursor.getDouble(cursor.getColumnIndex("tanggungan"));
            double umur = cursor.getDouble(cursor.getColumnIndex("umur"));
            double kekayaan = cursor.getDouble(cursor.getColumnIndex("kekayaan"));

            double nilaiS = Math.pow(pendapatan, pbPendapatan)
                    * Math.pow(tanggungan, -pbTanggungan)  // Negatif untuk cost
                    * Math.pow(umur, -pbUmur)  // Negatif untuk cost
                    * Math.pow(kekayaan, pbKekayaan);

            vektorS[i] = nilaiS;
            totalS += nilaiS;

            Log.d(TAG, "Calculated S for " + nama + ": " + nilaiS);

            dbHelper.insertVektorS(nama, pbPendapatan, pbTanggungan, pbUmur, pbKekayaan, nilaiS);
            i++;
        }

        for (int j = 0; j < jumlahNasabah; j++) {
            double vektorV = vektorS[j] / totalS;

            cursor.moveToPosition(j);
            String nama = cursor.getString(cursor.getColumnIndex("nama"));

            Log.d(TAG, "Calculated V for " + nama + ": " + vektorV);

            // Insert WP result into hasil_wp table
            long result = dbHelper.insertHasilWP(nama, bobotPendapatan, bobotTanggungan, bobotUmur, bobotKekayaan, vektorV);

            // Check if the data was inserted successfully
            if (result == -1) {
                Log.e(TAG, "Failed to insert WP result for " + nama);
            } else {
                Log.d(TAG, "Successfully inserted WP result for " + nama + " with row ID: " + result);
            }
        }

        cursor.close();
        Log.d(TAG, "Weighted product calculation and insertion completed.");
    }
}
