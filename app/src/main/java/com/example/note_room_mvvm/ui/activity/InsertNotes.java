package com.example.note_room_mvvm.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.note_room_mvvm.R;
import com.example.note_room_mvvm.databinding.ActivityInsertNotesBinding;
import com.example.note_room_mvvm.model.Notes;
import com.example.note_room_mvvm.util.KeyConstants;
import com.example.note_room_mvvm.viewmodel.NotesViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertNotes extends AppCompatActivity {
    private ActivityInsertNotesBinding binding;
    private NotesViewModel notesViewModel;
    private String priority = KeyConstants.GREEN_PRIORITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        clickListener();
    }

    private void clickListener() {
        clickDoneNotes();
        clickGreenPriority();
        clickYellowPriority();
        clickRedPriority();
    }

    private void clickRedPriority() {
        binding.redPriorityInsert.setOnClickListener(view -> {
            binding.redPriorityInsert.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriorityInsert.setImageResource(0);
            binding.greenPriorityInsert.setImageResource(0);
            priority = KeyConstants.RED_PRIORITY;
        });
    }

    private void clickYellowPriority() {
        binding.yellowPriorityInsert.setOnClickListener(view -> {
            binding.redPriorityInsert.setImageResource(0);
            binding.yellowPriorityInsert.setImageResource(R.drawable.ic_baseline_done_24);
            binding.greenPriorityInsert.setImageResource(0);
            priority = KeyConstants.YELLOW_PRIORITY;
        });
    }

    private void clickGreenPriority() {
        binding.greenPriorityInsert.setOnClickListener(view -> {
            binding.redPriorityInsert.setImageResource(0);
            binding.yellowPriorityInsert.setImageResource(0);
            binding.greenPriorityInsert.setImageResource(R.drawable.ic_baseline_done_24);
            priority = KeyConstants.GREEN_PRIORITY;
        });
    }

    private void clickDoneNotes() {
        binding.buttonDoneNotes.setOnClickListener(view ->  {
            if (binding.titleInsert.getText().toString().equals("")){
                showSnackBar();
                binding.titleInsert.setEnabled(false);
            } else {
                createNotes();
                finish();
            }
        });
    }

    public void showSnackBar() {
        View view = findViewById(R.id.insert_view);
        String message = getString(R.string.warning_message);
        Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.done_message), v -> {
            binding.titleInsert.setEnabled(true       );
            snackbar.dismiss();
        });
        snackbar.setActionTextColor(Color.RED);
        View snackBarView = snackbar.getView();
        TextView textView = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        textView.setAllCaps(true);
        textView.setTextSize(14);
        snackBarView.setBackgroundColor(Color.WHITE);
        snackbar.show();
    }

    private void createNotes() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy");
        String dateCurrent = simpleDateFormat.format(date.getTime());
        String notesTitle = binding.titleInsert.getText().toString().trim();
        String notesSubTile = binding.subTitleInsert.getText().toString().trim();
        String notes = binding.notesInsert.getText().toString().trim();
        Notes note = new Notes();
        note.noteTitle = notesTitle;
        note.noteSubtitle = notesSubTile;
        note.note = notes;
        note.noteDate = dateCurrent;
        note.notePriority = priority;
        insertNote(note);
    }

    private void insertNote(Notes note) {
        notesViewModel.insertNotes(note);
    }

}