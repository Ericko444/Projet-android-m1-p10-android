package mg.itu.projetm1.vues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mg.itu.projetm1.R;
import mg.itu.projetm1.models.Place;

public class PlaceItemAdapter extends RecyclerView.Adapter<PlaceItemAdapter.PlaceItemHolder> {

    ArrayList<Place> data;
    Context context;

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
    public void onBindViewHolder(@NonNull PlaceItemHolder holder, int position) {
        String imageUrl = "https://pixabay.com/get/g40cf2ac2eb92ab995ad0c6905aa86c93bdcf74d098a7dbcf509ab8dd4b1240e26872e92dc43e7238a064f359d328a594e15c28e0133b7b046103087565fb9e32_640.jpg";
        holder.textView.setText(data.get(position).getTitle());
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PlaceItemHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public PlaceItemHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvTitle);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
