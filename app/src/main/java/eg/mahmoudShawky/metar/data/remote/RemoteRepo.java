package eg.mahmoudShawky.metar.data.remote;

import android.util.Pair;

import java.io.IOException;
import java.util.List;

public interface RemoteRepo {
    List<Pair<String, String>> getStationsCodes(String nameFilter) throws IOException;

    String getStationsDecodedFile(String stationCode) throws IOException;
}
