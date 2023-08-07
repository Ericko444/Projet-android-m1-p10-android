package mg.itu.projetm1.vues;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mg.itu.projetm1.R;
import mg.itu.projetm1.models.Place;
import mg.itu.projetm1.models.PlaceModel;
import mg.itu.projetm1.session.SessionManager;
import mg.itu.projetm1.utils.DataCacheManager;
import mg.itu.projetm1.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedFragment extends Fragment implements SavedItemAdapter.OnItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;

    private List<Place> placeList;

    SavedItemAdapter adapter;

    PlaceModel placeModel;

    DataCacheManager dataCacheManager;

    public SavedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedFragment newInstance(String param1, String param2) {
        SavedFragment fragment = new SavedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeModel = new ViewModelProvider(this).get(PlaceModel.class);
        dataCacheManager = new DataCacheManager(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SessionManager sessionManager = new SessionManager(getContext());
        int id = (Integer) sessionManager.getUserDetail().get("ID");

        Log.d("USERIDKKK", String.valueOf(id));

        placeList = new ArrayList<>();
        dataCacheManager = new DataCacheManager(getContext());
        placeModel = new ViewModelProvider(requireActivity()).get(PlaceModel.class);
        View rootView = inflater.inflate(R.layout.fragment_saved, container, false);
        recyclerView = rootView.findViewById(R.id.saved_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SavedItemAdapter(placeList, getContext());
        adapter.setOnItemClickListener(SavedFragment.this);
        recyclerView.setAdapter(adapter);
        fetchFavorites();
        return rootView;
        // Inflate the layout for this fragment
    }

    private void fetchFavorites(){
        adapter.setLoading(true);
        adapter.notifyDataSetChanged();
        List<Place>cachedData =  dataCacheManager.recommendations();
        List<Place> cachedDataModel = placeModel.getData().getValue();
        if(cachedData != null && !cachedData.isEmpty()){
            adapter.setLoading(false);
            adapter.notifyDataSetChanged();
            placeList.addAll(cachedData);
            placeModel.setData(placeList);
            adapter.notifyDataSetChanged();
        }
        else if(cachedDataModel != null && !cachedDataModel.isEmpty()){
            adapter.notifyDataSetChanged();
            adapter.setLoading(false);
            adapter.notifyDataSetChanged();
            placeList.addAll(cachedDataModel);
            adapter.notifyDataSetChanged();
        }else{
            RetrofitClient.getRetrofitClient().getPlacesRecommendation().enqueue(new Callback<List<Place>>() {
                @Override
                public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        placeList.addAll(response.body());
                        placeModel.setData(placeList);
                        adapter.notifyDataSetChanged();
                    }
                    adapter.setLoading(false);
                }

                @Override
                public void onFailure(Call<List<Place>> call, Throwable t) {
                    adapter.setLoading(false);
                    Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("ERROR", "onFailure: "+t.getMessage());
                }
            });
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getActivity(), PlaceDetailActivity.class);
        Place clickedItem = placeModel.getData().getValue().get(position);
        Log.d("EXPLORECLICK", "onItemClick: CLICKED"+clickedItem.getTitle());
        detailIntent.putExtra("title", clickedItem.getTitle());
        detailIntent.putExtra("desc", clickedItem.getDesc());
        detailIntent.putExtra("images", (Serializable) clickedItem.getImages());
        detailIntent.putExtra("tags", (Serializable) clickedItem.getTags());
        detailIntent.putExtra("reviews", (Serializable) clickedItem.getReviews());
        detailIntent.putExtra("videos", (Serializable) clickedItem.getVideos());

        startActivity(detailIntent);
    }
}