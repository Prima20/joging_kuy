package com.papb.prima.jogingkuy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.papb.prima.jogingkuy.R;
import com.papb.prima.jogingkuy.model.Event;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private Context context;
    private ArrayList<Event> events;

    public EventAdapter(Context context) {
        this.context = context;
        events = new ArrayList<>();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_event, parent, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        holder.txtName.setText(getEvents().get(position).getNamaEvent());
        holder.txtDate.setText(getEvents().get(position).getTanggalEvent());
    }

    @Override
    public int getItemCount() {
        return getEvents().size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDate;

        public EventHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name);
            txtDate = itemView.findViewById(R.id.txt_date);
        }
    }
}
