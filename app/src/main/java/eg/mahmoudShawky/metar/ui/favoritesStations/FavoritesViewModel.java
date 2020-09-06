package eg.mahmoudShawky.metar.ui.favoritesStations;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;

import java.util.List;

import dagger.hilt.android.scopes.ActivityScoped;
import eg.mahmoudShawky.metar.data.Repository;
import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;
import eg.mahmoudShawky.metar.ui.base.BaseViewModel;
import eg.mahmoudShawky.metar.utils.concurrent.SimpleAsyncTask;

@ActivityScoped
public class FavoritesViewModel extends BaseViewModel {

    private LiveData<List<StationEntity>> stations;

    public LiveData<List<StationEntity>> getStations() {
        return stations;
    }

    @ViewModelInject
    public FavoritesViewModel(Repository repository) {
        super(repository);
        stations = repository.getFavouriteStationsLiveData();
    }

    public void setFavouriteStation(StationEntity station, boolean isFavourite) {
        station.setFavourite(isFavourite);
        SimpleAsyncTask.run(() -> repository.updateStation(station));
    }
}