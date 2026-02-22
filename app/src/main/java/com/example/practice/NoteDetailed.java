package com.example.practice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practice.diarydb.DiaryViewModel;
import com.example.practice.databinding.FragmentNoteDetailedBinding;

public class NoteDetailed extends Fragment {

    private static final String ARG_TITLE = "title";
    private FragmentNoteDetailedBinding binding;
    private DiaryViewModel viewModel;

    public NoteDetailed() {

    }

    public static NoteDetailed newInstance(String title) {
        NoteDetailed fragment = new NoteDetailed();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentNoteDetailedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);

        String title = requireArguments().getString(ARG_TITLE);

        viewModel = new ViewModelProvider(requireActivity()).get(DiaryViewModel.class);


        viewModel.getContent(title).observe(getViewLifecycleOwner(),note->{
            if (note == null){
                return;
            }
            binding.detaileTitle.setText(title);
            binding.detailContent.setText(note);
        });
    }


}