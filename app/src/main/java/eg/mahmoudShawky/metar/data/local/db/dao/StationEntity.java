package eg.mahmoudShawky.metar.data.local.db.dao;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

/***
 * @author mahmoud.shawky
 *
 * Represent a table of station data, id for Station Code, station name if available
 * decodedData and rawData can be retrieved and inserted
 * lastUpdateTime (system time im ms)to save last time that row has been updated
 * isFavourite to set the station to favourite list
 */
@Entity(tableName = "stations_table")
public class StationEntity {

    @PrimaryKey
    @NonNull
    private String id;
    private String stationName;
    private Long lastUpdateTime;
    private String rawData;
    private String decodedData;
    private boolean isFavourite;

    public StationEntity(@NotNull String id, String stationName, Long lastUpdateTime) {
        this.id = id;
        this.stationName = stationName;
        this.lastUpdateTime = lastUpdateTime;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getDecodedData() {
        return decodedData;
    }

    public void setDecodedData(String decodedData) {
        this.decodedData = decodedData;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
