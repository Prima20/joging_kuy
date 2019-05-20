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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.papb.prima.jogingkuy.R;
import com.papb.prima.jogingkuy.adapter.EventAdapter;
import com.papb.prima.jogingkuy.api.APIUtils;
import com.papb.prima.jogingkuy.api.BaseApiService;
import com.papb.prima.jogingkuy.model.Event;
import com.papb.prima.jogingkuy.model.EventReadResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView rvEvent;
    private EventAdapter eventAdapter;

    private BaseApiService mApiService;
    private ProgressBar pb_load;

    private List<EventReadResponse> mEventList = new ArrayList<>();
    private List<Event> listEvent = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mApiService = APIUtils.getApiService();

        pb_load = rootView.findViewById(R.id.pb_load);
        rvEvent = rootView.findViewById(R.id.rv_events);

        rvEvent.setHasFixedSize(true);
        rvEvent.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter(getContext(), listEvent);
        rvEvent.setItemAnimator(new DefaultItemAnimator());
        rvEvent.setAdapter(eventAdapter);

        return rootView;
    }

    private void readEvents(){
        rvEvent.setVisibility(View.GONE);

        mEventList.clear();
        listEvent.clear();

        mApiService.getEvent().enqueue(new Callback<EventReadResponse>() {
            @Override
            public void onResponse(Call<EventReadResponse> call, Response<EventReadResponse> response) {
                pb_load.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    try {
                        int total = response.body().getEvent().size();

                        for (int a=0; a<total; a++){
                            Event modelSeatGroup = new Event(
                                    response.body().getEvent().get(a).getIdEvent(),
                                    response.body().getEvent().get(a).getNamaEvent(),
                                    response.body().getEvent().get(a).getTanggalEvent());
                            listEvent.add(modelSeatGroup);
                        }

                        EventReadResponse item = new EventReadResponse(listEvent);
                        mEventList.add(item);

                        eventAdapter = new EventAdapter(getActivity(), listEvent);
                        rvEvent.setAdapter(eventAdapter);

                        if (listEvent.isEmpty()) {
                            rvEvent.setVisibility(View.GONE);

                            Toast.makeText(getActivity(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        } else {
                            rvEvent.setVisibility(View.VISIBLE);
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), "Please try again, server is down", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventReadResponse> call, Throwable t) {
                pb_load.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Please try again, server is down onfail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        pb_load.setVisibility(View.GONE);
        readEvents();
    }
}
