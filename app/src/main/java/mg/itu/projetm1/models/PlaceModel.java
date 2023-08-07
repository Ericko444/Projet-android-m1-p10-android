package mg.itu.projetm1.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class PlaceModel extends ViewModel {
    private MutableLiveData<List<Place>> data = new MutableLiveData<>();

    public LiveData<List<Place>> getData() {
        return data;
    }

    public void setData(List<Place> listData){
        this.data.setValue(listData);
    }

}
