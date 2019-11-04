package com.alirezazoghi.mvvm.ViewModel.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alirezazoghi.mvvm.model.Note;
import com.alirezazoghi.mvvm.db.NoteDAO;
import com.alirezazoghi.mvvm.db.NoteDatabase;
import com.alirezazoghi.mvvm.myApplication;
import com.alirezazoghi.mvvm.remote.RetrofitProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteRepository {

    private Application context;
    private NoteDAO noteDAO;
    private NoteDatabase database;

    private MutableLiveData<List<Note>> liveData = new MutableLiveData<>();

    private NoteDAO getNoteDAOInstance() {
        if (noteDAO == null)
            noteDAO = getNoteDatabaseInstance().noteDAO();
        return noteDAO;
    }

    private NoteDatabase getNoteDatabaseInstance() {
        if (database == null)
            database = NoteDatabase.getInstance(context);
        return database;
    }

    public NoteRepository(Application application) {
        this.context = application;
    }

    public LiveData<List<Note>> getAllNotes(boolean syncWhitServer) {
        if (myApplication.hasNetwork() && syncWhitServer) {
            getAllNotesFromServer();
            return liveData;
        } else {
            return getNoteDAOInstance().getAllNotes();
        }
    }

    private void getAllNotesFromServer() {
        RetrofitProvider.getApi().getNotes().enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                liveData.setValue(null);
            }
        });
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(getNoteDAOInstance()).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(getNoteDAOInstance()).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(getNoteDAOInstance()).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(getNoteDAOInstance()).execute();
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        private InsertNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        private UpdateNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        private DeleteNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDAO noteDAO;

        private DeleteAllNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.deleteAllNotes();
            return null;
        }
    }

}
