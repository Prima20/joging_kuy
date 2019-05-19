package com.papb.prima.jogingkuy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class RegisterDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter_data);

        // Memulai transaksi
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // mengganti isi container dengan fragment baru
        ft.replace(R.id.your_placeholder, new ChooseGenderFragment());
        // atau ft.add(R.id.your_placeholder, new FooFragment());
        // mulai melakukan hal di atas (jika belum di commit maka proses di atas belum dimulai)
        ft.commit();
    }
}
