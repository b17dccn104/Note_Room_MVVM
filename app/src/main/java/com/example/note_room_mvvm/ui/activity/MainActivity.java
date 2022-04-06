package com.example.note_room_mvvm.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.note_room_mvvm.R;
import com.example.note_room_mvvm.databinding.ActivityMainBinding;
import com.example.note_room_mvvm.model.Notes;
import com.example.note_room_mvvm.ui.adapter.NotesAdapter;
import com.example.note_room_mvvm.viewmodel.NotesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /*
     * Area : Variable
     */
    private ActivityMainBinding binding;
    private NotesViewModel notesViewModel;
    private NotesAdapter notesAdapter;
    private List<Notes> filterNotesList;

    /*
     * Area : Function
     */
    private void inIt() {
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        observeGetAllNotesViewModel();
        selectedNoFilter();
    }

    private void clickListener() {
        binding.lowToHighFilter.setOnClickListener(view -> {
            selectedLowToHighFilter();
            observeFilterLowToHighNotesViewModel();
        });
        binding.highToLowFilter.setOnClickListener(view -> {
            selectedHighToLowFilter();
            observeFilterHighToLowNotesViewModel();
        });
        binding.noFilter.setOnClickListener(view -> {
            selectedNoFilter();
            observeGetAllNotesViewModel();
        });
        binding.buttonNewNotes.setOnClickListener(
                view -> startActivity(new Intent(MainActivity.this, InsertNotesActivity.class)));
    }

    private void observeGetAllNotesViewModel() {
        notesViewModel.getAllNotes().observe(this, notes -> {
            updateRecyclerViewNotes(notes);
            filterNotesList = notes;
         });
    }

    private void observeFilterHighToLowNotesViewModel() {
        notesViewModel.filterHighToLow().observe(this, notes -> {
            updateRecyclerViewNotes(notes);
            filterNotesList = notes;
        });
    }

    private void observeFilterLowToHighNotesViewModel() {
        notesViewModel.filterLowToHigh().observe(this, notes -> {
            updateRecyclerViewNotes(notes);
            filterNotesList = notes;
        });
    }

    private void selectedNoFilter() {
        binding.noFilter.setBackgroundResource(R.drawable.item_filter_selected);
        binding.highToLowFilter.setBackgroundResource(R.drawable.item_filter);
        binding.lowToHighFilter.setBackgroundResource(R.drawable.item_filter);
    }

    private void selectedHighToLowFilter() {
        binding.highToLowFilter.setBackgroundResource(R.drawable.item_filter_selected);
        binding.noFilter.setBackgroundResource(R.drawable.item_filter);
        binding.lowToHighFilter.setBackgroundResource(R.drawable.item_filter);
    }

    private void selectedLowToHighFilter() {
        binding.lowToHighFilter.setBackgroundResource(R.drawable.item_filter_selected);
        binding.highToLowFilter.setBackgroundResource(R.drawable.item_filter);
        binding.noFilter.setBackgroundResource(R.drawable.item_filter);
    }

    private void updateRecyclerViewNotes(List<Notes> notes) {
        binding.listNotesHome.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        notesAdapter = new NotesAdapter(notes, this);
        binding.listNotesHome.setAdapter(notesAdapter);
    }

    private void filterNotes(String s) {
        ArrayList<Notes> filterName = new ArrayList<>();
        for (Notes notes: this.filterNotesList) {
            if (notes.noteTitle.contains(s) || notes.noteSubtitle.contains(s)) {
                filterName.add(notes);
            }
        }
        this.notesAdapter.searchNotes(filterName);
    }

    /*
     * Area : Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<Notes> arrayList = bundle.getParcelableArrayList(getString(R.string.list_notes));
            updateRecyclerViewNotes(arrayList);
        }
        inIt();
        clickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_notes, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                filterNotes(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}