package eg.mahmoudShawky.metar.utils.concurrent;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eg.mahmoudShawky.metar.utils.Utils;

public class SimpleTask {

    private static final ExecutorService EXECUTOR_SERVICE =
            Executors.newFixedThreadPool(Math.max(2, Math.min(Runtime.getRuntime().availableProcessors() - 1, 4)),
                    Executors.defaultThreadFactory());

    /**
     * Runs a task in the background and passes the result of the computation to a task that is run
     * on the main thread. Will only invoke the {@code foregroundTask} if the provided {@link Lifecycle}
     * is in a valid (i.e. visible) state at that time. In this way, it is very similar to
     * {@link AsyncTask}, but is safe in that you can guarantee your task won't be called when your
     * view is in an invalid state.
     */
    public static <E> void run(@NonNull Lifecycle lifecycle, @NonNull BackgroundTask<E> backgroundTask, @NonNull ForegroundTask<E> foregroundTask) {
        if (!isValid(lifecycle)) {
            return;
        }

        EXECUTOR_SERVICE.execute(() -> {
            final E result = backgroundTask.run();

            if (isValid(lifecycle)) {
                Utils.runOnMain(() -> {
                    if (isValid(lifecycle)) {
                        foregroundTask.run(result);
                    }
                });
            }
        });
    }

    /**
     * Runs a task in the background and passes the result of the computation to a task that is run on
     * the main thread. Essentially {@link AsyncTask}, but lambda-compatible.
     */
    public static <E> void run(@NonNull BackgroundTask<E> backgroundTask, @NonNull ForegroundTask<E> foregroundTask) {
        EXECUTOR_SERVICE.execute(() -> {
            final E result = backgroundTask.run();
            Utils.runOnMain(() -> foregroundTask.run(result));
        });
    }

    public static void run(@NonNull BackgroundVoidTask backgroundTask) {
        EXECUTOR_SERVICE.execute(backgroundTask::run);
    }

    private static boolean isValid(@NonNull Lifecycle lifecycle) {
        return lifecycle.getCurrentState().isAtLeast(Lifecycle.State.CREATED);
    }

    public interface BackgroundTask<E> {
        E run();
    }

    public interface BackgroundVoidTask {
        void run();
    }

    public interface ForegroundTask<E> {
        void run(E result);
    }
}
