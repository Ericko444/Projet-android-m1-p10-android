package mg.itu.projetm1.vues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
    public void onBindViewHolder(@NonNull PlaceItemHolder holder, int position) {
        String imageUrl = "https://cdn.statically.io/gh/Ericko444/CDN/6444d1fb/Tourisme/data-images/rova-3.jpg";
        holder.textView.setText(data.get(position).getTitle());
        holder.province.setText(data.get(position).getProvince().getName());
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PlaceItemHolder extends RecyclerView.ViewHolder {
        TextView textView;

        TextView province;
        ImageView imageView;

        public PlaceItemHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvTitle);
            province = itemView.findViewById(R.id.tvSub);
            imageView = itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
