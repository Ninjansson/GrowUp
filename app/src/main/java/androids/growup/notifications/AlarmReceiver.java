package androids.growup.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import androids.growup.R;
import androids.growup.activities.MyPageActivity;

/**
 * Handles the notifications.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, MyPageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, Intent.FILL_IN_ACTION);

        // The push notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("GrowApp")
                .setContentText("Glöm inte dina plantor!")
                .setSmallIcon(R.drawable.tomato)
                .setTicker("GrowUp påminnelse!")
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}