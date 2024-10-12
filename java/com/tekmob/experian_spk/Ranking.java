package com.tekmob.experian_spk;

public class Ranking {

    private final int rank;
    private final String nama;
    private final double bobotPendapatan;
    private final double bobotTanggungan;
    private final double bobotUmur;
    private final double bobotKekayaan;
    private final double hasilWP;

    public Ranking(int rank, String nama, double bobotPendapatan, double bobotTanggungan, double bobotUmur, double bobotKekayaan, double hasilWP) {
        this.rank = rank;
        this.nama = nama;
        this.bobotPendapatan = bobotPendapatan;
        this.bobotTanggungan = bobotTanggungan;
        this.bobotUmur = bobotUmur;
        this.bobotKekayaan = bobotKekayaan;
        this.hasilWP = hasilWP;
    }

    public int getRank() {
        return rank;
    }

    public String getNama() {
        return nama;
    }

    public double getBobotPendapatan() {
        return bobotPendapatan;
    }

    public double getBobotTanggungan() {
        return bobotTanggungan;
    }

    public double getBobotUmur() {
        return bobotUmur;
    }

    public double getBobotKekayaan() {
        return bobotKekayaan;
    }

    public double getHasilWP() {
        return hasilWP;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "rank=" + rank +
                ", nama='" + nama + '\'' +
                ", bobotPendapatan=" + bobotPendapatan +
                ", bobotTanggungan=" + bobotTanggungan +
                ", bobotUmur=" + bobotUmur +
                ", bobotKekayaan=" + bobotKekayaan +
                ", hasilWP=" + hasilWP +
                '}';
    }
}
