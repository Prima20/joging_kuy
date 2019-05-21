package com.papb.prima.jogingkuy.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.papb.prima.jogingkuy.MapsActivity;
import com.papb.prima.jogingkuy.R;
import com.papb.prima.jogingkuy.fragment.DatePickerFragment;
import com.papb.prima.jogingkuy.fragment.TimePickerFragment;
import com.papb.prima.jogingkuy.model.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewEventActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener,
        TimePickerFragment.DialogTimeListener {

    EditText edt_nama_event, edt_deskripsi_event;
    ImageButton btn_date_event, btn_time_event, btn_lokasi_event;
    Button btn_set_new_event;
    TextView tv_date_event, tv_time_event, tv_alamat_event;

    final String DATE_PICKER_TAG = "DatePicker";
    final String TIME_PICKER_TAG = "TimePicker";

    //Firebase properties
    private DatabaseReference database;

    String namaEvent, tanggalEvent, alamatEvent, jamEvent, deskripsiEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        database = FirebaseDatabase.getInstance().getReference("EVENT");

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

        if (getIntent().getExtras() != null) {
//            edt_nama_event.setText(getIntent().getStringExtra("nama"));
//            edt_deskripsi_event.setText(getIntent().getStringExtra("deskripsi"));
//            tv_date_event.setText(getIntent().getStringExtra("date"));
//            tv_time_event.setText(getIntent().getStringExtra("time"));

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
//                intentToLokasi.putExtra("nama", edt_nama_event.getText().toString());
//                intentToLokasi.putExtra("deskripsi", edt_deskripsi_event.getText().toString());
//                intentToLokasi.putExtra("date", tv_date_event.getText().toString());
//                intentToLokasi.putExtra("time", tv_time_event.getText().toString());
                startActivity(intentToLokasi);
                break;
            //Button untuk set event baru
            case R.id.btn_set_new_event:

                namaEvent = edt_nama_event.getText().toString();
                deskripsiEvent = edt_deskripsi_event.getText().toString();

                //Alamat Event to Firebase
//                alamatEvent = tv_alamat_event.getText().toString();

                createEvent(namaEvent, tanggalEvent, alamatEvent, jamEvent, deskripsiEvent);
                break;
        }
    }

    //method for create event and add to firebase
    private void createEvent(String namaEvent, String tanggalEvent, String alamatEvent, String jamEvent, String deskripsiEvent) {
        String eventId = database.push().getKey().toString();
        Event event = new Event(eventId, namaEvent, tanggalEvent, alamatEvent, jamEvent, deskripsiEvent);

        database.child(eventId).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("Event", "Successfulyy created");
                    Toast.makeText(getApplicationContext(), "event created", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Event", task.getException().toString());
                }
            }
        });
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

        tanggalEvent = dateFormat.format(calendar.getTime());
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

        jamEvent = dateFormat.format(calendar.getTime());
        tv_time_event.setText(dateFormat.format(calendar.getTime()));
    }
}
