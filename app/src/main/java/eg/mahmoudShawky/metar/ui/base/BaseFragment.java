package eg.mahmoudShawky.metar.ui.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import eg.mahmoudShawky.metar.R;

public abstract class BaseFragment extends Fragment {

    protected NavBackStackEntry backStackEntry;
    protected ViewModelProvider viewModelProvider;

    public void setBackStackEntry(@IdRes int destinationId) {
        this.backStackEntry = NavHostFragment.findNavController(this).getBackStackEntry(destinationId);
    }

    public void setViewModelProvider(ViewModelProvider viewModelProvider) {
        this.viewModelProvider = viewModelProvider;
    }

    public ViewModelProvider getViewModelProvider() {
        return viewModelProvider;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModeProvider();
        initViewModel();
        initUi();
        initObservers();
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
