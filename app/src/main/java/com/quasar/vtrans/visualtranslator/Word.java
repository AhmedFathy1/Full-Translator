package com.quasar.vtrans.visualtranslator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;
    @ColumnInfo(name = "word2")
    private String mWord_2;

    public Word(String word, String word_2) {
        this.mWord = word;
        this.mWord_2 = word_2;

    }

    public String getWord() {
        return this.mWord;
    }

    public String getWord_2 ()
    {
        return this.mWord_2;
    }


    @Dao
    public interface WordDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(Word word);

        @Query("DELETE FROM word_table")
        void deleteAll();

        @Query("SELECT * from word_table ORDER BY word ASC")
        LiveData<List<Word>> getAllWords();;

        @Delete
        void Delete(Word word);
    }
}