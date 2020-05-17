package tai.nk.learnenglish;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Activity_Final_Target extends AppCompatActivity {
    TextView TA,TV;
    ListView lv_translate;
    ImageButton Speak,Nghe,back;
    String TiengAnh;
    TextToSpeech ttsp;
    SharedPreferences sharePrefernces;
    ArrayList<String> list,list_SoSanh,list_PhatLai;
    SQLiteDatabase dataSQLite;
    Cursor doituong, cursor;
    float pitch,sprate;
    ImageButton dialog_btn_ok;
    TextView dialog_txt_BaoLoi;
    ListView dialog_lv_LoiSai;
    Dialog dialog_NDGN;
    String target[],vao[],nhan[],vanban1[],vanban2[],cautruc;
    Adapter_lv_TuVung adapter;
    AdapterNDGN adapter_NDGN;
    Bundle bd;
    Max max;
    int soDiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__final__target);
        sharePrefernces = getSharedPreferences("DATA",MODE_PRIVATE);
        pitch = sharePrefernces.getFloat("PITCH", 1.0f);
        sprate = sharePrefernces.getFloat("SPRATE", 1.0f);
        addConTrols();
        Translate(TA.getText().toString());
        lv_translate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Speak(target[position]);
            }
        });

        Speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Speak(TiengAnh);
            }
        });

        Nghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeechToText();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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

    private void addConTrols() {
        dataSQLite = DataSQLite.initDatabase(Activity_Final_Target.this,getString(R.string.fileSQLite));
        bd = getIntent().getExtras();
        TA = (TextView) findViewById(R.id.txt_TA);
        TV = (TextView) findViewById(R.id.txt_TV);
        lv_translate = (ListView) findViewById(R.id.lv_translate);
        Speak = (ImageButton) findViewById(R.id.btn_speak);
        Nghe = (ImageButton) findViewById(R.id.btn_Nghe);
        back = (ImageButton) findViewById(R.id.ACFN_img_back);
        list = new ArrayList<>();
        list_PhatLai = new ArrayList<>();
        list_SoSanh = new ArrayList<>();
        max = new Max("",0,"");
        adapter_NDGN = new AdapterNDGN(Activity_Final_Target.this,list_SoSanh);
        dialog_NDGN = new Dialog(Activity_Final_Target.this);
        dialog_NDGN.setContentView(R.layout.dialog_ndgn);
        dialog_NDGN.setCancelable(false);
        dialog_lv_LoiSai = (ListView) dialog_NDGN.findViewById(R.id.dialog_NDGN_lv_LoiSai);
        dialog_btn_ok = (ImageButton) dialog_NDGN.findViewById(R.id.dialog_NDGN_btn_ok);
        dialog_txt_BaoLoi = (TextView) dialog_NDGN.findViewById(R.id.dialog_NDGN_txt_BaoLoi);
        dialog_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_NDGN.cancel();
            }
        });
        dialog_lv_LoiSai.setAdapter(adapter_NDGN);dialog_lv_LoiSai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Speak(list_PhatLai.get(position));
            }
        });
        cursor = dataSQLite.rawQuery("SELECT * FROM "+bd.getString("TenTable")+" WHERE ID = ?",new String[]{bd.getInt("ID")+""});
        cursor.moveToFirst();
        TiengAnh = cursor.getString(1);
        TA.setText(getString(R.string.Tieng_Anh)+" "+cursor.getString(1));
        cautruc = cursor.getString(3);
        if (cautruc == null){
            TV.setText(getString(R.string.Tieng_Viet)+" "+cursor.getString(2));
        } else {
            String tv = cursor.getString(2);
            cursor = dataSQLite.rawQuery("SELECT * FROM CAU_TRUC WHERE TEN = ?", new String[]{cautruc});
            if (cursor.getCount() == 0){
                TV.setText(getString(R.string.Tieng_Viet)+" "+tv);
            } else {
                cursor.moveToFirst();
                tv =getString(R.string.Tieng_Viet)+" "+tv + "\n"+getString(R.string.cautruc)+" "+ cursor.getString(1);
                TV.setText(tv);
            }
        }

        ttsp = new TextToSpeech(Activity_Final_Target.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = ttsp.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(Activity_Final_Target.this, getString(R.string.Loi_Phat_Am), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        ttsp.setPitch(pitch);
        ttsp.setSpeechRate(sprate);
    }

    private void Speak(String s) {
        if (ttsp.isSpeaking()){
            ttsp.stop();
        }
        ttsp.speak(s,TextToSpeech.QUEUE_FLUSH,null);
    }

    private void Translate(String s){
        list.clear();
        s = TiengAnh;
        s = s.toLowerCase();
        s = s.replaceAll("\\!", "");
        s = s.replaceAll("\\?", "");
        s = s.replaceAll("\\.", "");
        target = s.split("\\s");
        for (int i = 0;i < target.length;i++) {
            doituong = dataSQLite.rawQuery("SELECT * FROM TU_VUNG WHERE TA = ? ", new String[]{target[i]});
            if (doituong.getCount() == 0) {
                list.add(target[i]+": null");
            } else {
            doituong.moveToFirst();
            list.add(target[i]+": "+doituong.getString(1));}
        }
        adapter = new Adapter_lv_TuVung(Activity_Final_Target.this,list);
        lv_translate.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:{
                if ((resultCode == RESULT_OK) && (data != null)){
                    list_SoSanh.clear();
                    list_PhatLai.clear();
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String VanBan = TA.getText().toString().replaceAll("Tiếng Anh: ","");
                    vanban1 = VanBan.split("\\s");
                    VanBan = VanBan.replaceAll("  "," ");
                    VanBan = VanBan.replaceAll("\\.","");
                    VanBan = VanBan.replaceAll("\\,","");
                    VanBan = VanBan.replaceAll("\\?","");
                    VanBan = VanBan.replaceAll("\\!","");
                    VanBan = VanBan.replaceAll("\\'","1");
                    vao = VanBan.split("\\s");
                    if (vao.length == 1){
                            vanban2 = result.get(0).split("\\s");
                            nhan = result.get(0).replaceAll("\\'","1").split("\\s");
                            if (vao.length == nhan.length){
                                if (vao[0].equals(nhan[0])){
                                    list_SoSanh.add(getString(R.string.Phat_Am_Dung));
                                    break;
                                } else {
                                        list_SoSanh.add(getString(R.string.Cac_Loi_Sai)+": ");
                                        list_SoSanh.add(vanban1[0]+": "+vanban2[0]);
                                        list_PhatLai.add(vanban1[0]);
                                        break;
                                }
                            } else {
                                list_SoSanh.add(getString(R.string.Phat_Am_Thua_Tu));
                                break;
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
                                        list_SoSanh.add(getString(R.string.Cac_Loi_Sai)+":");
                                    }
                                    list_SoSanh.add(vanban1[k] + ": " + vanban2[k]);
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

                        if ((list_SoSanh.size() == 0) && (xndu)) {
                            list_SoSanh.add(getString(R.string.Phat_Am_Dung));
                        } else {
                            if ((list_SoSanh.size() == 0) && (thieu) && (!thua)){
                                list_SoSanh.add(getString(R.string.Phat_Am_Thieu_Tu));
                            } else {
                                if ((list_SoSanh.size() == 0) && (thua) && (!thieu)){
                                    list_SoSanh.add(getString(R.string.Phat_Am_Thua_Tu));
                                }
                            }
                        }
                    }
                    adapter_NDGN.notifyDataSetChanged();
                    dialog_NDGN.show();
                }
            }
        }
    }
}
