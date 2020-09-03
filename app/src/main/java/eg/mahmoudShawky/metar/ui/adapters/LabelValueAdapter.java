package eg.mahmoudShawky.metar.ui.adapters;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eg.mahmoudShawky.metar.databinding.ListItemLabelValueBinding;

public class LabelValueAdapter extends RecyclerView.Adapter<LabelValueAdapter.LabelValueViewHolder> {

    private ArrayList<Pair<String, String>> labelValueList;

    public LabelValueAdapter(ArrayList<Pair<String, String>> labelValueList) {
        this.labelValueList = labelValueList;
    }

    public void updatePairs(ArrayList<Pair<String, String>> labelValueList) {
        this.labelValueList = labelValueList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public LabelValueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemLabelValueBinding binding = ListItemLabelValueBinding.inflate(inflater, parent, false);
        return new LabelValueViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LabelValueViewHolder holder, int position) {
        holder.itemView.tvLabel.setText(labelValueList.get(position).first);
        holder.itemView.tvValue.setText(labelValueList.get(position).second);
    }


    @Override
    public int getItemCount() {
        return labelValueList.size();
    }

    static class LabelValueViewHolder extends RecyclerView.ViewHolder {
        ListItemLabelValueBinding itemView;

        public LabelValueViewHolder(@NonNull ListItemLabelValueBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
