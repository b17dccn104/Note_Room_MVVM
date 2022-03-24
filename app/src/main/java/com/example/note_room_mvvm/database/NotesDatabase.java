package com.example.note_room_mvvm.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.note_room_mvvm.dao.NotesDao;
import com.example.note_room_mvvm.model.Notes;

@Database(entities = {Notes.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();
    public static NotesDatabase INSTANCE;

    public static NotesDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                       context.getApplicationContext(),
                       NotesDatabase.class,
                      "Notes")
                      .allowMainThreadQueries()
                      .build();
        }
        return INSTANCE;
    }
}
