package eg.mahmoudShawky.metar.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * @author mahmoud.shawky
 */
public class Utils {
    private static volatile Handler handler;

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private static Handler getHandler() {
        if (handler == null) {
            synchronized (Utils.class) {
                if (handler == null) {
                    handler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return handler;
    }

    public static void runOnMain(final @NonNull Runnable runnable) {
        if (isMainThread()) runnable.run();
        else getHandler().post(runnable);
    }


    /***
     * To parse html line that contains href and 4 char length station code
     * @param htmlRaw Html Line
     * @return Pair of file hyper ref and it's name
     */
    public static Pair<String, String> getLinkLocationAndName(String htmlRaw) {
        if (!TextUtils.isEmpty(htmlRaw)) {
            Pattern pattern = Pattern.compile("<a\\b[^>]*href=\"(.*?)\">(\\w{4}).TXT</a>");
            Matcher matcher = pattern.matcher(htmlRaw);
            if (matcher.find()) {
                return new Pair<>(matcher.group(1), matcher.group(2));
            }
        }

        return null;
    }

    /***
     /**
     * Helper method for testing LiveData objects, from
     * https://github.com/googlesamples/android-architecture-components.
     *
     * Get the value from a LiveData object. We're waiting for LiveData to emit, for 2 seconds.
     * Once we got a notification via onChanged, we stop observing.
     */
    public static <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(5, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }
}
