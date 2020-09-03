package eg.mahmoudShawky.metar.ui.stationsSearch;

import android.util.Pair;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.android.scopes.FragmentScoped;
import eg.mahmoudShawky.metar.R;
import eg.mahmoudShawky.metar.data.Repository;
import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;
import eg.mahmoudShawky.metar.ui.base.BaseViewModel;
import eg.mahmoudShawky.metar.utils.AppLogger;
import eg.mahmoudShawky.metar.utils.Consts;
import eg.mahmoudShawky.metar.utils.concurrent.SimpleTask;

import static eg.mahmoudShawky.metar.utils.NetworkStatus.FAILED;
import static eg.mahmoudShawky.metar.utils.NetworkStatus.REFRESHING;
import static eg.mahmoudShawky.metar.utils.NetworkStatus.RUNNING;
import static eg.mahmoudShawky.metar.utils.NetworkStatus.SUCCESS;
import static eg.mahmoudShawky.metar.utils.NetworkStatus.SUCCESS_NO_DATA;

@ActivityScoped
public class SearchViewModel extends BaseViewModel {

    public static final String TAG = "SearchViewModel";

    private LiveData<List<StationEntity>> stations;

    public LiveData<List<StationEntity>> getStations() {
        return stations;
    }

    @ViewModelInject
    public SearchViewModel(Repository repository) {
        super(repository);
        stations = repository.getAllStations();
        loadData(false);
    }

    public void loadData(boolean isRefresh) {
        if (isRefresh) networkStatus.setValue(REFRESHING);
        else networkStatus.setValue(RUNNING);
        getStationsFromRemote();
    }

    private void getStationsFromRemote() {
        SimpleTask.run(() -> {
            try {
                List<Pair<String, String>> stationsList = repository.getStationsCodes(Consts.GERMANY_STATIONS_PREFIX);
                CountDownLatch latch = new CountDownLatch(stationsList.size());

                //in case of no files that mean no data,
                //in case of number of files equal to number of rows in db so we don't need to update
                if (stationsList.size() == 0) {
                    networkStatus.postValue(SUCCESS_NO_DATA);
                    return;
                } else if (stationsList.size() == repository.getRowCount()) {
                    networkStatus.postValue(SUCCESS);
                    return;
                }

                //download all decoded files and update airport name and decoded data
                //we can update one by one or all
                //ArrayList<StationEntity> stationEntities = new ArrayList<>(stationsList.size());
                for (Pair<String, String> locationNamePair : stationsList) {
                    SimpleTask.run(() -> {
                        try {
                            String decodedData = repository.getStationsDecodedFile(locationNamePair.second);
                            insertNewStation(locationNamePair.second, decodedData);
                            //stationEntities.add(new StationEntity(locationNamePair.second, locationName, System.currentTimeMillis()));
                        } catch (IOException e) {
                            AppLogger.getInstance().e(TAG, e.getMessage());
                            errorText.postValue(e.getMessage());
                        } finally {
                            latch.countDown();
                        }
                    });
                }
                latch.await();
                networkStatus.postValue(SUCCESS);
            } catch (InterruptedException | IOException e) {
                AppLogger.getInstance().e(TAG, e.getMessage());
                if (repository.getRowCount() > 0) {
                    errorId.postValue(R.string.no_active_connection);
                    networkStatus.postValue(SUCCESS);
                } else {
                    networkStatus.postValue(FAILED);
                }
            }
        });
    }

    private void insertNewStation(String code, String decodedData) {
        if (decodedData != null && !decodedData.isEmpty()) {
            String firstLine = decodedData.split("\n")[0];
            String stationName;
            if (firstLine.contains(",")) {
                stationName = firstLine.substring(0, firstLine.indexOf(','));
            } else {
                stationName = firstLine;
            }

            StationEntity station = new StationEntity(code, stationName, System.currentTimeMillis());
            station.setDecodedData(decodedData);
            repository.insertStation(station);
        }
    }

    public void getAllStations() {
        stations = repository.getAllStations();
    }

    public void searchForStation(String filter) {
        networkStatus.postValue(REFRESHING);
        String finalFilter = "%" + filter;
        SimpleTask.run(() -> {
            stations = repository.searchForStations(finalFilter);
        });
    }

    public void setFavouriteStation(StationEntity station, boolean isFavourite) {
        station.setFavourite(isFavourite);
        SimpleTask.run(() -> repository.updateStation(station));
    }

}
