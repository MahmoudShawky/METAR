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
import eg.mahmoudShawky.metar.utils.Consts;
import eg.mahmoudShawky.metar.workers.UpdateWork;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkManager workManager = WorkManager.getInstance(this);

        Constraints updateConstraint = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest updateRequest =
                new PeriodicWorkRequest.Builder(UpdateWork.class, Consts.UPDATE_INTERVAL_IN_MIN, TimeUnit.MINUTES)
                        .setConstraints(updateConstraint)
                        .build();

        workManager.enqueue(updateRequest);
    }

}