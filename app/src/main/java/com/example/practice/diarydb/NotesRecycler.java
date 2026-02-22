package com.example.practice.diarydb;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;
import com.example.practice.databinding.NotesRecyclerItemBinding;

import java.util.ArrayList;

public class NotesRecycler extends RecyclerView.Adapter<NotesRecycler.diaryViewholder> {


    public interface OnNoteClickListener {
        void onNoteClick(String title);
    }


    private ArrayList<DiaryEntity> notes;
    private OnNoteClickListener listener;


    public NotesRecycler(ArrayList<DiaryEntity> notes, OnNoteClickListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public diaryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NotesRecyclerItemBinding binding =
               DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.notes_recycler_item,parent,false);

        return new diaryViewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull diaryViewholder holder, int position) {

        DiaryEntity entity = notes.get(position);

        holder.binding.setDiaryentity(entity);
        holder.binding.executePendingBindings();

        holder.binding.getRoot().setOnClickListener(v -> {
            listener.onNoteClick(entity.getTitle());
        });
    }

    @Override
    public int getItemCount() {
        if (notes != null){
            return notes.size();
        }
        else {
        return 0;
        }
    }

    public void removeItem(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
    }

    static class diaryViewholder extends RecyclerView.ViewHolder{
        final NotesRecyclerItemBinding binding;
        diaryViewholder(NotesRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
