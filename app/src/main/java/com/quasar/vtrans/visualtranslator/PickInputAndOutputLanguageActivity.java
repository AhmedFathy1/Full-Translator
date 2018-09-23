package com.quasar.vtrans.visualtranslator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class PickInputAndOutputLanguageActivity extends AppCompatActivity implements LanguagesAdapter.ItemClickListener {
    public static final String PICK_INPUT_LANGUAGE_KEY = "com.quasar.vtrans.visualtranslator.MAINACTIVITY.INPUTLANGUAGE";
    public static final String PICK_OUTPUT_LANGUAGE_KEY = "com.quasar.vtrans.visualtranslator.MAINACTIVITY.OUTPUTLANGUAGE";
    LanguagesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_input_and_output_language);

        // data to populate the RecyclerView with
        ArrayList<String> languages = new ArrayList<>();
        languages.add("Arabic");
        languages.add("English");
        languages.add("Spanish");
        languages.add("Italian");
        languages.add("German");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.languages_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LanguagesAdapter(this, languages);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent returnIntent = new Intent();
        int requestCode = getIntent().getExtras().getInt("requestCode");
        if(requestCode == 4)
        {
            returnIntent.putExtra(PICK_INPUT_LANGUAGE_KEY,adapter.getItem(position));
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }

        else if (requestCode == 5)
        {
            returnIntent.putExtra(PICK_OUTPUT_LANGUAGE_KEY,adapter.getItem(position));
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }

    }
}
