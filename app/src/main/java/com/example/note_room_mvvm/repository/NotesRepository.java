package com.example.note_room_mvvm.repository;

import android.app.Application;
import com.example.note_room_mvvm.dao.NotesDao;
import com.example.note_room_mvvm.database.NotesDatabase;
import com.example.note_room_mvvm.model.Notes;

import java.util.List;

public class NotesRepository {
    /*
     * Area : Variable
     */
    public NotesDao notesDao;

    /*
     * Area : Constructor
     */
    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
    }

    /*
     * Area : Function
     */
    public List<Notes> getAllNotes() {
        return notesDao.getAllNotes();
    }

    public List<Notes> filterHighToLow() {
        return notesDao.filterHighToLow();
    }

    public List<Notes> filterLowToHigh() {
        return notesDao.filterLowToHigh();
    }

    public  void insertNotes(Notes notes) {
        notesDao.insertNotes(notes);
    }

    public void deleteNotes(Notes notes) {
        notesDao.deleteNotes(notes);
    }

    public void updateNotes(Notes notes) {
        notesDao.updateNotes(notes);
    }

}
