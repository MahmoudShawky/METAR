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

@Module
@InstallIn(ApplicationComponent.class)
public class databaseModule {
    @Provides
    @Singleton
    public static MetarDatabase provideMetarDb(@ApplicationContext Context context){
        return MetarDatabase.getDatabase(context);
    }


    @Provides
    @Singleton
    public static StationDAO provideStationDAO(@ApplicationContext Context context){
        return MetarDatabase.getDatabase(context).stationDAO();
    }
}
