package com.example.note_room_mvvm.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.note_room_mvvm.dao.NotesDao;
import com.example.note_room_mvvm.database.NotesDatabase;
import com.example.note_room_mvvm.model.Notes;

import java.util.List;

public class NotesRepository {
    public NotesDao notesDao;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> filterHighToLow;
    public LiveData<List<Notes>> filterLowToHigh;

    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        getAllNotes = notesDao.getAllNotes();
        filterHighToLow = notesDao.filterHighToLow();
        filterLowToHigh = notesDao.filterLowToHigh();
    }

    public  void insertNotes(Notes notes) {
        notesDao.insertNotes(notes);
    }

    public void deleteNotes(int id) {
        notesDao.deleteNotes(id);
    }

    public void updateNotes(Notes notes) {
        notesDao.updateNotes(notes);
    }

}
