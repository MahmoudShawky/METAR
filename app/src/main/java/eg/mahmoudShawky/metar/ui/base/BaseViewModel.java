package eg.mahmoudShawky.metar.ui.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eg.mahmoudShawky.metar.data.Repository;

public class BaseViewModel extends ViewModel {
    protected Repository repository;
    protected MutableLiveData<Integer> networkStatus = new MutableLiveData<>();

    //String Id
    protected MutableLiveData<Integer> errorId = new MutableLiveData<>();
    protected MutableLiveData<String> errorText = new MutableLiveData<>();

    public BaseViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Integer> getNetworkStatus() {
        return networkStatus;
    }

    public LiveData<Integer> getErrorId() {
        return errorId;
    }

    public LiveData<String> getErrorText() {
        return errorText;
    }
}
