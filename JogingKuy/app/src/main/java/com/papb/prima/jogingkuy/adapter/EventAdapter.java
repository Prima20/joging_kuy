package com.papb.prima.jogingkuy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.papb.prima.jogingkuy.DetailEventActivity;
import com.papb.prima.jogingkuy.R;
import com.papb.prima.jogingkuy.model.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private Context context;
    private List<Event> events;
    private Activity activity;

    public EventAdapter(Context context, List<Event> events) {
        this.context = context;
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
        final Event event = events.get(position);

        holder.txtName.setText(event.getNamaEvent());
        holder.txtDate.setText(event.getTanggalEvent());

        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailEvent = new Intent(context, DetailEventActivity.class);
                detailEvent.putExtra("intent_idevent", event.getIdEvent());
                detailEvent.putExtra("intent_namaevent", event.getNamaEvent());
                detailEvent.putExtra("intent_tanggalevent", event.getTanggalEvent());
                detailEvent.putExtra("intent_alamatevent", event.getAlamat());
                detailEvent.putExtra("intent_jamevent", event.getJamEvent());
                detailEvent.putExtra("intent_deskripsievent", event.getDeskripsiEvent());
//                detailEvent.putExtra("intent_lokasievent", event.getLokasiEvent());

                view.getContext().startActivity(detailEvent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDate;
        RelativeLayout rl_layout;

        public EventHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name);
            txtDate = itemView.findViewById(R.id.txt_date);

            rl_layout = itemView.findViewById(R.id.rl_layout);
        }
    }
}
