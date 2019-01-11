package com.daniel.teildaniel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MedikamentAdapter extends RecyclerView.Adapter<MedikamentAdapter.MedikamentHolder> {

    private List<Medikament> medikamente = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public MedikamentAdapter.MedikamentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.medikament_item, parent, false);
        return new MedikamentAdapter.MedikamentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedikamentAdapter.MedikamentHolder holder, int position) {
        Medikament currentMedikament = medikamente.get(position);
        holder.textViewName.setText(currentMedikament.getName());
        holder.textViewWirkstoff.setText(currentMedikament.getWirkstoff());
        //holder.textViewPriority.setText(currentMedikament.getAufbewahrungshinweis());
        //holder.textViewPriority.setText(currentMedikament.getEinnahme());
        //holder.textViewPriority.setText(currentMedikament.getAnwendungsgebiet());
        //holder.textViewPriority.setText(currentMedikament.isVerschreibungspflichtig());
    }

    @Override
    public int getItemCount() {
        return medikamente.size();
    }

    public void setMedikamente(List<Medikament> medikamente) {
        this.medikamente = medikamente;
        notifyDataSetChanged();
    }

    public List<Medikament> getMedikamente() {
        return medikamente;
    }

    class MedikamentHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewWirkstoff;
        //private TextView textViewPriority;

        public MedikamentHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewWirkstoff = itemView.findViewById(R.id.text_view_wirkstoff);
            //textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(medikamente.get(position));
                    }
                }
            });

        }
    }

    public void filterList(ArrayList<Medikament> filteredList) {
        medikamente = filteredList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Medikament medikament);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
