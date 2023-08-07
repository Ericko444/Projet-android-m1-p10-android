package mg.itu.projetm1.vues;

import static mg.itu.projetm1.vues.PlaceDetailActivity.getMoyenneReviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mg.itu.projetm1.R;
import mg.itu.projetm1.models.Notification;
import mg.itu.projetm1.models.Place;
import mg.itu.projetm1.models.Review;

public class SavedItemAdapter extends RecyclerView.Adapter<SavedItemAdapter.SavedItemHolder>{
    private List<Place> savedItemsList;
    Context context;

    public SavedItemAdapter(List<Place> savedItemsList, Context context) {
        this.savedItemsList = savedItemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public SavedItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.favorite_card_layout, parent, false);
        return new SavedItemAdapter.SavedItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedItemHolder holder, int position) {
        Place currentSavedItem = savedItemsList.get(position);
        holder.title.setText(currentSavedItem.getTitle());
        holder.tag.setText(currentSavedItem.getProvince().getName());
        List<Review> reviews =  currentSavedItem.getReviews();
        double moyenneReviews = getMoyenneReviews(reviews);
        holder.rating.setRating((float) moyenneReviews);
        String imageUrl = currentSavedItem.getImages().size() > 0 ? currentSavedItem.getImages().get(0).getImage() : "https://cdn.statically.io/gh/Ericko444/CDN/c04f6bc6/Tourisme/default_image.png";
        Picasso.get().load(imageUrl).placeholder(R.drawable.default_image).fit().centerCrop().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return savedItemsList.size();
    }

    public class SavedItemHolder extends RecyclerView.ViewHolder{
        TextView title;

        TextView tag;
        ImageView image;

        RatingBar rating;
        public SavedItemHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.favorite_title);
            tag = itemView.findViewById(R.id.favorite_tag);
            image = itemView.findViewById(R.id.favorite_image);
            rating = itemView.findViewById(R.id.rating_favorite);
        }
    }
}
