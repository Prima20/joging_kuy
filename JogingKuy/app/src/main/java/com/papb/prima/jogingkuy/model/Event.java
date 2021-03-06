package com.papb.prima.jogingkuy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id_event")
    @Expose
    private String idEvent;
    @SerializedName("nama_event")
    @Expose
    private String namaEvent;
    @SerializedName("deskripsi_event")
    @Expose
    private String deskripsiEvent;
    @SerializedName("tanggal_event")
    @Expose
    private String tanggalEvent;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("jam_event")
    @Expose
    private String jamEvent;

    public Event(String idEvent, String namaEvent, String tanggalEvent, String alamatEvent, String jamEvent, String deskripsiEvent) {
        this.idEvent = idEvent;
        this.namaEvent = namaEvent;
        this.tanggalEvent = tanggalEvent;
        this.alamat = alamatEvent;
        this.jamEvent = jamEvent;
        this.deskripsiEvent = deskripsiEvent;
    }

    public Event() {
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getDeskripsiEvent() {
        return deskripsiEvent;
    }

    public void setDeskripsiEvent(String deskripsiEvent) {
        this.deskripsiEvent = deskripsiEvent;
    }

    public String getTanggalEvent() {
        return tanggalEvent;
    }

    public void setTanggalEvent(String tanggalEvent) {
        this.tanggalEvent = tanggalEvent;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJamEvent() {
        return jamEvent;
    }

    public void setJamEvent(String jamEvent) {
        this.jamEvent = jamEvent;
    }
}