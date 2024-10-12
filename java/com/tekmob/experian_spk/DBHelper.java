package com.tekmob.experian_spk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "experian.db";
    public static final int DATABASE_VERSION = 2;
    private static final String TAG = "DBHelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Create data_kriteria table
            db.execSQL("CREATE TABLE data_kriteria (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, pendapatan REAL, tanggungan REAL, umur REAL, kekayaan REAL)");

            // Create hasil_wp table
            db.execSQL("CREATE TABLE hasil_wp (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, bobotpendapatan REAL, bobottanggungan REAL, bobotumur REAL, bobotkekayaan REAL, hasil_wp REAL)");

            // Create vektors table (the table that was missing)
            db.execSQL("CREATE TABLE vektors (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, pbpendapatan REAL, pbtanggungan REAL, pbumur REAL, pbkekayaan REAL, vektors REAL)");

            Log.d(TAG, "Tables created successfully");
        } catch (SQLException e) {
            Log.e(TAG, "Error creating tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS data_kriteria");
            db.execSQL("DROP TABLE IF EXISTS hasil_wp");
            db.execSQL("DROP TABLE IF EXISTS vektors");  // Make sure to drop vektors table if it exists
            onCreate(db);
            Log.d(TAG, "Database upgraded successfully");
        } catch (SQLException e) {
            Log.e(TAG, "Error upgrading database: " + e.getMessage());
        }
    }

    public void insertData(String nama, double pendapatan, double tanggungan, double umur, double kekayaan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("pendapatan", pendapatan);
        contentValues.put("tanggungan", tanggungan);
        contentValues.put("umur", umur);
        contentValues.put("kekayaan", kekayaan);
        try {
            db.insert("data_kriteria", null, contentValues);
            Log.d(TAG, "Data inserted successfully: " + nama);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting data: " + e.getMessage());
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM data_kriteria", null);
            Log.d(TAG, "Data retrieved successfully");
            return cursor;
        } catch (SQLException e) {
            Log.e(TAG, "Error retrieving data: " + e.getMessage());
            return null;
        }
    }

    public void insertVektorS(String nama, double pbPendapatan, double pbTanggungan, double pbUmur, double pbKekayaan, double vektorS) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("pbpendapatan", pbPendapatan);
        contentValues.put("pbtanggungan", pbTanggungan);
        contentValues.put("pbumur", pbUmur);
        contentValues.put("pbkekayaan", pbKekayaan);
        contentValues.put("vektors", vektorS);
        try {
            db.insert("vektors", null, contentValues);
            Log.d(TAG, "Vektor S inserted successfully: " + nama);
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting vektor S: " + e.getMessage());
        }
    }

    public long insertHasilWP(String nama, double bobotPendapatan, double bobotTanggungan, double bobotUmur, double bobotKekayaan, double hasilWP) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("bobotpendapatan", bobotPendapatan);
        contentValues.put("bobottanggungan", bobotTanggungan);
        contentValues.put("bobotumur", bobotUmur);
        contentValues.put("bobotkekayaan", bobotKekayaan);
        contentValues.put("hasil_wp", hasilWP);

        String query = "SELECT * FROM hasil_wp WHERE nama=?";
        Cursor cursor = db.rawQuery(query, new String[]{nama});

        long result;
        if (cursor.getCount() > 0) {
            // If it exists, update the record
            result = db.update("hasil_wp", contentValues, "nama=?", new String[]{nama});
            Log.d(TAG, "Updated existing WP result for " + nama);
        } else {
            // If it does not exist, insert a new row
            result = db.insert("hasil_wp", null, contentValues);
            Log.d(TAG, "Inserted new WP result for " + nama);
        }
        cursor.close();
        return result; // Return the result of the insert/update operation
    }

    public Cursor getRankingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM hasil_wp ORDER BY hasil_wp DESC";
        return db.rawQuery(query, null);
    }

    public Cursor getTopRankingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM hasil_wp ORDER BY hasil_wp DESC LIMIT 1";
        return db.rawQuery(query, null);
    }
}
