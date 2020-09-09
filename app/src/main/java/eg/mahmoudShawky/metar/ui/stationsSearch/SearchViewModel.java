package eg.mahmoudShawky.metar.ui.stationsSearch;

import android.util.Pair;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import dagger.hilt.android.scopes.FragmentScoped;
import eg.mahmoudShawky.metar.R;
import eg.mahmoudShawky.metar.data.Repository;
import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;
import eg.mahmoudShawky.metar.ui.base.BaseViewModel;
import eg.mahmoudShawky.metar.utils.AppLogger;
import eg.mahmoudShawky.metar.utils.Constants;
import eg.mahmoudShawky.metar.utils.concurrent.SimpleAsyncTask;

import static eg.mahmoudShawky.metar.utils.NetworkStatus.FAILED;
import static eg.mahmoudShawky.metar.utils.NetworkStatus.REFRESHING;
import static eg.mahmoudShawky.metar.utils.NetworkStatus.RUNNING;
import static eg.mahmoudShawky.metar.utils.NetworkStatus.SUCCESS;
import static eg.mahmoudShawky.metar.utils.NetworkStatus.SUCCESS_NO_DATA;

@FragmentScoped
public class SearchViewModel extends BaseViewModel {

    public static final String TAG = "SearchViewModel";

    private LiveData<List<StationEntity>> stations;
    private boolean isFailed;
    private List<Pair<String, String>> remoteStationsList;

    public LiveData<List<StationEntity>> getStations() {
        return stations;
    }

    @ViewModelInject
    public SearchViewModel(Repository repository) {
        super(repository);
        getAllStations();
    }

    //Load data (show Refreshing indication if data has retrieved before)
    public void loadData(boolean isRefresh) {
        if (isRefresh) networkStatus.setValue(REFRESHING);
        else networkStatus.setValue(RUNNING);
        getStationsFromRemote();
    }

    private void getStationsFromRemote() {
        SimpleAsyncTask.run(() -> {
            try {
                if (remoteStationsList == null || remoteStationsList.isEmpty()) {
                    remoteStationsList = repository.getStationsCodes(Constants.GERMANY_STATIONS_PREFIX);
                }
                CountDownLatch latch = new CountDownLatch(remoteStationsList.size());

                //in case of no files that mean no data,
                //in case of number of files equal to number of rows in db so we don't need to update
                //in case of #stations in db less than the total #stations in remote display Refreshing (this case happens when the load interrupted)
                int rowsCount = repository.getRowCount();
                if (remoteStationsList.size() == 0) {
                    networkStatus.postValue(SUCCESS_NO_DATA);
                    return;
                } else if (remoteStationsList.size() == rowsCount) {
                    networkStatus.postValue(SUCCESS);
                    return;
                } else if (remoteStationsList.size() > rowsCount) {
                    networkStatus.postValue(REFRESHING);
                }

                //to save the favourite state of the stations
                // we can use {@androidx.room.OnConflictStrategy.REPLACE} but we need to update the decoded data
                List<StationEntity> favList = repository.getFavouriteStations();

                //download all decoded files and update airport name and decoded data
                //we can update one by one or all
                //ArrayList<StationEntity> stationEntities = new ArrayList<>(stationsList.size());
                for (Pair<String, String> locationNamePair : remoteStationsList) {
                    SimpleAsyncTask.run(() -> {
                        try {
                            String decodedData = repository.getStationsDecodedFile(locationNamePair.second);
                            insertNewStation(locationNamePair.second, decodedData, favList);
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
            } catch (InterruptedException e) {
                AppLogger.getInstance().e(TAG, e.getMessage());
                errorText.postValue(e.getMessage());
                //networkStatus.postValue(FAILED);
            } catch (IOException e) {
                AppLogger.getInstance().e(TAG, e.getMessage());
                errorId.postValue(R.string.no_active_connection);
            } finally {
                //In case of there ara any stations we can consider it as a success and show the result
                if (repository.getRowCount() > 0) {
                    networkStatus.postValue(SUCCESS);
                } else {
                    networkStatus.postValue(FAILED);
                }
            }
        });
    }

    void insertNewStation(String code, String decodedData, List<StationEntity> favList) {
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
            station.setFavourite(favList.contains(station));
            repository.insertStation(station);
        }
    }

    public void getAllStations() {
        stations = repository.getAllStations();
    }


    public void setFavouriteStation(StationEntity station, boolean isFavourite) {
        station.setFavourite(isFavourite);
        SimpleAsyncTask.run(() -> repository.updateStation(station));
    }

}
