package mg.itu.projetm1.vues;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mg.itu.projetm1.R;
import mg.itu.projetm1.models.Place;
import mg.itu.projetm1.models.PlaceModel;
import mg.itu.projetm1.models.User;
import mg.itu.projetm1.session.SessionManager;
import mg.itu.projetm1.utils.CircleTransformation;
import mg.itu.projetm1.utils.DataCacheManager;
import mg.itu.projetm1.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements PlaceItemAdapter.RecommendationItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerViewRecommendation;

    private ArrayList<Place> dataRecommendations;

    private PlaceModel placeModelRecom;

    private LinearLayoutManager linearLayoutManager;
    private PlaceItemAdapter placeItemAdapterRecommendation;
    SessionManager sessionManager;

    DataCacheManager dataCacheManager;

    TextView input_search;

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
        placeModelRecom = new ViewModelProvider(this).get(PlaceModel.class);
        dataCacheManager = new DataCacheManager(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sessionManager = new SessionManager(getActivity());
        dataCacheManager = new DataCacheManager(getContext());
        dataRecommendations = new ArrayList<Place>();
        placeModelRecom = new ViewModelProvider(requireActivity()).get(PlaceModel.class);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewRecommendation = rootView.findViewById(R.id.recommendations);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRecommendation.setLayoutManager(linearLayoutManager);
        placeItemAdapterRecommendation = new PlaceItemAdapter(dataRecommendations, getActivity());
        placeItemAdapterRecommendation.setRecommendationItemClickListener(HomeFragment.this);
        recyclerViewRecommendation.setAdapter(placeItemAdapterRecommendation);

        fetchPlacesRecommendation();
        initGreetings(rootView);
        setImageListener(rootView);
        setImageProfile(rootView);
        setSearchListener(rootView);
        // Inflate the layout for this fragment
        return rootView;
    }

    private void setSearchListener(View rootView) {
        input_search = rootView.findViewById(R.id.input_search);
        input_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment exploreFragment = new ExploreFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, exploreFragment);

                Bundle args = new Bundle();
                args.putBoolean("focusSearchBar", true);
                exploreFragment.setArguments(args);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void setImageProfile(View rootView) {
        if(sessionManager.isLogged()) {
            ImageView profileImage = rootView.findViewById(R.id.profileImage);
            HashMap<String, Object> userDetails = sessionManager.getUserDetail();
            String profileURL = (String) userDetails.get("PROFILE");
            if (profileURL != null && !profileURL.isEmpty()) {
                Transformation circleTransformation = new CircleTransformation();
                Picasso.get()
                        .load(profileURL)
                        .transform(circleTransformation)
                        .into(profileImage);
            }
        }
    }

    public void initGreetings(View rootView){
        TextView greetingTextView = rootView.findViewById(R.id.greetingText);

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;
        if (hourOfDay >= 0 && hourOfDay < 18) {
            greeting = getString(R.string.good_morning);
        } else {
            greeting = getString(R.string.good_evening);
        }

        greeting = addUserDetail(greeting);

        greetingTextView.setText(greeting);
    }

    public void setImageListener(View rootView){
        ImageView profileImage = rootView.findViewById(R.id.profileImage);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountFragment accountFragment = new AccountFragment();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_home, accountFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.account);
            }
        });
    }

    private String addUserDetail(String greeting) {
        String postFix = " !";
        if(sessionManager.isLogged()){
            HashMap<String, Object> userDetails = sessionManager.getUserDetail();
            String name = (String) userDetails.get("NAME");
            if(name != null && !name.isEmpty()){
                postFix = ", " + User.extractFirstName(name);
            }
        } else {
            greeting = getString(R.string.welcome);
        }
        return greeting + postFix;
    }

    private void fetchPlacesRecommendation(){
        placeItemAdapterRecommendation.setLoading(true);
        placeItemAdapterRecommendation.notifyDataSetChanged();
        List<Place>cachedData =  dataCacheManager.recommendations();
        List<Place> cachedDataModel =  placeModelRecom.getData().getValue();
        if(cachedData != null && !cachedData.isEmpty()){
            placeItemAdapterRecommendation.setLoading(false);
            placeItemAdapterRecommendation.notifyDataSetChanged();
            dataRecommendations.addAll(cachedData);
            placeModelRecom.setData(dataRecommendations);
            placeItemAdapterRecommendation.notifyDataSetChanged();
        }
        else if(cachedDataModel != null && !cachedDataModel.isEmpty()){
            placeItemAdapterRecommendation.setLoading(false);
            placeItemAdapterRecommendation.notifyDataSetChanged();
            dataRecommendations.addAll(cachedDataModel);
            placeItemAdapterRecommendation.notifyDataSetChanged();
        }else{
            RetrofitClient.getRetrofitClient().getPlacesRecommendation().enqueue(new Callback<List<Place>>() {
                @Override
                public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        dataRecommendations.clear();
                        dataRecommendations.addAll(response.body());
                        placeModelRecom.setData(dataRecommendations);
                        placeItemAdapterRecommendation.notifyDataSetChanged();
                    }
                    placeItemAdapterRecommendation.setLoading(false);
                }

                @Override
                public void onFailure(Call<List<Place>> call, Throwable t) {
                    placeItemAdapterRecommendation.setLoading(false);
                    Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("ERROR", "onFailure: "+t.getMessage());
                }
            });
        }
    }

//    @Override
//    public void onItemClick(int position) {
//        Intent detailIntent = new Intent(getActivity(), PlaceDetailActivity.class);
//        Place clickedItem = placeModelRecom.getData().getValue().get(position);
//
//        detailIntent.putExtra("title", clickedItem.getTitle());
//        detailIntent.putExtra("desc", clickedItem.getDesc());
//        detailIntent.putExtra("images", (Serializable) clickedItem.getImages());
//        detailIntent.putExtra("tags", (Serializable) clickedItem.getTags());
//        detailIntent.putExtra("reviews", (Serializable) clickedItem.getReviews());
//        detailIntent.putExtra("videos", (Serializable) clickedItem.getVideos());
//
//        startActivity(detailIntent);
//    }

    @Override
    public void onRecommendationItemClick(int position) {
        Intent detailIntent = new Intent(getActivity(), PlaceDetailActivity.class);
        Place clickedItem = placeModelRecom.getData().getValue().get(position);

        detailIntent.putExtra("title", clickedItem.getTitle());
        detailIntent.putExtra("desc", clickedItem.getDesc());
        detailIntent.putExtra("images", (Serializable) clickedItem.getImages());
        detailIntent.putExtra("tags", (Serializable) clickedItem.getTags());
        detailIntent.putExtra("reviews", (Serializable) clickedItem.getReviews());
        detailIntent.putExtra("videos", (Serializable) clickedItem.getVideos());

        startActivity(detailIntent);
    }

}