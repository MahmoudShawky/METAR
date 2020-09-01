package eg.mahmoudShawky.metar.ui.metarDetails;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eg.mahmoudShawky.metar.R;

public class MetarDetailsFragment extends Fragment {

    private MetarDetailsViewModel mViewModel;

    public static MetarDetailsFragment newInstance() {
        return new MetarDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.metar_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MetarDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}