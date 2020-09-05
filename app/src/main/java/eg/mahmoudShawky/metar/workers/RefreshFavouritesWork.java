package eg.mahmoudShawky.metar.workers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.ForegroundInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.util.List;

import eg.mahmoudShawky.metar.R;
import eg.mahmoudShawky.metar.data.Repository;
import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;
import eg.mahmoudShawky.metar.utils.AppLogger;

import static android.content.Context.NOTIFICATION_SERVICE;

/***
 * @author mahmoud.shawky
 *
 * Worker to update decoded data of favourite stationa
 * see https://developer.android.com/topic/libraries/architecture/workmanager/advanced/long-running
 */
public class RefreshFavouritesWork extends Worker {

    public static final String TAG = "UpdateWork";
    public static final int NOTIFICATION_ID = 1503;
    private final Repository repository;
    private final NotificationManager notificationManager;
    private String channelId = "METAR_UPDATE";

    public RefreshFavouritesWork(@NonNull Context context, @NonNull WorkerParameters workerParams, Repository repository) {
        super(context, workerParams);
        this.repository = repository;
        notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
    }


    @NonNull
    @Override
    public Result doWork() {
        setForegroundAsync(createForegroundInfo("progress"));
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

    @NonNull
    private ForegroundInfo createForegroundInfo(@NonNull String progress) {
        Context context = getApplicationContext();

        String title = context.getString(R.string.notification_title);
        String cancel = context.getString(R.string.cancel_download);
        // This PendingIntent can be used to cancel the worker
        PendingIntent intent = WorkManager.getInstance(context)
                .createCancelPendingIntent(getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setTicker(title)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, cancel, intent)
                .build();


        return new ForegroundInfo(NOTIFICATION_ID, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = notificationManager.getNotificationChannel(channelId);
        if (channel == null) {
            channel = new NotificationChannel(channelId, TAG, NotificationManager.IMPORTANCE_NONE);
        }
        channel.setLightColor(Color.BLUE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
    }
}
