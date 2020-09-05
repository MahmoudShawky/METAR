package eg.mahmoudShawky.metar.utils;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/***
 * @author mahmoud.shawky
 */
@IntDef({NetworkStatus.RUNNING, NetworkStatus.REFRESHING, NetworkStatus.SUCCESS, NetworkStatus.SUCCESS_NO_DATA, NetworkStatus.FAILED})
@Retention(RetentionPolicy.SOURCE)
public @interface NetworkStatus {
    int RUNNING = 0;
    int REFRESHING = 1;
    int SUCCESS = 2;
    int SUCCESS_NO_DATA = 3;
    int FAILED = 4;
}
