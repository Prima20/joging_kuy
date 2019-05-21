package com.papb.prima.jogingkuy;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    DialogDateListener mListener;

    //3-> Fungsi onAttach = hanya sekali dipanggil dalam fragment
    //berfungsi untuk mengaitkan dengan Activity pemanggil
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mListener = (DialogDateListener) context;
        }
    }

    //4-> Fungsi onDetach() = hanya dipanggil sebelum fragment tidak lagi dikaitkan dengan Activity pemanggil
    @Override
    public void onDetach() {
        super.onDetach();
        if (mListener != null) {
            mListener = null;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        return new DatePickerDialog(getActivity(), this, year, month, date);
    }

    //1-> Fungsi method onDateSet = akan dipanggil ketika memilih tanggal yang diinginkan
    //Setelah tanggal dipilih, variabel tanggal, bulan dan tahun dikirim ke MainActivity
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mListener.onDialogDateSet(getTag(), year, month, dayOfMonth);
    }

    //2-> Pengiriman variabel tanggal, bulan, tahun menggunakan bantuan interface DialogDateListener
    public interface DialogDateListener {
        void onDialogDateSet(String tag, int year, int month, int dayOfMonth);
    }
}