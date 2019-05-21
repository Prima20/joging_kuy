package com.papb.prima.jogingkuy.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.TimePicker;

import java.util.Calendar;

//Berbeda dengan DatePickerFragment
//Pada class TimePickerFragment diimplementasikan dengan metode TimePickerDialog.OnTimeSetListener
//(Variabel yang berbeda => jam dan menit)
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    DialogTimeListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mListener = (DialogTimeListener) context;
        }
    }

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
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        boolean formatHour24 = true;
        return new TimePickerDialog(getActivity(), this, hour, minute, formatHour24);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mListener.onDialogTimeSet(getTag(), hourOfDay, minute);
    }

    public interface DialogTimeListener {
        void onDialogTimeSet(String tag, int hourOfDay, int minute);
    }
}