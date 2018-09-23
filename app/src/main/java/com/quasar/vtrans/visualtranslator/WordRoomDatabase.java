package com.quasar.vtrans.visualtranslator;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, version = 2)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract Word.WordDao wordDao();

    private static WordRoomDatabase INSTANCE;


    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    public void insert (Word word) {
        new GeneralAsyncTask(wordDao()::insert).execute(word);
    }



    public void Delete(Word word)
    {
        new GeneralAsyncTask(wordDao()::Delete).execute(word);
    }
    private static class GeneralAsyncTask extends AsyncTask<Word, Void, Void> {
        Consumer<Word> consumer;
        GeneralAsyncTask(Consumer<Word> consumer) {
            this.consumer=consumer;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            consumer.accept(params[0]);
            return null;
        }
    }

}