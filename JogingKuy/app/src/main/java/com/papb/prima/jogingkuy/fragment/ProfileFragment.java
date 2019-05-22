package com.papb.prima.jogingkuy.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.papb.prima.jogingkuy.R;
import com.papb.prima.jogingkuy.model.Event;
import com.papb.prima.jogingkuy.model.User;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView tv_namaUser, tv_weight, tv_height;

    FirebaseAuth auth;
    FirebaseDatabase database;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String uId = auth.getCurrentUser().getUid();

        tv_namaUser = rootView.findViewById(R.id.tV_namauser);
        tv_height = rootView.findViewById(R.id.tV_height_received);
        tv_weight = rootView.findViewById(R.id.tV_weight_received);

        setProfile(uId);

        return rootView;
    }

    void setProfile(String uId){
        DatabaseReference weight = database.getReference("USERS").child(uId).child("weight");

        weight.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int weight = dataSnapshot.getValue(Integer.class);
                tv_weight.setText(String.valueOf(weight) + " kg");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference height = database.getReference("USERS").child(uId).child("height");

        height.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int height = dataSnapshot.getValue(Integer.class);
                tv_height.setText(String.valueOf(height) + " cm");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference username = database.getReference("USERS").child(uId).child("username");

        username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getValue(String.class);
                tv_namaUser.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
