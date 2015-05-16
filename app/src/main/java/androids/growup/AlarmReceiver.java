package androids.growup;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import java.util.HashMap;
import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String DEBUG_TAG = "AlarmReceiver";
    private static final String PREFS_NAME = "SETTINGS";

    // TODO: Do this some other way because this sucks!
    private final String[] plants = {
            "oregano",
            "basilika",
            "physalis (gyllenbär)",
            "jordgubbar",
            "morot",
            "rädisa",
            "smörgåskrasse",
            "ringblomma"
    };

    private Map<String, Boolean> notices = new HashMap();

    @Override
    public void onReceive(Context ctx, Intent intent) {
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        /* What to show push for ... */
        for (String plant : plants) {
            boolean show = settings.getBoolean(plant, false);
            notices.put(plant, show);
        }

        int nrOfPlantsToShow = 0;
        String show = "";

        for (Map.Entry<String, Boolean> entry : notices.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();

            if (value) {
                nrOfPlantsToShow++;
                show += key + " ";
            }
        }

        boolean isPushNoticesTurnedOn = settings.getBoolean("settings_toggle_push_notices", false);
        //Log.i("TEST MOTHERFUCKER", "Number of plants set to true: " + nrOfPlantsToShow);

        if ((nrOfPlantsToShow > 0) && (isPushNoticesTurnedOn == true)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                    .setContentTitle("GrowApp")
                    .setContentText("Glöm inte dina plantor!")
                    .setSmallIcon(R.drawable.tomato);

            NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
            // For our recurring task, we'll just display a message
        }
    }
}