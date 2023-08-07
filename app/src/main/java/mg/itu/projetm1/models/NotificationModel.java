package mg.itu.projetm1.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NotificationModel extends ViewModel {
    private MutableLiveData<List<Notification>> data = new androidx.lifecycle.MutableLiveData<>();

    public LiveData<List<Notification>> getData() {
        return data;
    }

    public void setData(List<Notification> listData){
        this.data.setValue(listData);
    }
}
