package com.papb.prima.jogingkuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SampleMapActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_lokasi_event;
    Button btn_set_new_event;
    TextView tv_alamat_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_map);

        tv_alamat_event = findViewById(R.id.tv_alamat_event);

        btn_lokasi_event = findViewById(R.id.btn_lokasi_event);
        btn_lokasi_event.setOnClickListener(this);

        btn_set_new_event = findViewById(R.id.btn_set_new_event);
        btn_set_new_event.setOnClickListener(this);

        if(getIntent().getExtras() != null){
            tv_alamat_event.setText(getIntent().getStringExtra("lokasi"));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //Button untuk memanggil lokasi event
            case R.id.btn_lokasi_event:
                Intent intentLokasi = new Intent(this, MapsActivity.class);
                startActivity(intentLokasi);
                break;
        }
    }
}
