package com.example.taxi;

import java.io.Serializable;

public class Taxi_01 implements Serializable {

    int ma;
    String soXe;
    float quangDuong;
    int donGia;
    int khuyenMai;

    public Taxi_01(String soXe, float quangDuong, int donGia, int khuyenMai) {
        this.soXe = soXe;
        this.quangDuong = quangDuong;
        this.donGia = donGia;
        this.khuyenMai = khuyenMai;
    }


    public Taxi_01(int ma, String soXe, float quangDuong, int donGia, int khuyenMai) {
        this.ma = ma;
        this.soXe = soXe;
        this.quangDuong = quangDuong;
        this.donGia = donGia;
        this.khuyenMai = khuyenMai;
    }

    public Taxi_01() {
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getSoXe() {
        return soXe;
    }

    public void setSoXe(String soXe) {
        this.soXe = soXe;
    }

    public float getQuangDuong() {
        return quangDuong;
    }

    public void setQuangDuong(float quangDuong) {
        this.quangDuong = quangDuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(int khuyenMai) {
        this.khuyenMai = khuyenMai;
    }
}
