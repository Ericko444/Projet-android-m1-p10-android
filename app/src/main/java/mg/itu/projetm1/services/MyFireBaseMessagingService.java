package mg.itu.projetm1.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import mg.itu.projetm1.R;
import mg.itu.projetm1.vues.MainActivity;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private static final String CANAL = "MyNotifCanal";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String message = remoteMessage.getNotification().getBody();
        String title = remoteMessage.getNotification().getTitle();
        Log.d("FirebaseMessage", "Notif :"+message);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bell);
        showNotification(bitmap, title, message);


    }

    private void showNotification(Bitmap bitmap, String title, String body) {
        int noificationId = new Random().nextInt(100);
        String channelId = "notification_channel_3";
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(), channelId
        );
        builder.setSmallIcon(R.drawable.bell);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentTitle(title); // make suer change the channel for image
        builder.setContentText(body);
        builder.setStyle(new NotificationCompat.BigPictureStyle().
                bigPicture(bitmap));
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if (notificationManager != null && notificationManager.
                    getNotificationChannel(channelId) == null){
                NotificationChannel notificationChannel = new NotificationChannel(
                        channelId, "Notification channel 1",
                        NotificationManager.IMPORTANCE_HIGH
                );
                notificationChannel.setDescription("This notification channel is used to notify user");
                notificationChannel.enableVibration(true);
                notificationChannel.enableLights(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Notification notification = builder.build();
        if (notificationManager != null){
            notificationManager.notify(noificationId, notification);
        }
    }
}
