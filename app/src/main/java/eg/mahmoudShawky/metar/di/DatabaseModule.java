package eg.mahmoudShawky.metar.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import eg.mahmoudShawky.metar.data.local.db.MetarDatabase;
import eg.mahmoudShawky.metar.data.local.db.dao.StationDAO;

/***
 * @author mahmoud.shawky
 *
 * Hint Database Module to provide the Database and DAOs
 * see https://developer.android.com/training/dependency-injection/hilt-android
 */
@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {
    @Provides
    @Singleton
    public static MetarDatabase provideMetarDb(@ApplicationContext Context context) {
        return MetarDatabase.getDatabase(context);
    }


    @Provides
    @Singleton
    public static StationDAO provideStationDAO(@ApplicationContext Context context) {
        return MetarDatabase.getDatabase(context).stationDAO();
    }
}
