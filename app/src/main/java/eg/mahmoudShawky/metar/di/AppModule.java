package eg.mahmoudShawky.metar.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import eg.mahmoudShawky.metar.data.Repository;
import eg.mahmoudShawky.metar.data.RepositoryImp;
import eg.mahmoudShawky.metar.data.local.db.DbHelper;
import eg.mahmoudShawky.metar.data.local.db.DbHelperImp;
import eg.mahmoudShawky.metar.data.local.db.MetarDatabase;
import eg.mahmoudShawky.metar.data.local.db.dao.StationDAO;
import eg.mahmoudShawky.metar.data.remote.RemoteRepo;
import eg.mahmoudShawky.metar.data.remote.RemoteRepoImp;

@InstallIn(ApplicationComponent.class)
@Module
public class ApplicationModule {

    @Provides
    public static StationDAO provideStationDAO(@ApplicationContext Context context){
        return MetarDatabase.getDatabase(context).stationDAO();
    }

    @Provides
    @Singleton
    public static MetarDatabase provideMetarDb(@ApplicationContext Context context){
        return MetarDatabase.getDatabase(context);
    }

    @Singleton
    public static RemoteRepo provideRemoteRepo() {
        return new RemoteRepoImp();
    }

    @Singleton
    public static DbHelper provideDbHelper(MetarDatabase db) {
        return new DbHelperImp(db);
    }

    @Singleton
    public static Repository provideRepository(RemoteRepo remoteRepo, DbHelper dbHelper) {
        return new RepositoryImp(remoteRepo, dbHelper);
    }
}
