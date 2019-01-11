package com.daniel.teildaniel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MedikamentAdapter extends RecyclerView.Adapter<MedikamentAdapter.MedikamentHolder> implements Filterable {

    private List<Medikament> medikamente = new ArrayList<>();

    private List<Medikament> mOriginalValues = new ArrayList<>();

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
        this.mOriginalValues = medikamente;
        notifyDataSetChanged();
    }

    public List<Medikament> getMedikamente() {
        return medikamente;
    }

    // ---------------------------------------------------------------------------------------------

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                medikamente = (ArrayList<Medikament>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Medikament> FilteredArrList = new ArrayList<Medikament>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Medikament>(medikamente); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Medikament(mOriginalValues.get(i).getName(),mOriginalValues.get(i).getWirkstoff(), mOriginalValues.get(i).getAnwendungsgebiet(), mOriginalValues.get(i).getVerschreibungspflichtig()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    // ---------------------------------------------------------------------------------------------

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
