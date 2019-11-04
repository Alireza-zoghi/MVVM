package com.alirezazoghi.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.alirezazoghi.mvvm.model.Note;
import com.alirezazoghi.mvvm.R;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ADD =
            "com.alirezazoghi.mvvm.EXTRA_ADD";

    public static final String EXTRA_EDIT =
            "com.alirezazoghi.mvvm.EXTRA_EDIT";

    private EditText editTextTitle, editTextDescription;
    private NumberPicker numberPicker;
    private boolean edit_mode = false;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        editTextTitle = findViewById(R.id.et_title);
        editTextDescription = findViewById(R.id.et_description);
        numberPicker = findViewById(R.id.np_Priority);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        if (intent.hasExtra(EXTRA_EDIT)) {
            toolbar.setTitle("Edit Note");
            Note note = getIntent().getParcelableExtra(EXTRA_EDIT);
            editTextTitle.setText(note.getTitle());
            editTextDescription.setText(note.getDescription());
            numberPicker.setValue(note.getPriority());
            id=note.getId();
            edit_mode = true;
        } else {
            toolbar.setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        Note note = new Note(title, description, priority);
        if (!edit_mode) {
            data.putExtra(EXTRA_ADD, note);
        } else {
            note.setId(id);
            data.putExtra(EXTRA_EDIT, note);
        }
        setResult(RESULT_OK, data);
        finish();
    }

}
