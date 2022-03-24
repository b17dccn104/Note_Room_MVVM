package com.example.note_room_mvvm.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

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

public class UpdateNotes extends AppCompatActivity {
    private ActivityUpdateNotesBinding binding;
    private Notes notesUpdate;
    private NotesViewModel notesViewModel;
    private String priority = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_update_notes);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        getDataIntentNotes();
        clickListener();
    }

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
        clickUpdateNotes();
        clickGreenPriority();
        clickYellowPriority();
        clickRedPriority();
    }

    private void clickRedPriority() {
        binding.redPriorityUpdate.setOnClickListener(view -> {
            setImageResourceRed();
            priority = KeyConstants.RED_PRIORITY;
        });
    }

    private void clickYellowPriority() {
        binding.yellowPriorityUpdate.setOnClickListener(view -> {
            setImageResourceYellow();
            priority = KeyConstants.YELLOW_PRIORITY;
        });
    }

    private void clickGreenPriority() {
        binding.greenPriorityUpdate.setOnClickListener(view -> {
            setImageResourceGreen();
            priority = KeyConstants.GREEN_PRIORITY;
        });
    }

    private void clickUpdateNotes() {
        binding.doneNotesUpdate.setOnClickListener(view -> createNotes());
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
        finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_delete) {
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNotes.this,
                    R.style.BottomDialogStyle);
            View view = LayoutInflater.from(UpdateNotes.this)
                    .inflate(R.layout.delete_notes_sheet,(ConstraintLayout)  findViewById(R.id.bottom_sheet));
            sheetDialog.setContentView(view);
            TextView noDelete, yesDelete;
            noDelete = view.findViewById(R.id.delete_no);
            yesDelete = view.findViewById(R.id.delete_yes);
            noDelete.setOnClickListener(v -> sheetDialog.dismiss());
            yesDelete.setOnClickListener(v -> {
                notesViewModel.deleteNotes(notesUpdate.noteId);
                finish();
            });
            sheetDialog.show();
        }
        return true;
    }
}