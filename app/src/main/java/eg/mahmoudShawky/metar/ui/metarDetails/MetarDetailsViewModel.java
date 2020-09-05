package eg.mahmoudShawky.metar.ui.metarDetails;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import eg.mahmoudShawky.metar.data.Repository;
import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;
import eg.mahmoudShawky.metar.ui.base.BaseViewModel;
import eg.mahmoudShawky.metar.utils.AppLogger;
import eg.mahmoudShawky.metar.utils.concurrent.SimpleTask;

import static eg.mahmoudShawky.metar.utils.NetworkStatus.FAILED;
import static eg.mahmoudShawky.metar.utils.NetworkStatus.REFRESHING;
import static eg.mahmoudShawky.metar.utils.NetworkStatus.SUCCESS;

public class MetarDetailsViewModel extends BaseViewModel {

    private static final String TAG = "MetarDetailsViewModel";
    LiveData<StationEntity> station;
    LiveData<ArrayList<Pair<String, String>>> pairList;
    private String id;

    //@ViewModelInject
    public MetarDetailsViewModel(Repository repository, @NonNull String id) {
        super(repository);
        AppLogger.getInstance().i(TAG, "Id: " + id);
        this.id = id;

        station = repository.getStation(id);
        pairList = Transformations.map(station, this::getPairs);
    }

    @NotNull
    ArrayList<Pair<String, String>> getPairs(StationEntity stationEntity) {
        if (stationEntity == null || stationEntity.getDecodedData() == null)
            return new ArrayList<>();
        String[] lines = stationEntity.getDecodedData().split("\n");
        ArrayList<Pair<String, String>> pairs = new ArrayList<>(lines.length);
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] == null || lines[i].isEmpty()) break;
            if (i < 2) {
                pairs.add(new Pair<>(lines[i], ""));
            } else {
                int colIndex = lines[i].indexOf(":");
                String f = lines[i].substring(0, colIndex+1);
                String s = lines[i].substring(colIndex + 1);
                pairs.add(new Pair<>(f, s));
            }
        }

        return pairs;
    }

    public void getStation() {
        networkStatus.setValue(REFRESHING);
        SimpleTask.run(() -> {
            try {
                String decodedFile = repository.getStationsDecodedFile(id);
                repository.updateDecodedData(id, decodedFile, System.currentTimeMillis());
                networkStatus.postValue(SUCCESS);
            } catch (IOException e) {
                errorText.postValue(e.getMessage());
                networkStatus.postValue(FAILED);
            }
        });
    }


}