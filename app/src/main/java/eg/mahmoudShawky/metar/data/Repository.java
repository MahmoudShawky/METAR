package eg.mahmoudShawky.metar.data;

import eg.mahmoudShawky.metar.data.local.db.DbHelper;
import eg.mahmoudShawky.metar.data.remote.RemoteRepo;

public interface Repository extends DbHelper, RemoteRepo {
}
