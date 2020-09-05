package eg.mahmoudShawky.metar.data;

import eg.mahmoudShawky.metar.data.local.db.DbHelper;
import eg.mahmoudShawky.metar.data.remote.RemoteRepo;

/***
 * @author mahmoud.shawky
 *
 * Repository is an interface the should be implemented to access the application data layer
 */
public interface Repository extends DbHelper, RemoteRepo {
}
