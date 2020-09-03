package eg.mahmoudShawky.metar.ui.favouriteStations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.hilt.android.AndroidEntryPoint;
import eg.mahmoudShawky.metar.R;
import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;
import eg.mahmoudShawky.metar.databinding.FragmentFavouriteStationsBinding;
import eg.mahmoudShawky.metar.ui.adapters.StationListener;
import eg.mahmoudShawky.metar.ui.adapters.StationsAdapter;
import eg.mahmoudShawky.metar.ui.base.BaseFragment;

@AndroidEntryPoint
public class FavouriteStationsFragment extends BaseFragment {

    private FragmentFavouriteStationsBinding binding;
    private FavouriteStationsViewModel viewModel;
    private StationsAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFavouriteStationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    protected void initViewModel() {
        viewModel = getViewModelProvider()
                .get(FavouriteStationsViewModel.class);
    }

    @Override
    protected void initUi() {
        binding.ivAdd.setOnClickListener(view -> openSearchFragment());
        adapter = new StationsAdapter();
        adapter.addListener(new StationListener() {
            @Override
            public void onItemClicked(StationEntity station) {
                FavouriteStationsFragmentDirections.ActionFavouriteStationsFragmentToMetarDetailsFragment action
                        = FavouriteStationsFragmentDirections.actionFavouriteStationsFragmentToMetarDetailsFragment(station.getId());
                NavHostFragment.findNavController(FavouriteStationsFragment.this)
                        .navigate(action);
            }

            @Override
            public void onAddToFavClicked(StationEntity station, boolean isAdd) {
                viewModel.setFavouriteStation(station, isAdd);

            }
        });
        binding.rvStations.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvStations.setAdapter(adapter);
    }

    @Override
    protected void initObservers() {

        viewModel.getStations().observe(getViewLifecycleOwner(), stationEntities -> {
            if (stationEntities == null || stationEntities.isEmpty()){
               showEmptyPlaceHolder(true);
            }else {
                showEmptyPlaceHolder(false);
                adapter.update(stationEntities);
            }
        });
    }

    private void showEmptyPlaceHolder(boolean isShow) {
        if(isShow){
            binding.emptyPlaceHolder.setVisibility(View.VISIBLE);
            binding.mainLayout.setVisibility(View.GONE);
        }else {
            binding.emptyPlaceHolder.setVisibility(View.GONE);
            binding.mainLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            openSearchFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSearchFragment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_favouriteStationsFragment_to_searchFragment);
    }

    @Override
    protected void clearBinding() {
        binding = null;
    }
}