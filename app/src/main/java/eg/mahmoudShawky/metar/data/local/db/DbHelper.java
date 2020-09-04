package eg.mahmoudShawky.metar.data.local.db;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;

public interface DbHelper {
    void insertStation(StationEntity stationEntity);

    void insertStations(ArrayList<StationEntity> stations);

    void updateStation(StationEntity stationEntity);

    LiveData<List<StationEntity>> getAllStations();

    LiveData<List<StationEntity>> getFavouriteStationsLiveData();

    List<StationEntity> getFavouriteStations();

    LiveData<StationEntity> getStation(String id);

    LiveData<List<StationEntity>> searchForStations(String filter);

    int getRowCount();

    void updateDecodedData(String id, String data, Long updateTime);
}
