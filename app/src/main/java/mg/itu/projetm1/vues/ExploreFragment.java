package mg.itu.projetm1.vues;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

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


public class ExploreFragment extends Fragment  implements PlaceItemAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private PlaceItemAdapter placeAdapter;
    private ArrayList<Place> allPlaces;
    private ArrayList<Place> initialPlaces;
    private TextInputLayout searchInputLayout;
    private EditText inputSearch;

    private PlaceModel placeModel;

    DataCacheManager dataCacheManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeModel = new ViewModelProvider(this).get(PlaceModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        placeModel = new ViewModelProvider(requireActivity()).get(PlaceModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeModel = new ViewModelProvider(requireActivity()).get(PlaceModel.class);
        allPlaces = new ArrayList<>();
        recyclerView = view.findViewById(R.id.allPlaces);

        dataCacheManager = new DataCacheManager(getContext());

        int spanCount = 2; // Number of columns in the grid
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));

        placeAdapter = new PlaceItemAdapter(allPlaces, getActivity());
        recyclerView.setAdapter(placeAdapter);
        placeAdapter.setOnItemClickListener(ExploreFragment.this);

        searchInputLayout = view.findViewById(R.id.search_input_layout_explore);
        inputSearch = view.findViewById(R.id.explore_search);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                filterList(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not used
            }
        });

        inputSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    hideKeyboard(v);
                    return true;
                }
                return false;
            }
        });

        Bundle args = getArguments();
        if (args != null && args.getBoolean("focusSearchBar", false)) {
            inputSearch.requestFocus();

            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(inputSearch, InputMethodManager.SHOW_IMPLICIT);
        }

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hideKeyboard(v);
                }
                return false;
            }
        });


        fetchPlaces();
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    private void filterList(String query) {

        ArrayList<Place> filteredList = new ArrayList<>();
        for (Place place : initialPlaces) {
            if (place.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(place);
            }
        }
        placeAdapter.updateList(filteredList);
    }
    private void fetchPlaces(){
        List<Place>cachedData =  dataCacheManager.loadData();
        List<Place> cachedDataModel =  placeModel.getData().getValue();
        if(cachedData != null && !cachedData.isEmpty()){
            allPlaces.addAll(cachedData);
            placeModel.setData(allPlaces);
            placeAdapter.notifyDataSetChanged();
            initialPlaces = new ArrayList<>(allPlaces);
        }
        else if(cachedDataModel != null && !cachedDataModel.isEmpty()){
            allPlaces.addAll(cachedDataModel);
            placeAdapter.notifyDataSetChanged();
            initialPlaces = new ArrayList<>(allPlaces);
        }
        else{
            RetrofitClient.getRetrofitClient().getAllPlaces().enqueue(new Callback<List<Place>>() {
                @Override
                public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        allPlaces.addAll(response.body());
                        placeModel.setData(allPlaces);
                        placeAdapter.notifyDataSetChanged();
                        initialPlaces = new ArrayList<>(allPlaces);
                    }
                }

                @Override
                public void onFailure(Call<List<Place>> call, Throwable t) {
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