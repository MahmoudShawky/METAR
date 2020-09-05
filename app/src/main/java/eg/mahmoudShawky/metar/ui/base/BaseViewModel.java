package eg.mahmoudShawky.metar.ui.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import eg.mahmoudShawky.metar.data.Repository;
import eg.mahmoudShawky.metar.utils.SingleLiveEvent;

/***
 * @author mahmoud.shawky
 *
 * Base ViewModel that contains the common implementations for the app View Models
 */
public class BaseViewModel extends ViewModel {
    protected Repository repository;
    protected SingleLiveEvent<Integer> networkStatus = new SingleLiveEvent<>();

    //Res String Id
    protected SingleLiveEvent<Integer> errorId = new SingleLiveEvent<>();
    //String Message
    protected SingleLiveEvent<String> errorText = new SingleLiveEvent<>();

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
