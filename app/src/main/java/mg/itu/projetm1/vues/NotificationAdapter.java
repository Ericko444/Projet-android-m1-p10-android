package mg.itu.projetm1.vues;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mg.itu.projetm1.R;
import mg.itu.projetm1.models.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_PLACEHOLDER = 1;
    List<Notification> notificationList;

    Context context;

    private boolean isLoading;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public NotificationAdapter(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.notification_item, parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            Notification currentNotif = notificationList.get(position);
            holder.title.setText(currentNotif.getTitle());
            holder.body.setText(currentNotif.getBody());
            holder.date.setText(currentNotif.getDate());
        } else {
            Log.d("NOT FETCHED YET", "OOOOHH");
        }
    }

    @Override
    public int getItemCount() {
        return isLoading ? notificationList.size() + 1 : notificationList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return isLoading && position == getItemCount() - 1
                ? VIEW_TYPE_PLACEHOLDER
                : VIEW_TYPE_ITEM;
    }

    public class NotificationHolder extends RecyclerView.ViewHolder{

        TextView title;

        TextView body;
        TextView date;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notif_title);
            body = itemView.findViewById(R.id.notif_body);
            date = itemView.findViewById(R.id.notif_date);
        }
    }
}
