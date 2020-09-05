package eg.mahmoudShawky.metar.data;

import android.util.Pair;

import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import eg.mahmoudShawky.metar.data.local.db.DbHelper;
import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;
import eg.mahmoudShawky.metar.data.remote.RemoteRepo;

/***
 * @author mahmoud.shawky
 *
 * The implementation of {@link Repository}
 */
@Singleton
public class RepositoryImp implements Repository {

    private final RemoteRepo remoteRepo;
    private final DbHelper dbHelper;

    @Inject
    public RepositoryImp(RemoteRepo remoteRepo, DbHelper dbHelper) {
        this.remoteRepo = remoteRepo;
        this.dbHelper = dbHelper;
    }

    @Override
    public void insertStation(StationEntity stationEntity) {
        dbHelper.insertStation(stationEntity);
    }

    @Override
    public void insertStations(ArrayList<StationEntity> stations) {
        dbHelper.insertStations(stations);
    }

    @Override
    public void updateStation(StationEntity stationEntity) {
        dbHelper.updateStation(stationEntity);
    }

    @Override
    public LiveData<List<StationEntity>> getAllStations() {
        return dbHelper.getAllStations();
    }

    @Override
    public LiveData<List<StationEntity>> getFavouriteStationsLiveData() {
        return dbHelper.getFavouriteStationsLiveData();
    }

    @Override
    public List<StationEntity> getFavouriteStations() {
        return dbHelper.getFavouriteStations();
    }

    @Override
    public LiveData<StationEntity> getStation(String id) {
        return dbHelper.getStation(id);
    }

    @Override
    public LiveData<List<StationEntity>> searchForStations(String filter) {
        return dbHelper.searchForStations(filter);
    }

    @Override
    public int getRowCount() {
        return dbHelper.getRowCount();
    }

    @Override
    public void updateDecodedData(String id, String data, Long updateTime) {
        dbHelper.updateDecodedData(id, data, updateTime);
    }

    @Override
    public List<Pair<String, String>> getStationsCodes(String nameFilter) throws IOException {
        return remoteRepo.getStationsCodes(nameFilter);
    }

    @Override
    public String getStationsDecodedFile(String stationCode) throws IOException {
        return remoteRepo.getStationsDecodedFile(stationCode);
    }
}
