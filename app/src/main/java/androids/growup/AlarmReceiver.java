package androids.growup;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("motherfucker", "PING!");

        Intent myIntent = new Intent(context, MyPageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, Intent.FILL_IN_ACTION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("GrowApp")
                .setContentText("Glöm inte dina plantor!")
                .setSmallIcon(R.drawable.tomato)
                .setTicker("Notification!")
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}