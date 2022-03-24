package com.example.note_room_mvvm.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.note_room_mvvm.repository.NotesRepository;
import com.example.note_room_mvvm.model.Notes;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    public NotesRepository notesRepository;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> filterHighToLow;
    public LiveData<List<Notes>> filterLowToHigh;

    public NotesViewModel(Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
        getAllNotes = notesRepository.getAllNotes;
        filterHighToLow = notesRepository.filterHighToLow;
        filterLowToHigh = notesRepository.filterLowToHigh;
    }

    public void insertNotes(Notes notes) {
        notesRepository.insertNotes(notes);
    }

    public void deleteNotes(int notesId) {
        notesRepository.deleteNotes(notesId);
    }

    public void updateNotes(Notes notes) {
        notesRepository.updateNotes(notes);
    }

}
