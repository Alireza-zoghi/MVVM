package com.alirezazoghi.mvvm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alirezazoghi.mvvm.model.Note;
import com.alirezazoghi.mvvm.ViewModel.repository.NoteRepository;


import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;

    private NoteRepository getRepositoryInstance() {
        if (repository == null)
            repository = new NoteRepository(getApplication());
        return repository;
    }

    public NoteViewModel(@NonNull Application application) {
        super(application);
    }

    public void insert(Note note) {
        getRepositoryInstance().insert(note);
    }

    public void update(Note note) {
        getRepositoryInstance().update(note);
    }

    public void delete(Note note) {
        getRepositoryInstance().delete(note);
    }

    public void deleteAllNotes() {
        getRepositoryInstance().deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(boolean syncWhitServer) {
        return getRepositoryInstance().getAllNotes(syncWhitServer);
    }

}
