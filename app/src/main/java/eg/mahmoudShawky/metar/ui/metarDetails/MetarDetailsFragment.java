package eg.mahmoudShawky.metar.ui.metarDetails;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import eg.mahmoudShawky.metar.R;
import eg.mahmoudShawky.metar.data.Repository;
import eg.mahmoudShawky.metar.databinding.FragmentMetarDetailsBinding;
import eg.mahmoudShawky.metar.di.MetarViewModelFactory;
import eg.mahmoudShawky.metar.ui.adapters.LabelValueAdapter;
import eg.mahmoudShawky.metar.ui.base.BaseFragment;

@AndroidEntryPoint
public class MetarDetailsFragment extends BaseFragment {

    private MetarDetailsViewModel viewModel;
    private FragmentMetarDetailsBinding binding;
    private LabelValueAdapter adapter;
    @Inject
    Repository repo;
    private MetarViewModelFactory factory;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMetarDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViewModeProvider() {
        setBackStackEntry(R.id.main_navigation);
        if (getArguments() != null) {
            String id = MetarDetailsFragmentArgs.fromBundle(getArguments()).getStationId();
            factory = new MetarViewModelFactory(repo, id);
        }
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this, factory).get(MetarDetailsViewModel.class);
        viewModel.getStation();
    }

    @Override
    protected void initUi() {
        setBackEnabled(true);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.getStation());
    }

    @Override
    protected void initObservers() {
        viewModel.pairList.observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            if (adapter == null) {
                adapter = new LabelValueAdapter(result);
                binding.rvPairs.setAdapter(adapter);
            } else {
                adapter.updatePairs(result);
            }
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        viewModel.station.observe(getViewLifecycleOwner(), stationEntity -> {
            if (stationEntity == null || stationEntity.getLastUpdateTime() == null) return;
            CharSequence updatedAt = DateUtils.getRelativeDateTimeString(requireContext(),
                    stationEntity.getLastUpdateTime(),
                    DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0);

            binding.tvUpdatedAt.setText(updatedAt);
        });

        viewModel.getErrorId().observe(getViewLifecycleOwner(), this::showError);
        viewModel.getErrorText().observe(getViewLifecycleOwner(), this::showError);
    }

    @Override
    protected void clearBinding() {
        binding = null;
    }

}