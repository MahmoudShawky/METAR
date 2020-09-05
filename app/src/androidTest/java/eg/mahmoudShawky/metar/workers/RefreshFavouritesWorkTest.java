package eg.mahmoudShawky.metar.workers;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.work.Configuration;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.testing.SynchronousExecutor;
import androidx.work.testing.WorkManagerTestInitHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/***
 * @author mahmoud.shawky
 * see https://developer.android.com/topic/libraries/architecture/workmanager/how-to/integration-testing
 */
@RunWith(AndroidJUnit4.class)
public class RefreshFavouritesWorkTest {
    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        Configuration config = new Configuration.Builder()
                // Set log level to Log.DEBUG to
                // make it easier to see why tests failed
                .setMinimumLoggingLevel(Log.DEBUG)
                // Use a SynchronousExecutor to make it easier to write tests
                .setExecutor(new SynchronousExecutor())
                .build();

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(
                context, config);


    }


    @Test
    public void RefreshFavouritesWorkNoFavourites() throws Exception {
        // Create request
        OneTimeWorkRequest request =
                new OneTimeWorkRequest.Builder(RefreshFavouritesWork.class)
                        .build();

        WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        // Enqueue and wait for result. This also runs the Worker synchronously
        // because we are using a SynchronousExecutor.
        workManager.enqueue(request).getResult().get();
        // Get WorkInfo
        WorkInfo workInfo = workManager.getWorkInfoById(request.getId()).get();
        // Assert
        assertThat(workInfo.getState(), is(WorkInfo.State.SUCCEEDED));
    }

}