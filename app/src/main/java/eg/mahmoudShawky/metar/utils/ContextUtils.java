package eg.mahmoudShawky.metar.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/***
 * This class contains utils that require context
 * Author: Mahmoud Shawky
 */
public class ContextUtils {

    private Context context;

    @Inject
    public ContextUtils(Context context) {
        this.context = context;
    }

    /***
     * To check if there an active network connection
     * @return true if connection available, false otherwise
     */
    public boolean isOnline() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }
}
