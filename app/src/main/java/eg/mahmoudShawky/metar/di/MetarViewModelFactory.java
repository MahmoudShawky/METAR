package eg.mahmoudShawky.metar.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import eg.mahmoudShawky.metar.data.Repository;
import eg.mahmoudShawky.metar.ui.metarDetails.MetarDetailsViewModel;

public class MetarViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String id;
    private Repository repository;

    public MetarViewModelFactory(@NonNull Repository repository,@NonNull String id) {
        this.id = id;
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == MetarDetailsViewModel.class){
            return (T) new MetarDetailsViewModel(repository, id);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }

}
