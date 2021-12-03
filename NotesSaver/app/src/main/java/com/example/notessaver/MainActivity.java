package com.example.notessaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.app.PendingIntent;
import android.os.Build;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView mListViewNotes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewNotes  = (ListView) findViewById(R.id.main_listview);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    // options when clicking buttons on menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        createNotificationChannel();
        switch (item.getItemId()){
            case R.id.action_create:
                //Starts Note Activity in the other activity so you write your note
                Intent newNoteActivity = new Intent(this, NoteActivity.class);
                startActivity(newNoteActivity);
                break;
            case R.id.action_website:
                Intent Website = new Intent(this, Website.class);
                startActivity(Website);
                break;
            case R.id.action_reminder:

                Toast.makeText(MainActivity.this, "Reminder Set!...", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long currentTime = System.currentTimeMillis();

                //twenty hours in millisecconds
                long twentyHours = 1000*60*60*20;

                assert alarmManager != null;
                alarmManager.set(
                        AlarmManager.RTC_WAKEUP, currentTime + twentyHours, pendingIntent);
                break;
        }

        return true;
    }
    // here i create the notification channel for the notification im making
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotesChannel";
            String description = "Channel for Notes reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListViewNotes.setAdapter(null);
        ArrayList<Note> notes = Utilities.getAllSavedNotes(this);

        if(notes == null || notes.size() == 0){
            Toast.makeText(this,"You have no Notes", Toast.LENGTH_SHORT).show();
            return;
        }else {
            NoteAdapter na = new NoteAdapter(this, R.layout.item_note, notes);
            mListViewNotes.setAdapter(na);
            mListViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String filename = ((Note)mListViewNotes.getItemAtPosition(position)).getDateTime() + Utilities.FILE_EXTENSION;
                    Intent viewNoteIntent = new Intent(getApplicationContext(), NoteActivity.class);
                    viewNoteIntent.putExtra("NOTE_FILE", filename);
                    startActivity(viewNoteIntent);
                }
            });
        }
    }
}
