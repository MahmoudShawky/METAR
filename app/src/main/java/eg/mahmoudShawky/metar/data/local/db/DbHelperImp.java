package eg.mahmoudShawky.metar.data.local.db;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;

@Singleton
public class DbHelperImp implements DbHelper {

    private final MetarDatabase metarDatabase;

    @Inject
    public DbHelperImp(MetarDatabase metarDatabase) {
        this.metarDatabase = metarDatabase;
    }

    @Override
    public void insertStation(StationEntity stationEntity) {
        metarDatabase.stationDAO().insert(stationEntity);
    }

    @Override
    public void insertStations(ArrayList<StationEntity> stations) {
        metarDatabase.stationDAO().insert(stations);
    }

    @Override
    public void updateStation(StationEntity stationEntity) {
        metarDatabase.stationDAO().update(stationEntity);
    }

    @Override
    public LiveData<List<StationEntity>> getAllStations() {
        return metarDatabase.stationDAO().getAllStations();
    }

    @Override
    public LiveData<List<StationEntity>> getFavouriteStationsLiveData() {
        return metarDatabase.stationDAO().getFavouriteStationsLiveData();
    }

    @Override
    public List<StationEntity> getFavouriteStations() {
        return metarDatabase.stationDAO().getFavouriteStations();
    }

    @Override
    public LiveData<StationEntity> getStation(String id) {
        return metarDatabase.stationDAO().getStation(id);
    }

    @Override
    public LiveData<List<StationEntity>> searchForStations(String filter) {
        return metarDatabase.stationDAO().searchForStations(filter);
    }

    @Override
    public int getRowCount() {
        return metarDatabase.stationDAO().getRawCount();
    }

    @Override
    public void updateDecodedData(String id, String data, Long updateTime) {
        metarDatabase.stationDAO().updateDecodedData(id, data, updateTime);
    }
}
