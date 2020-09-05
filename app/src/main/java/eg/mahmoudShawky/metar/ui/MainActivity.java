package eg.mahmoudShawky.metar.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;
import eg.mahmoudShawky.metar.R;
import eg.mahmoudShawky.metar.utils.Constants;
import eg.mahmoudShawky.metar.workers.RefreshFavouritesWork;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startPeriodicRefreshWork();
    }


    public void startPeriodicRefreshWork() {
        Constraints updateConstraint = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest updateRequest =
                new PeriodicWorkRequest.Builder(RefreshFavouritesWork.class,
                        Constants.UPDATE_INTERVAL_MIN, TimeUnit.MINUTES,
                        Constants.UPDATE_INTERVAL_MAX, TimeUnit.MINUTES)
                        .setConstraints(updateConstraint)
                        .build();

        WorkManager.getInstance(this).enqueue(updateRequest);
    }

}