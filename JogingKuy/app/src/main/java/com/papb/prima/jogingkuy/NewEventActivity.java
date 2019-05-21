package com.papb.prima.jogingkuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class NewEventActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener,
        TimePickerFragment.DialogTimeListener {

    EditText edt_nama_event, edt_deskripsi_event;
    ImageButton btn_date_event, btn_time_event, btn_lokasi_event;
    Button btn_set_new_event;
    TextView tv_date_event, tv_time_event, tv_alamat_event;

    final String DATE_PICKER_TAG = "DatePicker";
    final String TIME_PICKER_TAG = "TimePicker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        edt_nama_event = findViewById(R.id.edt_nama_event);
        edt_deskripsi_event = findViewById(R.id.edt_deskripsi_event);

        btn_date_event = findViewById(R.id.btn_date_event);
        btn_time_event = findViewById(R.id.btn_time_event);

        btn_set_new_event = findViewById(R.id.btn_set_new_event);

        tv_date_event = findViewById(R.id.tv_date_event);
        tv_time_event = findViewById(R.id.tv_time_event);
        tv_alamat_event = findViewById(R.id.tv_alamat_event);

        btn_date_event.setOnClickListener(this);
        btn_time_event.setOnClickListener(this);
        btn_set_new_event.setOnClickListener(this);

        btn_lokasi_event = findViewById(R.id.btn_lokasi_event);
        btn_lokasi_event.setOnClickListener(this);

        //Terima intent dari class MapsActivity
        if(getIntent().getExtras() != null){
            edt_nama_event.setText(getIntent().getStringExtra("nama"));
            edt_deskripsi_event.setText(getIntent().getStringExtra("deskripsi"));
            tv_date_event.setText(getIntent().getStringExtra("date"));
            tv_time_event.setText(getIntent().getStringExtra("time"));

            tv_alamat_event.setText(getIntent().getStringExtra("lokasi"));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //Button untuk memanggil date picker
            case R.id.btn_date_event:
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                break;
            //Button untuk memanggil time picker
            case R.id.btn_time_event:
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), TIME_PICKER_TAG);
                break;
            //Button untuk memanggil lokasi event
            case R.id.btn_lokasi_event:
                Intent intentToLokasi = new Intent(NewEventActivity.this, MapsActivity.class);
                intentToLokasi.putExtra("nama", edt_nama_event.getText().toString());
                intentToLokasi.putExtra("deskripsi", edt_deskripsi_event.getText().toString());
                intentToLokasi.putExtra("date", tv_date_event.getText().toString());
                intentToLokasi.putExtra("time", tv_time_event.getText().toString());
                startActivity(intentToLokasi);
                break;
            //Button untuk set event baru
            case R.id.btn_set_new_event:
                break;
        }
    }

    //=============NOTE==================
    //Kedua method dibawah hasil dari callback (DatePickerFragment & TimePickerFragment)

    //Hasil dari implementasi DatePickerFragment.DialogDateListener
    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        //Menggunakan SimpleDateFormat
        //"yyyy-MM-dd" -> seperti 2016-09-29
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());

        tv_date_event.setText(dateFormat.format(calendar.getTime()));
    }

    //Hasil dari implementasi TimePickerFragment.DialogTimeListener
    @Override
    public void onDialogTimeSet(String tag, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        //Menggunakan SimpleDateFormat
        //"HH:mm" -> seperti 12:00
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        tv_time_event.setText(dateFormat.format(calendar.getTime()));
    }
}
