package eg.mahmoudShawky.metar.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.ListenableWorker;
import androidx.work.WorkerFactory;
import androidx.work.WorkerParameters;

import eg.mahmoudShawky.metar.data.Repository;

public class MetarWorkerFactory extends WorkerFactory {

    private Repository repository;

    public MetarWorkerFactory(Repository repository) {
        this.repository = repository;
    }

    @Nullable
    @Override
    public ListenableWorker createWorker(@NonNull Context appContext, @NonNull String workerClassName, @NonNull WorkerParameters workerParameters) {
        if (workerClassName.equalsIgnoreCase(UpdateWork.class.getName())) {
            return new UpdateWork(appContext, workerParameters, repository);
        }
        throw new IllegalArgumentException("Unknown Worker Class");
    }
}
