package acer.example.com.audiowala;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    private Button btnStart;
    private Button btnStop;
    private TextView tvDisplay;
    private MediaRecorder mRecorder;
    private String mFileName = null;
    private int REQ_CODE_SPEECH_INPUT = 100;
    //ArrayList<String> result;
    String output = null;
    private StorageReference mStorage;
    ProgressDialog mProgressDialog;
    DatabaseReference dbRef;
    EditText etName;
    //FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnUpload);
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        //result = new ArrayList<String>();
        output = "";
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(this);
        dbRef = FirebaseDatabase.getInstance().getReference();
        etName = (EditText) findViewById(R.id.etName);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/recorded_audio.wav";
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //startRecording();
                askSpeechInput();
                //tvDisplay.setText("Started Recording!");
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopRecording();
                //tvDisplay.setText("Stopped Recording!");
                uploadText();
            }
        });
    }

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, Long.valueOf(10000));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 100:
            {
                if (resultCode == RESULT_OK && null != data)
                {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    output += result.get(0) + ". ";
                    tvDisplay.setText(output);
                    //uploadText();
                }
                break;
            }
        }
    }

    private void startRecording()
    {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try
        {
            mRecorder.prepare();
        } catch (IOException e)
        {
            Toast.makeText(this, "Failed to start recording!", Toast.LENGTH_SHORT).show();
        }

        mRecorder.start();
    }

    private void stopRecording()
    {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }



    void uploadText()
    {
        dbRef.child(etName.getText().toString()).setValue(output);
        /*mProgressDialog.setMessage("Uploading, please wait!");
        mProgressDialog.show();*/

        //StorageReference filepath = mStorage.child("audio").child("recorded_audio.wav");


    }

}
