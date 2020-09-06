package eg.mahmoudShawky.metar.data.local.db.dao;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import eg.mahmoudShawky.metar.data.local.db.MetarDatabase;
import eg.mahmoudShawky.metar.utils.Utils;

import static org.junit.Assert.assertEquals;

public class StationDAOTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();
    StationEntity entity1 = new StationEntity("EDAH", "Heringsdorf", 1599404797037L);
    StationEntity entity2 = new StationEntity("EDDC", "Dresden-Klotzsche", 1599404797037L);
    private StationDAO stationDAO;
    private MetarDatabase database;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        database = Room.inMemoryDatabaseBuilder(context, MetarDatabase.class).allowMainThreadQueries().build();
        stationDAO = database.stationDAO();
        stationDAO.insert(entity1);
        stationDAO.insert(entity2);
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insert() {
        StationEntity entity3 = new StationEntity("EDMA", "Augsburg", 1599404797037L);
        stationDAO.insert(entity3);

        assertEquals(stationDAO.getRawCount(), 3);
    }

    @Test
    public void getFavouriteStations() {
        entity1.setFavourite(true);
        stationDAO.update(entity1);
        List<StationEntity> favList = stationDAO.getFavouriteStations();

        assertEquals(favList.get(0).getId(), entity1.getId());
    }

    @Test
    public void getRawCount() {
    }

    @Test
    public void updateDecodedData() {
        String encodedData = "Heringsdorf, Germany (EDAH) 53-53N 014-09E\n" +
                "Sep 06, 2020 - 10:20 AM EDT / 2020.09.06 1420 UTC\n" +
                "Wind: from the NW (310 degrees) at 5 MPH (4 KT) (direction variable):0\n" +
                "Visibility: greater than 7 mile(s):0\n" +
                "Sky conditions: partly cloudy\n" +
                "Temperature: 62 F (17 C)\n" +
                "Dew Point: 46 F (8 C)\n" +
                "Relative Humidity: 55%\n" +
                "Pressure (altimeter): 30.00 in. Hg (1016 hPa)\n" +
                "ob: EDAH 061420Z 31004KT 230V360 9999 SCT049 17/08 Q1016\n" +
                "cycle: 14";

        entity1.setDecodedData(encodedData);
        stationDAO.update(entity1);
        LiveData<StationEntity> res = database.stationDAO().getStation(entity1.getId());
        try {
            assertEquals(Utils.getOrAwaitValue(res).getDecodedData(), entity1.getDecodedData());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}