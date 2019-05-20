package com.papb.prima.jogingkuy;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.papb.prima.jogingkuy.api.APIUtils;
import com.papb.prima.jogingkuy.api.BaseApiService;

public class DetailEventActivity extends AppCompatActivity {

    private BaseApiService mApiService;

    private TextView txt_name_event_received,
            txt_penyelenggara_received, txt_date, txt_time, txt_alamat_received,
            txt_lokasi_received, txt_deskripsi;
    private Button btn_join_event;

    private String intent_action = "", intent_idevent = "",
            intent_namaevent = "",
            intent_tanggalevent = "",
            intent_alamatevent = "",
            intent_jamevent = "",
            intent_deskripsievent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        mApiService = APIUtils.getApiService();

        intent_idevent = getIntent().getStringExtra("intent_idevent");
        intent_namaevent = getIntent().getStringExtra("intent_namaevent");
        intent_tanggalevent = getIntent().getStringExtra("intent_tanggalevent");
        intent_alamatevent = getIntent().getStringExtra("intent_alamatevent");
        intent_jamevent = getIntent().getStringExtra("intent_jamevent");
        intent_deskripsievent = getIntent().getStringExtra("intent_deskripsievent");

        txt_name_event_received = findViewById(R.id.txt_name_event_received);
        txt_penyelenggara_received = findViewById(R.id.txt_penyelenggara_received);
        txt_date = findViewById(R.id.txt_date);
        txt_time = findViewById(R.id.txt_time);
        txt_alamat_received = findViewById(R.id.txt_alamat_received);
        txt_lokasi_received = findViewById(R.id.txt_lokasi_received);
        txt_deskripsi = findViewById(R.id.txt_deskripsi_received);
        btn_join_event = findViewById(R.id.btn_join_event);

            txt_name_event_received.setText(intent_namaevent);
//            txt_penyelenggara_received.setText(intent_);
            txt_date.setText(intent_tanggalevent);
            txt_time.setText(intent_jamevent);
            txt_alamat_received.setText(intent_alamatevent);
//            txt_lokasi_received.setText(intent_namaevent);
            txt_deskripsi.setText(intent_deskripsievent);
    }
}
