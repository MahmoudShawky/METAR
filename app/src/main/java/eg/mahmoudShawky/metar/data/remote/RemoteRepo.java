package eg.mahmoudShawky.metar.data.remote;

import android.util.Pair;

import java.io.IOException;
import java.util.List;

/***
 * @author mahmoud.shawky
 *
 * RemoteRepo is an interface that should be implemented to be able to interact with the Remote service functions
 */
public interface RemoteRepo {
    List<Pair<String, String>> getStationsCodes(String nameFilter) throws IOException;

    String getStationsDecodedFile(String stationCode) throws IOException;
}
