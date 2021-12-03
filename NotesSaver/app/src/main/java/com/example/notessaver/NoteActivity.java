package com.example.notessaver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


// in this class I work on the Activities when editing the note
public class NoteActivity extends AppCompatActivity {
    private EditText mEtTitle;
    private EditText mEtContent;
    private String mNoteFileName;
    private Note mLoadedNote = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mEtContent = (EditText) findViewById(R.id.note_et_content);
        mEtTitle = (EditText) findViewById(R.id.note_et_title);
        mNoteFileName = getIntent().getStringExtra("NOTE_FILE");
        if(mNoteFileName != null && !mNoteFileName.isEmpty()) {
            mLoadedNote = Utilities.getNoteByName(this,mNoteFileName);

            if(mLoadedNote != null){
                mEtTitle.setText(mLoadedNote.getTitle());
                mEtContent.setText((mLoadedNote.getContent()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_note_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_note_save:
                saveNote();
                break;
            case R.id.action_note_delete:
                deleteNote();
            break;
        }
        return true;
    }

    //here is when the delete button is clicked, and a dialog is made to ask if you are sure you want to delete the note
    private  void deleteNote() {
        if(mLoadedNote == null){
            finish();
        }else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Are you sure?").setMessage("You are about to delete this note!").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    Utilities.deleteNotes(getApplicationContext(), mLoadedNote.getDateTime() + Utilities.FILE_EXTENSION);
                    Toast.makeText(getApplicationContext(), "Note Deteted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).setNegativeButton("NO", null).setCancelable(false);
            dialog.show();

        }
    }
    //here the note is saved and creates a small message that the note has been saved
    private  void saveNote() {
        Note note;
        if(mLoadedNote == null) {
        note = new Note(System.currentTimeMillis(), mEtTitle.getText().toString(), mEtContent.getText().toString());
        } else{
            note = new Note(mLoadedNote.getDateTime(), mEtTitle.getText().toString(), mEtContent.getText().toString());
            }

        if(Utilities.saveNote(this, note)) {
            Toast.makeText(NoteActivity.this, "Note Saved!", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(NoteActivity.this, "Note not Saved!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
