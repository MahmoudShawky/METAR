package eg.mahmoudShawky.metar.di;

import android.content.Context;
import android.util.Log;

import androidx.work.Configuration;
import androidx.work.WorkManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import eg.mahmoudShawky.metar.data.Repository;
import eg.mahmoudShawky.metar.data.RepositoryImp;
import eg.mahmoudShawky.metar.data.local.db.DbHelper;
import eg.mahmoudShawky.metar.data.local.db.DbHelperImp;
import eg.mahmoudShawky.metar.data.local.db.MetarDatabase;
import eg.mahmoudShawky.metar.data.remote.RemoteRepo;
import eg.mahmoudShawky.metar.data.remote.RemoteRepoImp;
import eg.mahmoudShawky.metar.utils.ContextUtils;
import eg.mahmoudShawky.metar.workers.MetarWorkerFactory;

@InstallIn(ApplicationComponent.class)
@Module
public class AppModule {

    @Provides
    @Singleton
    public static ContextUtils provideMetarDb(@ApplicationContext Context context) {
        return new ContextUtils(context);
    }

    @Provides
    @Singleton
    public static RemoteRepo provideRemoteRepo(ContextUtils contextUtils) {
        return new RemoteRepoImp(contextUtils);
    }

    @Provides
    @Singleton
    public static DbHelper provideDbHelper(MetarDatabase db) {
        return new DbHelperImp(db);
    }

    @Provides
    @Singleton
    public static Repository provideRepository(RemoteRepo remoteRepo, DbHelper dbHelper) {
        return new RepositoryImp(remoteRepo, dbHelper);
    }

    @Provides
    @Singleton
    public static MetarWorkerFactory provideWorkerFactory(Repository repository) {
        return new MetarWorkerFactory(repository);
    }

    @Provides
    @Singleton
    public static Configuration provideWorkManagerConfiguration(MetarWorkerFactory factory) {
        return new Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setWorkerFactory(factory)
                .build();
    }

    @Provides
    @Singleton
    public static WorkManager provideWorkManager(@ApplicationContext Context context) {
        return WorkManager.getInstance(context);
    }


/*    @Provides
    public static MetarDetailsViewModel provideDetailsViewModel(RemoteRepo remoteRepo, String id) {
        return new RepositoryImp(remoteRepo, dbHelper);
    }*/


}
