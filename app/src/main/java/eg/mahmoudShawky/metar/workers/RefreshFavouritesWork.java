package eg.mahmoudShawky.metar.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.util.List;

import eg.mahmoudShawky.metar.data.Repository;
import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;
import eg.mahmoudShawky.metar.utils.AppLogger;

public class RefreshFavouritesWork extends Worker {

    public static final String TAG = "UpdateWork";
    private final Repository repository;

    public RefreshFavouritesWork(@NonNull Context context, @NonNull WorkerParameters workerParams, Repository repository) {
        super(context, workerParams);
        this.repository = repository;
    }


    @NonNull
    @Override
    public Result doWork() {
        updateFavouriteStations();
        if (updateFavouriteStations()) {
            return Result.success();
        } else {
            return Result.failure();
        }
    }

    private boolean updateFavouriteStations() {
        List<StationEntity> favStations = repository.getFavouriteStations();
        for (StationEntity stations : favStations) {
            try {
                String decodedData = repository.getStationsDecodedFile(stations.getId());
                if (decodedData != null && !decodedData.isEmpty()) {
                    repository.updateDecodedData(stations.getId(), decodedData, System.currentTimeMillis());
                }
            } catch (IOException e) {
                AppLogger.getInstance().e(TAG, e.getMessage());
                return false;
            }
        }

        return true;
    }
}
