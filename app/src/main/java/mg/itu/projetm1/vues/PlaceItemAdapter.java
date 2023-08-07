package mg.itu.projetm1.vues;

import static mg.itu.projetm1.vues.PlaceDetailActivity.getMoyenneReviews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mg.itu.projetm1.R;
import mg.itu.projetm1.models.Place;
import mg.itu.projetm1.models.Review;

public class PlaceItemAdapter extends RecyclerView.Adapter<PlaceItemAdapter.PlaceItemHolder> {

    ArrayList<Place> data;
    Context context;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_PLACEHOLDER = 1;

    private boolean isLoading;

    private RecommendationItemClickListener recommendationItemClickListener;
    private ParProvinceItemClickListener parProvinceItemClickListener;

    private OnItemClickListener listener;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface RecommendationItemClickListener {
        void onRecommendationItemClick(int position);
    }

    public interface ParProvinceItemClickListener {
        void onParProvinceItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setRecommendationItemClickListener(RecommendationItemClickListener recommendationItemClickListener) {
        this.recommendationItemClickListener = recommendationItemClickListener;
    }

    public void setParProvinceItemClickListener(ParProvinceItemClickListener parProvinceItemClickListener) {
        this.parProvinceItemClickListener = parProvinceItemClickListener;
    }

    public PlaceItemAdapter(ArrayList<Place> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.place_item, parent, false);

        return new PlaceItemHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return isLoading && position == getItemCount() - 1
                ? VIEW_TYPE_PLACEHOLDER
                : VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceItemHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            Place currentItem = data.get(position);
            List<Review> reviews =  currentItem.getReviews();
            double moyenneReviews = getMoyenneReviews(reviews);
            holder.rating.setRating((float) moyenneReviews);
            String imageUrl = currentItem.getImages().size() > 0 ? currentItem.getImages().get(0).getImage() : "https://cdn.statically.io/gh/Ericko444/CDN/c04f6bc6/Tourisme/default_image.png";
            holder.textView.setText(currentItem.getTitle());
            holder.province.setText(currentItem.getProvince().getName());
            Picasso.get().load(imageUrl).placeholder(R.drawable.default_image).fit().centerCrop().into(holder.imageView);
        }
        else{
            Log.d("NOT FETCHED YET", "OOOOHH");
        }

    }

    @Override
    public int getItemCount() {
        return isLoading ? data.size() + 1 : data.size();
    }

    public void updateList(ArrayList<Place> newList) {
        data.clear();
        data.addAll(newList);
        notifyDataSetChanged();
    }

    public class PlaceItemHolder extends RecyclerView.ViewHolder {
        TextView textView;

        TextView province;
        ImageView imageView;

        RatingBar rating;

        public PlaceItemHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvTitle);
            province = itemView.findViewById(R.id.tvSub);
            imageView = itemView.findViewById(R.id.image_view);
            rating = itemView.findViewById(R.id.smallRating);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    if (recommendationItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recommendationItemClickListener.onRecommendationItemClick(position);
                        }
                    }
                    else if(parProvinceItemClickListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            parProvinceItemClickListener.onParProvinceItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
