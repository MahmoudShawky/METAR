package eg.mahmoudShawky.metar.data.local.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

/***
 * @author mahmoud.shawky
 * Data Access Object for {@link StationEntity} class
 */
@Dao
public interface StationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StationEntity stationEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArrayList<StationEntity> stations);

    @Update
    void update(StationEntity stationEntity);

    @Query("SELECT* FROM stations_table ORDER BY id ASC")
    LiveData<List<StationEntity>> getAllStations();

    @Query("SELECT* FROM stations_table WHERE isFavourite = 1 ORDER BY id ASC")
    LiveData<List<StationEntity>> getFavouriteStationsLiveData();

    @Query("SELECT* FROM stations_table WHERE isFavourite = 1 ORDER BY id ASC")
    List<StationEntity> getFavouriteStations();

    @Query("SELECT* FROM stations_table WHERE id = :id")
    LiveData<StationEntity> getStation(String id);

    @Query("SELECT* FROM stations_table WHERE id LIKE :filter OR stationName LIKE :filter ORDER BY id ASC")
    LiveData<List<StationEntity>> searchForStations(String filter);

    @Query("SELECT COUNT(*) FROM stations_table line ")
    int getRawCount();

    @Query("UPDATE stations_table SET decodedData = :data, lastUpdateTime= :updateTime WHERE id = :id")
    void updateDecodedData(String id, String data, Long updateTime);

/*    @Query("SELECT lastUpdateTime FROM stations_table WHERE id = :id")
    LiveData<Long> getLastUpdateTime(String id);*/
}
