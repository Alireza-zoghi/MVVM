package com.alirezazoghi.mvvm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alirezazoghi.mvvm.model.Note;
import com.alirezazoghi.mvvm.ViewModel.repository.NoteRepository;


import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    public LiveData<List<Note>> liveData;

    public void init() {
        if (liveData != null)
            return;
        repository = NoteRepository.getInstance(getApplication());
        liveData = repository.getAllNotes();
    }

    public NoteViewModel(@NonNull Application application) {
        super(application);
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return liveData;
    }

}
