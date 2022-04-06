package com.example.note_room_mvvm.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.note_room_mvvm.R;
import com.example.note_room_mvvm.databinding.ActivityUpdateNotesBinding;
import com.example.note_room_mvvm.model.Notes;
import com.example.note_room_mvvm.util.KeyConstants;
import com.example.note_room_mvvm.viewmodel.NotesViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UpdateNotesActivity extends AppCompatActivity {
    /*
     * Area : Variable
     */
    private ActivityUpdateNotesBinding binding;
    private Notes notesUpdate;
    private NotesViewModel notesViewModel;
    private String priority = "";

    /*
     * Area : Function
     */
    private void getDataIntentNotes() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Notes notesIntent = bundle.getParcelable(getString(R.string.notes_item));
        if (notesIntent != null) {
            notesUpdate = notesIntent;
            setDataUpdateView(notesIntent);
            setPriorityInit();
            setImagePriorityInit();
        }
    }

    private void clickListener() {
        binding.redPriorityUpdate.setOnClickListener(view -> {
            setImageResourceRed();
            priority = KeyConstants.RED_PRIORITY;
        });
        binding.yellowPriorityUpdate.setOnClickListener(view -> {
            setImageResourceYellow();
            priority = KeyConstants.YELLOW_PRIORITY;
        });
        binding.greenPriorityUpdate.setOnClickListener(view -> {
            setImageResourceGreen();
            priority = KeyConstants.GREEN_PRIORITY;
        });
        binding.doneNotesUpdate.setOnClickListener(view -> {
            if (binding.titleUpdate.getText().toString().equals("")) {
                showSnackBar();
                binding.titleUpdate.setEnabled(false);
                binding.subTitleUpdate.setEnabled(false);
                binding.notesUpdate.setEnabled(false);
            } else {
                createNotes();
            }
        });
    }

    private void showSnackBar() {
        View view = findViewById(R.id.update_view);
        String message = getString(R.string.warning_message);
        Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.done_message), v -> {
            binding.titleUpdate.setEnabled(true);
            binding.subTitleUpdate.setEnabled(true);
            binding.notesUpdate.setEnabled(true);
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

    private void setImageResourceGreen() {
        binding.redPriorityUpdate.setImageResource(0);
        binding.yellowPriorityUpdate.setImageResource(0);
        binding.greenPriorityUpdate.setImageResource(R.drawable.ic_baseline_done_24);
    }

    private void setImageResourceRed() {
        binding.redPriorityUpdate.setImageResource(R.drawable.ic_baseline_done_24);
        binding.yellowPriorityUpdate.setImageResource(0);
        binding.greenPriorityUpdate.setImageResource(0);
    }

    private void setImageResourceYellow() {
        binding.redPriorityUpdate.setImageResource(0);
        binding.yellowPriorityUpdate.setImageResource(R.drawable.ic_baseline_done_24);
        binding.greenPriorityUpdate.setImageResource(0);
    }

    private void createNotes() {
        String titleUD= binding.titleUpdate.getText().toString();
        String subTitleUD = binding.subTitleUpdate.getText().toString();
        String notesUD = binding.notesUpdate.getText().toString();
        Notes notes = new Notes();
        notes.noteId = notesUpdate.noteId;
        notes.noteTitle = titleUD;
        notes.noteSubtitle = subTitleUD;
        notes.note = notesUD;
        notes.noteDate = notesUpdate.noteDate;
        notes.notePriority = priority;
        updateNotes(notes);
    }

    private void updateNotes(Notes notes) {
        notesViewModel.updateNotes(notes);
        sentListNotesToMainActivity();
    }

    private void sentListNotesToMainActivity() {
        notesViewModel.getAllNotes().observe(this, notes -> {
            ArrayList<Notes> notesArrayList = new ArrayList<>(notes);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.list_notes), notesArrayList);
            Intent intent = new Intent(UpdateNotesActivity.this, MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void setImagePriorityInit() {
        switch (priority) {
            case KeyConstants.GREEN_PRIORITY:
                setImageResourceGreen();
                break;
            case KeyConstants.YELLOW_PRIORITY:
                setImageResourceYellow();
                break;
            case KeyConstants.RED_PRIORITY:
                setImageResourceRed();
                break;
        }
    }

    private void setPriorityInit() {
        priority = notesUpdate.notePriority;
    }

    private void setDataUpdateView(Notes notes) {
        binding.setNotes(notes);
    }

    /*
     * Area : Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_update_notes);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        getDataIntentNotes();
        clickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_delete) {
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNotesActivity.this,
                    R.style.BottomDialogStyle);
            View view = LayoutInflater.from(UpdateNotesActivity.this)
                    .inflate(R.layout.delete_notes_sheet,findViewById(R.id.bottom_sheet));
            sheetDialog.setContentView(view);
            TextView noDelete, yesDelete;
            noDelete = view.findViewById(R.id.delete_no);
            yesDelete = view.findViewById(R.id.delete_yes);
            noDelete.setOnClickListener(v -> sheetDialog.dismiss());
            yesDelete.setOnClickListener(v -> {
                notesViewModel.deleteNotes(notesUpdate);
                sentListNotesToMainActivity();
            });
            sheetDialog.show();
        }
        return true;
    }
}