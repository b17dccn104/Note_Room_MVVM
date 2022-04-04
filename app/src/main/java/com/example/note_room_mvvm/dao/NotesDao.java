package com.example.note_room_mvvm.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.note_room_mvvm.model.Notes;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM Notes")
    List<Notes> getAllNotes();

    @Query("SELECT * FROM Notes ORDER BY notePriority DESC")
    List<Notes> filterHighToLow();

    @Query("SELECT * FROM Notes ORDER BY notePriority ASC")
    List<Notes> filterLowToHigh();

    @Insert
    void insertNotes(Notes... notes);

    @Query("DELETE FROM notes WHERE noteId = :noteId")
    void deleteNotes(int noteId);

    @Update
    void updateNotes(Notes notes);
}
