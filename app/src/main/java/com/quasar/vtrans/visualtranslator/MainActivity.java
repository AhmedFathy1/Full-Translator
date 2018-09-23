package com.quasar.vtrans.visualtranslator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

        public static final String PICK_INPUT_LANGUAGE_KEY = "com.quasar.vtrans.visualtranslator.MAINACTIVITY.INPUTLANGUAGE";
        public static final String PICK_OUTPUT_LANGUAGE_KEY = "com.quasar.vtrans.visualtranslator.MAINACTIVITY.OUTPUTLANGUAGE";
        public static final int INPUT_REQUEST_CODE = 4;
        public static final int OUTPUT_REQUEST_CODE = 5;
        public static final int ACTIVITY_RECORD_SOUND = 0;
        private Button mInputLanguageButton;
        private Button mOutputLanguageButton;
        private EditText mWordsInputEditText;
        private EditText mTranslatedWordsEditText;
        private ImageView mClearImageView;
        private ImageView mStartImageView;
        private boolean mIsButtonClicked = false;
        private TextView mCameraTextView;
        private TextView mVoiceTextView;
        private TextView mConversationTextView;
        private TextView mImageTextView;
        private Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        mInputLanguageButton = findViewById(R.id.input_language_button);
        mInputLanguageButton.setOnClickListener(this);

        mOutputLanguageButton =findViewById(R.id.output_language_button);
        mOutputLanguageButton.setOnClickListener(this);

        mTranslatedWordsEditText = findViewById(R.id.translated_edit_text);
        mTranslatedWordsEditText.setVisibility(View.INVISIBLE);

        mClearImageView = findViewById(R.id.clear_imageView);
        mClearImageView.setOnClickListener(this);


        mWordsInputEditText = findViewById(R.id.words_input_edit_text);
        mWordsInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTranslatedWordsEditText.setVisibility(View.VISIBLE);
                mStartImageView.setVisibility(View.VISIBLE);
                mTranslatedWordsEditText.setText(mWordsInputEditText.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mStartImageView = findViewById(R.id.star_image_view);
        mStartImageView.setOnClickListener(this);
        mStartImageView.setVisibility(View.INVISIBLE);

        mCameraTextView = findViewById(R.id.camera_textView);
        mCameraTextView.setOnClickListener(this);

        mVoiceTextView = findViewById(R.id.voice_textView);
        mVoiceTextView.setOnClickListener(this);

        mConversationTextView = findViewById(R.id.conversation_textView);
        mConversationTextView.setOnClickListener(this);

        mImageTextView = findViewById(R.id.image_text_view);
        mImageTextView.setOnClickListener(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.phrasebook_item_menu:
                StartPhraseBookActivity();
                return true;
            case R.id.offline_item_menu:
                StartOfflineLanguagesActivity();
                return true;
                case R.id.checkable_item_menu:
                    return true;
            case R.id.settings_item_menu:
                StartSettingsActivity();
                return true;
            case R.id.feedback_help_item_menu:
                StartFeedBackAndHelpActivity();
                return true;
            case R.id.about_item_menu:
                StartAboutActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.input_language_button:
                StartPickLanguageActivityForInputLanguage();
                break;

            case R.id.output_language_button:
                StartPickLanguageActivityForOutputLanguage();
                break;

            case R.id.clear_imageView:
                mWordsInputEditText.getText().clear();
                break;

            case R.id.star_image_view:
                mIsButtonClicked = !mIsButtonClicked;
                ClickStarImageView();
                break;

            case R.id.camera_textView:
                StartCamera(context);
            break;

            case R.id.voice_textView:
                StartVoiceListener();
                break;
            case R.id.image_text_view:
                StartTextRecognistionActivity();
                break;

        }

    }

    private void StartPickLanguageActivityForInputLanguage()
    {
        Intent intent = new Intent(MainActivity.this,PickInputAndOutputLanguageActivity.class);
        intent.putExtra("requestCode", INPUT_REQUEST_CODE);
        startActivityForResult(intent,INPUT_REQUEST_CODE);
    }

    private void StartPickLanguageActivityForOutputLanguage()
    {
        Intent intent = new Intent(MainActivity.this,PickInputAndOutputLanguageActivity.class);
        intent.putExtra("requestCode", OUTPUT_REQUEST_CODE);
        startActivityForResult(intent,OUTPUT_REQUEST_CODE);
    }

    private void StartCamera(Context context)
    {
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            PackageManager pm = context.getPackageManager();

            final ResolveInfo mInfo = pm.resolveActivity(i, 0);

            Intent intent = new Intent();
            intent.setComponent(new ComponentName(mInfo.activityInfo.packageName, mInfo.activityInfo.name));
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(MainActivity.this,"Unable to start Camera",Toast.LENGTH_SHORT).show();
        }
    }

    private void StartVoiceListener () {
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, ACTIVITY_RECORD_SOUND);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode)
        {
            case 4:
                if(resultCode == Activity.RESULT_OK )
                 {
                     String mInputPickLanguageResult = data.getStringExtra(PICK_INPUT_LANGUAGE_KEY);
                     if(mInputLanguageButton!=null)
                     mInputLanguageButton.setText(mInputPickLanguageResult);
                 }
                break;
            case 5:
                if(resultCode == Activity.RESULT_OK)
                {
                    String mOutputPickLanguageResult = data.getStringExtra(PICK_OUTPUT_LANGUAGE_KEY);
                    if(mOutputLanguageButton!=null)
                        mOutputLanguageButton.setText(mOutputPickLanguageResult);
                }
                break;
            case 0:

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("MyInput", mInputLanguageButton.getText().toString());
        savedInstanceState.putString("My Output", mOutputLanguageButton.getText().toString());

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        mInputLanguageButton.setText(savedInstanceState.getString("MyInput"));
        mOutputLanguageButton.setText(savedInstanceState.getString("My Output"));

    }


    private void StartPhraseBookActivity() {
        Intent intent = new Intent(MainActivity.this,PhraseBookActivity.class);
        startActivity(intent);
    }

    private void StartOfflineLanguagesActivity()
    {
        Intent intent = new Intent(MainActivity.this,OfflineLanguages.class);
        startActivity(intent);
    }

    private void StartSettingsActivity()
    {
        Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(intent);
    }

    private void StartFeedBackAndHelpActivity()
    {
        Intent intent = new Intent(MainActivity.this,FeedBackAndHelpActivity.class);
        startActivity(intent);
    }

    private void StartAboutActivity()
    {
        Intent intent = new Intent(MainActivity.this,AboutActivity.class);
        startActivity(intent);
    }

    private void StartTextRecognistionActivity()
    {
        Intent intent = new Intent(MainActivity.this,TextRecognition.class);
        startActivity(intent);
    }

    private void ClickStarImageView()
    {
        if (TextUtils.isEmpty(mWordsInputEditText.getText())) {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        } else {

           Word word = new Word(mWordsInputEditText.getText().toString(),mTranslatedWordsEditText.getText().toString());
           WordRoomDatabase.getDatabase(this.getApplication()).insert(word);
        }
    }




}
