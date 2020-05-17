package tai.nk.learnenglish;

import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Locale;

public class Cai_Dat extends AppCompatActivity {
    ConstraintLayout layout;
    SeekBar sb_pitch,sb_spRate;
    EditText edt_test;
    ImageButton back,img_speak;
    Float pitch,sprate;
    boolean xn;
    SharedPreferences save;
    SharedPreferences.Editor edit;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai__dat);
        addConTrols();
        sb_pitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pitch = sb_pitch.getProgress()/(50f);
                edit.putFloat("PITCH",pitch);
                edit.commit();
            }
        });
        sb_spRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sprate = sb_spRate.getProgress()/(50f);
                edit.putFloat("SPRATE",sprate);
                edit.commit();
            }
        });
        img_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.setSpeechRate(sprate);
                textToSpeech.setPitch(pitch);
                if (textToSpeech.isSpeaking()){textToSpeech.stop();}
                textToSpeech.speak(edt_test.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void addConTrols() {
        sb_pitch = (SeekBar) findViewById(R.id.seekBar_pitch);
        sb_spRate = (SeekBar) findViewById(R.id.seekBar_speechRate);
        edt_test = (EditText) findViewById(R.id.edt_testSpeak);
        back = (ImageButton) findViewById(R.id.TL_img_Back);
        img_speak = (ImageButton) findViewById(R.id.TL_img_testSpeak);
        layout = (ConstraintLayout) findViewById(R.id.conly_PhatAmTuyChinh);

        textToSpeech = new TextToSpeech(Cai_Dat.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(Cai_Dat.this, R.string.Loi_Phat_Am, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        save = getSharedPreferences("DATA",MODE_PRIVATE);
        edit = save.edit();
        pitch = save.getFloat("PITCH",1.0f);
        sprate = save.getFloat("SPRATE", 1.0f);
        sb_spRate.setProgress((int) (pitch*(50f)));
        sb_pitch.setProgress((int) (sprate*(50f)));
    }
}
