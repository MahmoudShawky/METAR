package eg.mahmoudShawky.metar.utils;

import android.util.Log;

/***
 * @author mahmoud.shawky
 *
 * Represnts a single class for logging, helps us if we need to add anothe logging way
 */
public class AppLogger {

    private static AppLogger appLogger;

    private AppLogger() {
    }

    public synchronized static AppLogger getInstance() {
        if (appLogger == null) {
            appLogger = new AppLogger();
        }

        return appLogger;
    }

    public void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public void w(String tag, String msg) {
        Log.w(tag, msg);
    }


    public void i(String tag, String msg, Throwable t) {
        Log.i(tag, msg, t);
    }

    public void d(String tag, String msg, Throwable t) {
        Log.d(tag, msg, t);
    }

    public void v(String tag, String msg, Throwable t) {
        Log.v(tag, msg, t);
    }

    public void e(String tag, String msg, Throwable t) {
        Log.e(tag, msg, t);
    }

    public void w(String tag, String msg, Throwable t) {
        Log.w(tag, msg, t);
    }

    public void w(String tag, Throwable t) {
        Log.w(tag, t);
    }
}
