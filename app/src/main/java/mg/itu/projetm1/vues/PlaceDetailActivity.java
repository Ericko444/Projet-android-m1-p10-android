package mg.itu.projetm1.vues;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import mg.itu.projetm1.R;

public class PlaceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");

        ImageCarousel carousel = findViewById(R.id.carousel);

        carousel.registerLifecycle(getLifecycle());

        List<CarouselItem> list = new ArrayList<>();

        list.add(new CarouselItem("https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080"));

        List<String> tags = new ArrayList<>();
        tags.add("landscape");
        tags.add("rova");
        tags.add("antananarivo");

        carousel.setData(list);

        TextView textViewCreator = findViewById(R.id.place_name);
        TextView textViewLikes = findViewById(R.id.place_desc);
        TextView textViewTags = findViewById(R.id.place_tags);

        String textTags = "Tags : ";
        for (String tag: tags
             ) {
            textTags += ("#"+tag+", ");
        }

//        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
        textViewCreator.setText(title);
        textViewLikes.setText(desc);
        textViewTags.setText(textTags);
    }
}