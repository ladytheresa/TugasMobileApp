package umn.ac.tugasmobile;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tblFavorite")
public class FavoriteList implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nama_lengkap") private String nama_lengkap;
    @ColumnInfo(name = "nomor_hp") private String nomor_hp;

    public FavoriteList(String nama_lengkap, String nomor_hp) {
        this.nama_lengkap = nama_lengkap;
        this.nomor_hp = nomor_hp;
    }

    public void setNama_lengkap(String nama_lengkap) {this.nama_lengkap = nama_lengkap;}
    public void setNomor_hp(String nomor_hp) {this.nomor_hp = nomor_hp;}

    public String getNama_lengkap() { return this.nama_lengkap; }
    public String getNomor_hp() { return this.nomor_hp; }
}