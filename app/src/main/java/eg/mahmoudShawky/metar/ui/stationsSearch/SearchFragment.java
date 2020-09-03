package eg.mahmoudShawky.metar.ui.stationsSearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.hilt.android.AndroidEntryPoint;
import eg.mahmoudShawky.metar.R;
import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;
import eg.mahmoudShawky.metar.databinding.FragmentSearchBinding;
import eg.mahmoudShawky.metar.ui.adapters.StationListener;
import eg.mahmoudShawky.metar.ui.adapters.StationsAdapter;
import eg.mahmoudShawky.metar.ui.base.BaseFragment;
import eg.mahmoudShawky.metar.utils.NetworkStatus;

@AndroidEntryPoint
public class SearchFragment extends BaseFragment implements SearchView.OnQueryTextListener {

    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;
    private StationsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getAllStations();
    }

    @Override
    protected void initViewModel() {
        viewModel = getViewModelProvider()
                .get(SearchViewModel.class);
    }

    @Override
    protected void initUi() {
        adapter = new StationsAdapter();
        adapter.addListener(new StationListener() {
            @Override
            public void onItemClicked(StationEntity station) {
                openDetails(station);
            }

            @Override
            public void onAddToFavClicked(StationEntity station, boolean isAdd) {
                viewModel.setFavouriteStation(station, isAdd);

            }
        });
        binding.rvStations.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvStations.setAdapter(adapter);

        binding.placeHolder.btnRetry.setOnClickListener(view -> viewModel.loadData(false));

        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.loadData(true));
    }

    @Override
    protected void initObservers() {
        viewModel.getNetworkStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status) {
                case NetworkStatus.RUNNING:
                    showLoading(true);
                    break;

                case NetworkStatus.SUCCESS:
                    showLoading(false);
                    break;

                case NetworkStatus.REFRESHING:
                    binding.swipeRefreshLayout.setVisibility(View.VISIBLE);
                    binding.swipeRefreshLayout.setRefreshing(true);
                    break;

                case NetworkStatus.SUCCESS_NO_DATA:
                    showStopperError(getString(R.string.no_data_found));
                    break;

                case NetworkStatus.FAILED:
                    showStopperError(getString(R.string.some_error_occured));
                    break;
            }
        });

        viewModel.getStations().observe(getViewLifecycleOwner(), stationEntities -> {
            if (stationEntities == null || stationEntities.isEmpty()) return;
            if (binding.swipeRefreshLayout.getVisibility() != View.VISIBLE) {
                binding.placeHolder.mainLayout.setVisibility(View.GONE);
                binding.swipeRefreshLayout.setVisibility(View.VISIBLE);
            }
            adapter.update(stationEntities);
        });

        viewModel.getErrorId().observe(getViewLifecycleOwner(), this::showError);
        viewModel.getErrorText().observe(getViewLifecycleOwner(), this::showError);
    }

    @Override
    protected void clearBinding() {
        binding = null;
    }


    private void openDetails(StationEntity station) {
        SearchFragmentDirections.ActionSearchFragmentToMetarDetailsFragment action =
                SearchFragmentDirections.actionSearchFragmentToMetarDetailsFragment(station.getId());

        NavHostFragment.findNavController(SearchFragment.this)
                .navigate(action);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        if (getActivity() != null) {
            SearchView searchView = new SearchView(getActivity());
            searchView.setQueryHint(getString(R.string.search_hint));
            item.setActionView(searchView);
            searchView.setOnQueryTextListener(this);
        }
    }

    private void showLoading(boolean isShow) {
        if (isShow) {
            binding.swipeRefreshLayout.setVisibility(View.GONE);
            binding.placeHolder.mainLayout.setVisibility(View.VISIBLE);
            binding.placeHolder.centerLoading.setVisibility(View.VISIBLE);
            binding.placeHolder.errorLayout.setVisibility(View.GONE);
        } else {
            binding.placeHolder.centerLoading.setVisibility(View.GONE);
            binding.placeHolder.mainLayout.setVisibility(View.GONE);
            binding.swipeRefreshLayout.setVisibility(View.VISIBLE);
            binding.swipeRefreshLayout.setRefreshing(false);
        }
    }


    private void showStopperError(String errorMsg) {
        binding.swipeRefreshLayout.setVisibility(View.GONE);
        binding.placeHolder.mainLayout.setVisibility(View.VISIBLE);
        binding.placeHolder.centerLoading.setVisibility(View.GONE);
        binding.placeHolder.errorLayout.setVisibility(View.VISIBLE);
        binding.placeHolder.tvErrorMsg.setText(errorMsg);
    }

    private void hideStopperError() {
        binding.placeHolder.mainLayout.setVisibility(View.VISIBLE);
        binding.placeHolder.errorLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }
}