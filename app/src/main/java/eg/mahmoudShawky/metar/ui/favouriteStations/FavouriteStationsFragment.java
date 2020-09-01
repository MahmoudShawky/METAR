package eg.mahmoudShawky.metar.ui.favouriteStations;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eg.mahmoudShawky.metar.R;

public class FavouriteStationsFragment extends Fragment {

    private FavouriteStationsViewModel mViewModel;

    public static FavouriteStationsFragment newInstance() {
        return new FavouriteStationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favourite_stations_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FavouriteStationsViewModel.class);
        // TODO: Use the ViewModel
    }

}