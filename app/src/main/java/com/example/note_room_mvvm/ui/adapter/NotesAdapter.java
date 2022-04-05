package com.example.note_room_mvvm.ui.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_room_mvvm.R;
import com.example.note_room_mvvm.databinding.ItemNotesBinding;
import com.example.note_room_mvvm.model.Notes;
import com.example.note_room_mvvm.ui.activity.UpdateNotes;
import com.example.note_room_mvvm.util.KeyConstants;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    /*
     * Area : Variable
     */
    private List<Notes> notesList;
    private final Context context;

    /*
     * Area : Constructor
     */
    public NotesAdapter(List<Notes> notesList, Context context) {
        this.notesList = notesList;
        this.context = context;
    }

    /*
     * Area : Function
     */
    @SuppressLint("NotifyDataSetChanged")
    public void searchNotes(List<Notes> filterNotesName) {
        this.notesList = filterNotesName;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemNotesBinding itemNotesBinding;

        public MyViewHolder(@NonNull ItemNotesBinding itemNotesBinding) {
            super(itemNotesBinding.getRoot());
            this.itemNotesBinding = itemNotesBinding;
        }

        private void bindData(int position) {
            Notes notes = notesList.get(position);
            itemNotesBinding.setNotes(notes);
            setViewToNotesPriority(notes);
            setOnClickItemNotes(notes);
            itemNotesBinding.executePendingBindings();
        }

        private void setOnClickItemNotes(Notes notes) {
            itemNotesBinding.itemNotes.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable(context.getString(R.string.notes_item), notes);
                Intent intent = new Intent(context, UpdateNotes.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            });
        }

        private void setViewToNotesPriority(Notes notes) {
            switch (notes.notePriority) {
                case KeyConstants.GREEN_PRIORITY:
                    itemNotesBinding.priorityItem.setBackgroundResource(R.drawable.green_priority_background);
                    break;
                case KeyConstants.YELLOW_PRIORITY:
                    itemNotesBinding.priorityItem.setBackgroundResource(R.drawable.yellow_priority_background);
                    break;
                case KeyConstants.RED_PRIORITY:
                    itemNotesBinding.priorityItem.setBackgroundResource(R.drawable.red_priority_background);
                    break;
            }
        }
    }

    /*
     * Area : Override
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNotesBinding itemNotesBinding = ItemNotesBinding.inflate(layoutInflater,parent,false);
        return new MyViewHolder(itemNotesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (notesList == null) {
            return;
        }
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        if (notesList != null) {
            return notesList.size();
        }
        return 0;
    }
}
