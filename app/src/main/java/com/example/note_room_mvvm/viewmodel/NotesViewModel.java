package com.example.note_room_mvvm.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.note_room_mvvm.repository.NotesRepository;
import com.example.note_room_mvvm.model.Notes;
import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    /*
     * Area : Variable
     */
    public NotesRepository notesRepository;
    public MutableLiveData<List<Notes>> allNotes;
    public MutableLiveData<List<Notes>> highToLowNotes;
    public MutableLiveData<List<Notes>> lowToHighNotes;

    /*
     * Area : Constructor
     */
    public NotesViewModel(Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
    }

    /*
     * Area : Function
     */
    public MutableLiveData<List<Notes>> getAllNotes() {
        allNotes = new MutableLiveData<>();
        allNotes.postValue(notesRepository.getAllNotes());
        return allNotes;
    }

    public MutableLiveData<List<Notes>> filterHighToLow() {
        highToLowNotes = new MutableLiveData<>();
        highToLowNotes.postValue(notesRepository.filterHighToLow());
        return highToLowNotes;
    }

    public MutableLiveData<List<Notes>> filterLowToHigh() {
        lowToHighNotes = new MutableLiveData<>();
        lowToHighNotes.postValue(notesRepository.filterLowToHigh());
        return lowToHighNotes;
    }

    public void insertNotes(Notes notes) {
        notesRepository.insertNotes(notes);
    }

    public void deleteNotes(Notes notes) {
        notesRepository.deleteNotes(notes);
    }

    public void updateNotes(Notes notes) {
        notesRepository.updateNotes(notes);
    }

}
