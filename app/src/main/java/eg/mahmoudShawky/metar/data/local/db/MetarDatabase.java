package eg.mahmoudShawky.metar.data.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eg.mahmoudShawky.metar.data.local.db.dao.StationDAO;
import eg.mahmoudShawky.metar.data.local.db.dao.StationEntity;

@Database(entities = {StationEntity.class}, version = 1, exportSchema = false)
public abstract class MetarDatabase extends RoomDatabase {

    public abstract StationDAO stationDAO();

    private static volatile MetarDatabase INSTANCE;

    public static MetarDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MetarDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MetarDatabase.class, "metar_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}