package com.papb.prima.jogingkuy.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.papb.prima.jogingkuy.R;
import com.papb.prima.jogingkuy.adapter.EventAdapter;
import com.papb.prima.jogingkuy.api.BaseApiService;
import com.papb.prima.jogingkuy.model.Event;
import com.papb.prima.jogingkuy.model.EventReadResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyEventFragment extends Fragment {

    RecyclerView rvMyEvent;
    private EventAdapter eventAdapter;

    private List<EventReadResponse> mEventList = new ArrayList<>();
    private List<Event> listEvent = new ArrayList<>();

    //Firebase properties
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference ref;

    public MyEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_event, container, false);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        String userId = auth.getCurrentUser().getUid();

        DatabaseReference eventReference = database.getReference("USERS").child(userId).child("eventId");

        eventReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String eventId = dataSnapshot.getValue(String.class);
                ref = database.getReference("EVENT");
                readEventFromFirebase(eventId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("USerId:",userId);

        rvMyEvent = rootView.findViewById(R.id.rv_my_events);

        rvMyEvent.setHasFixedSize(true);
        rvMyEvent.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter(getContext(), listEvent);
        rvMyEvent.setItemAnimator(new DefaultItemAnimator());
        rvMyEvent.setAdapter(eventAdapter);

        return rootView;
    }

    private void readEventFromFirebase(final String selectedEventId){
        rvMyEvent.setVisibility(View.GONE);

        mEventList.clear();
        listEvent.clear();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Event event = dataSnapshot1.getValue(Event.class);
                    if(event.getIdEvent().equals(selectedEventId)){
                        listEvent.add(event);
                    }
                }

                eventAdapter = new EventAdapter(getActivity(), listEvent);
                rvMyEvent.setAdapter(eventAdapter);
                rvMyEvent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        readEventFromFirebase();
    }
}
