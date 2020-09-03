package eg.mahmoudShawky.metar.ui.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;
import eg.mahmoudShawky.metar.databinding.ListItemStationBinding;

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.StationViewHolder> implements Filterable {

    private ArrayList<StationEntity> stationsList;
    private ArrayList<StationEntity> stationsListFiltered;
    private boolean isFilterMode = false;
    private StationListener listener;

    public void update(List<StationEntity> stationsList) {
        this.stationsList = (ArrayList<StationEntity>) stationsList;
        if(!isFilterMode){
            stationsListFiltered = this.stationsList;
            notifyDataSetChanged();
        }
    }

    public void addListener(StationListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemStationBinding binding = ListItemStationBinding.inflate(inflater, parent, false);
        return new StationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        StationEntity station = stationsListFiltered.get(position);
        holder.itemView.tvStationName.setText(station.getStationName());
        holder.itemView.tvStationCode.setText(station.getId());

        holder.itemView.toggleFavourite.setChecked(station.isFavourite());
        holder.itemView.toggleFavourite.setOnClickListener(view -> {
            if (listener != null){
                StationEntity st = stationsListFiltered.get(holder.getAdapterPosition());
                listener.onAddToFavClicked(st, !st.isFavourite());
            }
        });

        holder.itemView.parentView.setOnClickListener(view -> {
            if (listener != null)
                listener.onItemClicked(stationsListFiltered.get(holder.getAdapterPosition()));
        });
    }


    @Override
    public int getItemCount() {
        if(stationsListFiltered != null) return stationsListFiltered.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                if (stationsList == null || stationsList.isEmpty()) return null;
                isFilterMode = true;
                String charString = charSequence.toString().toLowerCase();

                if(TextUtils.isEmpty(charSequence)){
                    stationsListFiltered = stationsList;
                    isFilterMode = false;
                }else {
                    ArrayList<StationEntity> filteredList = new ArrayList<>();
                    for (StationEntity st: stationsList) {
                        if(st.getId().toLowerCase().contains(charString)
                                || st.getStationName().toLowerCase().contains(charString)){
                            filteredList.add(st);
                        }
                    }

                    stationsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = stationsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null) {
                    stationsListFiltered = (ArrayList<StationEntity>) filterResults.values;
                    notifyDataSetChanged();
                }
            }
        };
    }

    static class StationViewHolder extends RecyclerView.ViewHolder {
        ListItemStationBinding itemView;

        public StationViewHolder(@NonNull ListItemStationBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
