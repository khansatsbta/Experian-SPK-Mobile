package com.tekmob.experian_spk;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HasilActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private RankingAdapter rankingAdapter;
    private ArrayList<Ranking> rankingList;
    private TextView alertTextView;
    private static final String TAG = "HasilActivity"; // For logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        alertTextView = findViewById(R.id.alertTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rankingList = new ArrayList<>();
        rankingAdapter = new RankingAdapter(this, rankingList);
        recyclerView.setAdapter(rankingAdapter);

        // Fetch and display data
        loadRankingData();

        // Display the top-ranked nasabah
        showTopRank();
    }

    private void loadRankingData() {
        Cursor cursor = dbHelper.getRankingData();
        Log.d(TAG, "Fetching data from hasil_wp table.");

        if (cursor != null && cursor.getCount() > 0) {
            Log.d(TAG, "Data found: " + cursor.getCount() + " records.");

            int rank = 1;
            while (cursor.moveToNext()) {
                String nama = cursor.getString(cursor.getColumnIndex("nama"));
                double pendapatan = cursor.getDouble(cursor.getColumnIndex("bobotpendapatan"));
                double tanggungan = cursor.getDouble(cursor.getColumnIndex("bobottanggungan"));
                double umur = cursor.getDouble(cursor.getColumnIndex("bobotumur"));
                double kekayaan = cursor.getDouble(cursor.getColumnIndex("bobotkekayaan"));
                double hasilWP = cursor.getDouble(cursor.getColumnIndex("hasil_wp"));

                rankingList.add(new Ranking(rank, nama, pendapatan, tanggungan, umur, kekayaan, hasilWP));
                rank++;
            }
            rankingAdapter.notifyDataSetChanged();
        } else {
            Log.d(TAG, "No data found in hasil_wp table.");
            alertTextView.setText("Tidak ada data untuk ditampilkan.");
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private void showTopRank() {
        Cursor cursor = dbHelper.getTopRankingData();
        if (cursor != null && cursor.moveToFirst()) {
            String namaTop = cursor.getString(cursor.getColumnIndex("nama"));
            alertTextView.setText("Nasabah dengan nama " + namaTop + " menjadi urutan pertama dalam pemilihan Kelayakan Kredit.");
        }
        if (cursor != null) {
            cursor.close();
        }
    }
}
