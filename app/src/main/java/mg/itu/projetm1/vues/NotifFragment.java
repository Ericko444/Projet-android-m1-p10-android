package mg.itu.projetm1.vues;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mg.itu.projetm1.R;
import mg.itu.projetm1.models.Notification;
import mg.itu.projetm1.models.Place;
import mg.itu.projetm1.session.SessionManager;
import mg.itu.projetm1.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifFragment extends Fragment {
    RecyclerView recyclerView;

    List<Notification> notificationList;

    RecyclerView.Adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int id = 4;

        notificationList = new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_notif, container, false);
        recyclerView = rootView.findViewById(R.id.notifications_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotificationAdapter(notificationList, getContext());
        recyclerView.setAdapter(adapter);
        fetchNotifications(id);
        return rootView;
    }

    private void fetchNotifications(int id){
        RetrofitClient.getRetrofitClient().getNotifications(id).enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if(response.isSuccessful() && response.body() != null){
                    notificationList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("ERROR", "onFailure: "+t.getMessage());
            }
        });
    }
}