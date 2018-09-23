package com.quasar.vtrans.visualtranslator;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class WordRepository {

    private WordRoomDatabase db;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        db = WordRoomDatabase.getDatabase(application);
        mAllWords = db.wordDao().getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }


    public void insert (Word word) {
        db.insert(word);
    }
}