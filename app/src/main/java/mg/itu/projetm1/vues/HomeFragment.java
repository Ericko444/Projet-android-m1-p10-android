package mg.itu.projetm1.vues;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import mg.itu.projetm1.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements PlaceItemAdapter.OnItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerViewRecommendation;
    private RecyclerView recyclerViewParProvince;

    private ArrayList<Place> dataRecommendations;
    private ArrayList<Place> dataParProvince;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager2;
    private PlaceItemAdapter placeItemAdapterRecommendation;
    private PlaceItemAdapter placeItemAdapterParProvince;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataRecommendations = new ArrayList<Place>();
        dataParProvince = new ArrayList<Place>();

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewRecommendation = rootView.findViewById(R.id.recommendations);
        recyclerViewParProvince = rootView.findViewById(R.id.par_province);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRecommendation.setLayoutManager(linearLayoutManager);
        recyclerViewParProvince.setLayoutManager(linearLayoutManager2);
        placeItemAdapterRecommendation = new PlaceItemAdapter(dataRecommendations, getActivity());
        placeItemAdapterParProvince = new PlaceItemAdapter(dataParProvince, getActivity());
        recyclerViewRecommendation.setAdapter(placeItemAdapterRecommendation);
        placeItemAdapterRecommendation.setOnItemClickListener(HomeFragment.this);
        recyclerViewParProvince.setAdapter(placeItemAdapterParProvince);
        placeItemAdapterParProvince.setOnItemClickListener(HomeFragment.this);

        fetchPlacesRecommendation();
        fetchPlacesParProvince();
        // Inflate the layout for this fragment
        return rootView;
    }

    private void fetchPlacesRecommendation(){
        RetrofitClient.getRetrofitClient().getPlacesRecommendation().enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                if(response.isSuccessful() && response.body() != null){
                    dataRecommendations.addAll(response.body());
                    placeItemAdapterRecommendation.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("ERROR", "onFailure: "+t.getMessage());
            }
        });
    }

    private void fetchPlacesParProvince(){
        RetrofitClient.getRetrofitClient().getPlacesParProvince().enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                if(response.isSuccessful() && response.body() != null){
                    dataParProvince.addAll(response.body());
                    placeItemAdapterParProvince.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("ERROR", "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getActivity(), PlaceDetailActivity.class);
        Place clickedItem = dataRecommendations.get(position);

        detailIntent.putExtra("title", clickedItem.getTitle());
        detailIntent.putExtra("desc", clickedItem.getDesc());
        detailIntent.putExtra("images", (Serializable) clickedItem.getImages());
        detailIntent.putExtra("tags", (Serializable) clickedItem.getTags());
        detailIntent.putExtra("reviews", (Serializable) clickedItem.getReviews());

        startActivity(detailIntent);
    }
}