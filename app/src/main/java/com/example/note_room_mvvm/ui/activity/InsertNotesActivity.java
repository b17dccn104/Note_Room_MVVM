package com.example.note_room_mvvm.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.Date;
public class InsertNotesActivity extends AppCompatActivity {
    /*
     * Area : Variable
     */
    private ActivityInsertNotesBinding binding;
    private NotesViewModel notesViewModel;
    private String priority = KeyConstants.GREEN_PRIORITY;

    /*
     * Area : Function
     */
    private void clickListener() {
        binding.redPriorityInsert.setOnClickListener(view -> {
            binding.redPriorityInsert.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriorityInsert.setImageResource(0);
            binding.greenPriorityInsert.setImageResource(0);
            priority = KeyConstants.RED_PRIORITY;
        });
        binding.yellowPriorityInsert.setOnClickListener(view -> {
            binding.redPriorityInsert.setImageResource(0);
            binding.yellowPriorityInsert.setImageResource(R.drawable.ic_baseline_done_24);
            binding.greenPriorityInsert.setImageResource(0);
            priority = KeyConstants.YELLOW_PRIORITY;
        });
        binding.greenPriorityInsert.setOnClickListener(view -> {
            binding.redPriorityInsert.setImageResource(0);
            binding.yellowPriorityInsert.setImageResource(0);
            binding.greenPriorityInsert.setImageResource(R.drawable.ic_baseline_done_24);
            priority = KeyConstants.GREEN_PRIORITY;
        });
        binding.buttonDoneNotes.setOnClickListener(view ->  {
            if (binding.titleInsert.getText().toString().equals("")){
                showSnackBar();
                binding.titleInsert.setEnabled(false);
                binding.subTitleInsert.setEnabled(false);
                binding.notesInsert.setEnabled(false);
            } else {
                createNotes();
                sentListNotesToActivity();
            }
        });
    }

    private void sentListNotesToActivity() {
        notesViewModel.getAllNotes().observe(this, notes -> {
            ArrayList<Notes> notesArrayList = new ArrayList<>(notes);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.list_notes), notesArrayList);
            Intent intent = new Intent(InsertNotesActivity.this, MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void showSnackBar() {
        View view = findViewById(R.id.insert_view);
        String message = getString(R.string.warning_message);
        Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.done_message), v -> {
            binding.titleInsert.setEnabled(true);
            binding.subTitleInsert.setEnabled(true);
            binding.notesInsert.setEnabled(true);
            snackbar.dismiss();
        });
        snackbar.setActionTextColor(Color.RED);
        View snackBarView = snackbar.getView();
        TextView textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
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

    /*
     * Area : Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        clickListener();
    }
}