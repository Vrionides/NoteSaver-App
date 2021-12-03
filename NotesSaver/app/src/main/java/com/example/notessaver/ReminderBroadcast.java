package com.example.notessaver;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// BroadcastReciever class to get the notification to work

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("NotesSaver")
                .setContentText("This is a reminder to use NotesSaver")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);


        notificationManager.notify(200, builder.build());
    }
}
