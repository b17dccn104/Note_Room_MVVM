package com.example.note_room_mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Notes")
public class Notes implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    public int noteId;
    public String note;
    public String noteTitle;
    public String noteSubtitle;
    public String noteDate;
    public String notePriority;

    public Notes() {
    }

    protected Notes(Parcel in) {
        noteId = in.readInt();
        note = in.readString();
        noteTitle = in.readString();
        noteSubtitle = in.readString();
        noteDate = in.readString();
        notePriority = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(noteId);
        parcel.writeString(note);
        parcel.writeString(noteTitle);
        parcel.writeString(noteSubtitle);
        parcel.writeString(noteDate);
        parcel.writeString(notePriority);
    }
}
