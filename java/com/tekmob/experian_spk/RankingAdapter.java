package com.tekmob.experian_spk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Ranking> rankingList;

    public RankingAdapter(Context context, ArrayList<Ranking> rankingList) {
        this.context = context;
        this.rankingList = rankingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_ranking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ranking ranking = rankingList.get(position);
        holder.rankTextView.setText(String.valueOf(ranking.getRank()));
        holder.namaTextView.setText(ranking.getNama());
        holder.pendapatanTextView.setText(String.valueOf(ranking.getBobotPendapatan()));
        holder.tanggunganTextView.setText(String.valueOf(ranking.getBobotTanggungan()));
        holder.umurTextView.setText(String.valueOf(ranking.getBobotUmur()));
        holder.kekayaanTextView.setText(String.valueOf(ranking.getBobotKekayaan()));
        holder.hasilWPTextView.setText(String.valueOf(ranking.getHasilWP()));
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rankTextView, namaTextView, pendapatanTextView, tanggunganTextView, umurTextView, kekayaanTextView, hasilWPTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rankTextView = itemView.findViewById(R.id.textRank);
            namaTextView = itemView.findViewById(R.id.textNama);
            pendapatanTextView = itemView.findViewById(R.id.textPendapatan);
            tanggunganTextView = itemView.findViewById(R.id.textTanggungan);
            umurTextView = itemView.findViewById(R.id.textUmur);
            kekayaanTextView = itemView.findViewById(R.id.textKekayaan);
            hasilWPTextView = itemView.findViewById(R.id.textHasilWP);
        }
    }
}
