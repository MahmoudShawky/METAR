package eg.mahmoudShawky.metar.ui.base;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import eg.mahmoudShawky.metar.R;

public abstract class BaseFragment extends Fragment {

    protected NavBackStackEntry backStackEntry;
    protected ViewModelProvider viewModelProvider;
    private NavController navController;


    public void setBackStackEntry(@IdRes int destinationId) {
        this.backStackEntry = NavHostFragment.findNavController(this).getBackStackEntry(destinationId);
    }

    public void setViewModelProvider(ViewModelProvider viewModelProvider) {
        this.viewModelProvider = viewModelProvider;
    }

    public NavController getNavController() {
        return navController;
    }

    public ViewModelProvider getViewModelProvider() {
        return viewModelProvider;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        initUi();
        initViewModeProvider();
        initViewModel();
        initObservers();
    }

    protected void setBackEnabled(boolean enable) {
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(enable);
                if (enable) {
                    // This callback will only be called when MyFragment is at least Started.
                    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
                        @Override
                        public void handleOnBackPressed() {
                            doBack();
                        }
                    };
                    requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
                }

            }
        }
    }

    private void doBack() {
        navController.popBackStack();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            doBack();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected abstract void initViewModel();

    //default initialization for viewModelProvider
    protected void initViewModeProvider() {
        setBackStackEntry(R.id.main_navigation);
        viewModelProvider = new ViewModelProvider(
                backStackEntry,
                getDefaultViewModelProviderFactory()
        );
    }

    protected abstract void initUi();

    protected abstract void initObservers();

    protected abstract void clearBinding();

    public void showError(@StringRes int strId) {
        showError(getString(strId));
    }

    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearBinding();
    }
}
