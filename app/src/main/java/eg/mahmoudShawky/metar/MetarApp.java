package eg.mahmoudShawky.metar;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.work.Configuration;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MetarApp extends Application implements Configuration.Provider {

    @Inject
    Configuration workConfig;

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return workConfig;
    }
}
