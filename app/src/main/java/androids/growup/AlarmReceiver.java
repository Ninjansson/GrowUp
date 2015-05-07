package androids.growup;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String DEBUG_TAG = "AlarmReceiver";
    private static final String PREFS_NAME = "SETTINGS";

    @Override
    public void onReceive(Context ctx, Intent intent) {
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        boolean apa = settings.getBoolean("oregano", false);

        if (apa) {
            Toast.makeText(ctx, "STARTAR", Toast.LENGTH_SHORT).show();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                    .setContentTitle("GrowApp")
                    .setContentText("Mia Grooveman in the house!" + "\r\n" + "WOOP WOOP!")
                    .setSmallIcon(R.drawable.tomato);
            NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        } else {
            Toast.makeText(ctx, "INTE IGÅNG", Toast.LENGTH_SHORT).show();
        }
        // For our recurring task, we'll just display a message

    }
}