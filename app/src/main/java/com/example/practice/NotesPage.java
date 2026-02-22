package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.databinding.ActivityNotesPageBinding;
import com.example.practice.diarydb.DiaryEntity;
import com.example.practice.diarydb.DiaryViewModel;
import com.example.practice.diarydb.NotesRecycler;

import java.util.ArrayList;

public class NotesPage extends AppCompatActivity {

    private ActivityNotesPageBinding binding;
    private DiaryViewModel viewModel;
    private NotesRecycler adapter;
    private ArrayList<DiaryEntity> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes_page);

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Username")) {
            String user = intent.getStringExtra("Username");
            binding.textView.setText("Hello " + user);
        }

        RecyclerView recyclerView = binding.Recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        notes = new ArrayList<>();
        adapter = new NotesRecycler(notes, this::onClickDetailed);
        recyclerView.setAdapter(adapter);

        enableDeleteOnCLick(recyclerView);

        viewModel = new ViewModelProvider(this).get(DiaryViewModel.class);

        binding.setDiarycontent(this);

        viewModel.getAllTitle().observe(this, list -> {

            if (list == null) return;

            notes.clear();
            notes.addAll(list);
            adapter.notifyDataSetChanged();
        });

        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {

                        if (binding.fragmentContainer.getVisibility() == View.VISIBLE) {

                            binding.fragmentContainer.setVisibility(View.GONE);
                            binding.Recyclerview.setVisibility(View.VISIBLE);

                            getSupportFragmentManager().popBackStack();

                        } else {
                            setEnabled(false);
                            getOnBackPressedDispatcher().onBackPressed();
                        }
                    }
                });
    }

    private void onClickDetailed(String title) {

        binding.Recyclerview.setVisibility(View.GONE);
        binding.fragmentContainer.setVisibility(View.VISIBLE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.fragmentContainer,
                        NoteDetailed.newInstance(title))
                .addToBackStack(null)
                .commit();
    }

    public void onclickWrite(View view) {

        Intent intent = new Intent(this, CreateNote.class);
        startActivity(intent);
    }

    public void onclickContact(View view){
        Intent intent  = new Intent(this, profile.class);
        startActivity(intent);
    }

    private void enableDeleteOnCLick(RecyclerView recyclerView){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallBack =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        DiaryEntity noteToDelete = notes.get(position);

                        viewModel.delete(noteToDelete);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}