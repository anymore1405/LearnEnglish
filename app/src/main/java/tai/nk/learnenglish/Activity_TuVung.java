package tai.nk.learnenglish;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Activity_TuVung extends AppCompatActivity {
    ListView lv_TuVung;
    ArrayList<TuVung> list;
    ImageButton back;
    SQLiteDatabase database;
    Cursor cursor;
    TextToSpeech textToSpeech;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tu_vung);
        addConTrols();
        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);
        textToSpeech = new TextToSpeech(Activity_TuVung.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(Activity_TuVung.this, getString(R.string.Loi_Phat_Am), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        textToSpeech.setPitch(sharedPreferences.getFloat("PITCH", 1.0f));
        textToSpeech.setSpeechRate(sharedPreferences.getFloat("SPRATE", 1.0f));

        lv_TuVung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                speak(list.get(position).TA);
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
        lv_TuVung = (ListView) findViewById(R.id.lv_TuVung);
        back = (ImageButton) findViewById(R.id.TV_back);
        list = new ArrayList<>();
        database = DataSQLite.initDatabase(Activity_TuVung.this,getString(R.string.fileSQLite));
        cursor = database.rawQuery("SELECT * FROM TU_VUNG", null);
        for (int i =0;i < cursor.getCount();i++){
            cursor.moveToPosition(i);
            list.add(new TuVung(cursor.getString(0),cursor.getString(1)));
        }

        AdapterTuVung adapterTuVung = new AdapterTuVung(list,Activity_TuVung.this);
        lv_TuVung.setAdapter(adapterTuVung);
    }
    private void speak(String s){
        if (textToSpeech.isSpeaking()){textToSpeech.stop();}
        textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);
    }
}
