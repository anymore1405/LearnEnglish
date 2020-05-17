package tai.nk.learnenglish;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class NhanDienGiongNoi extends AppCompatActivity {
    ArrayList<String> list,list_PhatLai;
    int soDiem;
    EditText edt_VanBan;
    ListView lv_KetQua;
    ArrayAdapter adapter;
    String[] vao,nhan;
    Max max;
    TextToSpeech ttsp;
    SharedPreferences sharePrefernces;
    float pitch,sprate;
    ImageButton img_speak,img_phatam,back;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_dien_giong_noi);
        ttsp = new TextToSpeech(NhanDienGiongNoi.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = ttsp.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(NhanDienGiongNoi.this, getString(R.string.Loi_Phat_Am), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        sharePrefernces = getSharedPreferences("DATA",MODE_PRIVATE);
        pitch = sharePrefernces.getFloat("PITCH", 1.0f);
        sprate = sharePrefernces.getFloat("SPRATE", 1.0f);
        ttsp.setPitch(pitch);
        ttsp.setSpeechRate(sprate);

        list = new ArrayList<>();
        list_PhatLai = new ArrayList<>();
        max = new Max("",0,"");
        img_speak = (ImageButton) findViewById(R.id.NDGN_img_speak);
        img_phatam = (ImageButton) findViewById(R.id.NDGN_img_speaker);
        back = (ImageButton) findViewById(R.id.NDGN_img_back);
        edt_VanBan = (EditText) findViewById(R.id.NDGN_edt_DoanVan);
        lv_KetQua = (ListView) findViewById(R.id.NDGN_lv_KetQua);
        adapter = new ArrayAdapter(NhanDienGiongNoi.this,android.R.layout.simple_list_item_1,list);
        lv_KetQua.setAdapter(adapter);
        img_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeechToText();
            }
        });
        img_phatam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_VanBan.getText().toString().equals("")){
                    Speak(edt_VanBan.getText().toString());
                }
            }
        });
        lv_KetQua.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.size() > 1) {
                    if (position!=0) {
                        Speak(list_PhatLai.get(position - 1));
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void Speak(String s){
        if (ttsp.isSpeaking()){ttsp.stop();}
        ttsp.speak(s,TextToSpeech.QUEUE_FLUSH,null);
    }
    private void SpeechToText(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.Noi_Vao_Mic));
        try {
            startActivityForResult(intent,100);
        } catch (ActivityNotFoundException a){
            Toast.makeText(this, getString(R.string.Loi), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 100:{
                if ((resultCode == RESULT_OK) && (data != null)){
                    list.clear();
                    list_PhatLai.clear();
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String VanBan = edt_VanBan.getText().toString();
                    String[] vanban1 = VanBan.split("\\s");
                    String[] vanban2 = null;
                    VanBan = VanBan.replaceAll("  "," ");
                    VanBan = VanBan.replaceAll("\\.","");
                    VanBan = VanBan.replaceAll("\\,","");
                    VanBan = VanBan.replaceAll("\\?","");
                    VanBan = VanBan.replaceAll("\\!","");
                    VanBan = VanBan.replaceAll("\\'","1");
                    vao = VanBan.split("\\s");
                    max.GiaTri = 0;
                    max.Max = result.get(0);
                    if (vao.length == 1){
                        for (int i = 0;i<result.size();i++){
                            vanban2 = result.get(i).split("\\s");
                            nhan = result.get(i).replaceAll("\\'","1").split("\\s");
                            if (vao.length == nhan.length){
                                vao[0] = vao[0].toLowerCase();
                                nhan[0] = nhan[0].toLowerCase();
                                if (vao[0].equals(nhan[0])){
                                    list.add(getString(R.string.Phat_Am_Dung));
                                    break;
                                } else {
                                        list.add(getString(R.string.Cac_Loi_Sai)+": ");

                                        list.add(vanban1[0]+": "+vanban2[0]);
                                        list_PhatLai.add(vanban1[0]);
                                        break;
                                }
                            } else {
                                list.add(getString(R.string.Phat_Am_Thua_Tu));
                                break;
                            }
                        }
                    } else {
                        nhan = result.get(0).split("\\s");
                        soDiem = 0;
                        boolean xndu = false;
                        boolean thua = false;
                        boolean thieu = false;
                        if (vao.length == nhan.length) {
                            xndu = true;
                            vanban2 = nhan;
                            nhan = result.get(0).replaceAll("\\'","1").split("\\s");
                            boolean xn = true;
                            for (int k = 0; k < vao.length; k++) {
                                vao[k] = vao[k].toLowerCase();
                                nhan[k] = nhan[k].toLowerCase();
                                if (!vao[k].equals(nhan[k])) {
                                    if (xn) {
                                        xn = false;
                                        list.add(getString(R.string.Cac_Loi_Sai)+":");
                                    }
                                    list.add(vanban1[k] + ": " + vanban2[k]);
                                    list_PhatLai.add(vanban1[k]);
                                }
                            }
                        } else {
                            if (vao.length > nhan.length){
                                thieu = true;
                                thua = false;
                            } else {
                                thieu = false;
                                thua = true;
                            }
                        }

                        if ((list.size() == 0) && (xndu)) {
                            list.add(getString(R.string.Phat_Am_Dung));
                        } else {
                            if ((list.size() == 0) && (!xndu)&&(thieu) && (!thua)){
                                list.add(getString(R.string.Phat_Am_Thieu_Tu));
                            } else {
                                if ((list.size() == 0) &&(!xndu)&& (thua) && (!thieu)){
                                    list.add(getString(R.string.Phat_Am_Thua_Tu));
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
