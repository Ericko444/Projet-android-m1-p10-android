package mg.itu.projetm1.vues;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import mg.itu.projetm1.R;
import mg.itu.projetm1.models.Image;
import mg.itu.projetm1.models.Review;
import mg.itu.projetm1.models.Tag;
import mg.itu.projetm1.models.Video;

public class PlaceDetailActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        List<Image> images = (List<Image>) intent.getSerializableExtra("images");
        List<Tag> tags = (List<Tag>) intent.getSerializableExtra("tags");
        List<Review> reviews = (List<Review>) intent.getSerializableExtra("reviews");
        List<Video> videos = (List<Video>) intent.getSerializableExtra("videos");

        ImageCarousel carousel = findViewById(R.id.carousel);

        carousel.registerLifecycle(getLifecycle());

        List<CarouselItem> list = new ArrayList<>();

        for (Image im: images
             ) {
            list.add(new CarouselItem(im.getImage()));
        }

        List<String> tagList = new ArrayList<>();
        for (Tag t : tags
             ) {
            tagList.add(t.getName());
        }

        carousel.setData(list);

        TextView textViewCreator = findViewById(R.id.place_name);
        TextView descr = findViewById(R.id.place_desc);
        TextView textViewTags = findViewById(R.id.place_tags);
        TextView textViewReviews= findViewById(R.id.place_reviews);
        RatingBar notes = findViewById(R.id.smallRating);
        double moyenneReviews = getMoyenneReviews(reviews);
        notes.setRating((float) moyenneReviews);
        String textTags = "Tags : ";
        for (String t : tagList
             ) {
            textTags += ("#"+t+", ");
        }

        WebView video = findViewById(R.id.place_video);
        WebSettings webSettings = video.getSettings();

        webSettings.setJavaScriptEnabled(true);

        if(videos.size() > 0){
            String videoId = videos.get(0).getVideo();
            String youtubeUrl = "https://www.youtube.com/embed/" + videoId;
            video.loadUrl(youtubeUrl);
        }
        else{
            linearLayout = findViewById(R.id.video_parent);
            webView = findViewById(R.id.place_video);
            linearLayout.removeView(webView);
        }


//        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
        textViewCreator.setText(title);
        textViewTags.setText(textTags);
        textViewReviews.setText(""+moyenneReviews+" ("+reviews.size()+" reviews)");
        descr.setText(desc);
    }

    public static double getMoyenneReviews(List<Review> reviewList){
        double total = 0;
        for (Review rv: reviewList
             ) {
            total += rv.getNote();
        }
        if(total > 0){
            return Math.ceil((double)total / reviewList.size());
        }
        return 0;
    }
}