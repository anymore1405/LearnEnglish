package tai.nk.learnenglish;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class Bai_Test extends AppCompatActivity {
    Button btn_TLA, btn_TLB, btn_TLC, btn_TLD;
    TextView txt_CauHoi, txt_SoCau, txt_TiLeDung;
    SQLiteDatabase database;
    Cursor cursor;
    SharedPreferences sharedPreferences;
    TextToSpeech textToSpeech;
    ArrayList<Integer> list, list_TaiNguyen, list_DapAn,list_TaoDapAn;
    boolean BienDich, DichNghia, Nghe, Noi;
    Bundle bundle;
    Random random;
    int soCau, chonKieu, tongSoCau, tiLeDung, soLanBam,dapan;
    String[] vao,nhan;
    ListView lv_LoiSai;
    ArrayList<String> list_SoSanh,list_PhatLai;
    ArrayAdapter adapter;
    ImageButton img_Noi,back;
    String vanban1[],vanban2[];
    @Override
    protected void onRestart() {
        textToSpeech.setPitch(sharedPreferences.getFloat("PITCH", 1.0f));
        textToSpeech.setSpeechRate(sharedPreferences.getFloat("SPRATE", 1.0f));
        list.clear();
        DichNghia = sharedPreferences.getBoolean("DichNghia", true);
        if (DichNghia) {
            list.add(1);
        }
        BienDich = sharedPreferences.getBoolean("BienDich", true);
        if (BienDich) {
            list.add(2);
        }
        Nghe = sharedPreferences.getBoolean("Nghe", true);
        if (Nghe) {
            list.add(3);
        }
        Noi = sharedPreferences.getBoolean("Noi",true);
        if (Noi){
            list.add(4);
        }
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai__test);
        addConTrols();
        TaoCauTraLoi();
        btn_TLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLanBam++;
                tiLeDung = Math.round(soCau*100/soLanBam);
                txt_TiLeDung.setText(getString(R.string.ti_le_dung)+" "+tiLeDung +getString(R.string.phan_tram));
                if (dapan == 1) {
                    list_TaiNguyen.remove(0);
                    TaoCauTraLoi();
                } else {
                    btn_TLA.setTextColor(Color.RED);
                }
            }
        });

        btn_TLB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLanBam++;
                tiLeDung = Math.round(soCau*100/soLanBam);
                txt_TiLeDung.setText(getString(R.string.ti_le_dung)+" "+tiLeDung +getString(R.string.phan_tram));
                if (dapan == 2) {
                    list_TaiNguyen.remove(0);
                    TaoCauTraLoi();
                } else {
                    btn_TLB.setTextColor(Color.RED);
                }
            }
        });

        btn_TLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLanBam++;
                tiLeDung = Math.round(soCau*100/soLanBam);
                txt_TiLeDung.setText(getString(R.string.ti_le_dung)+" "+tiLeDung +getString(R.string.phan_tram));
                if (dapan == 3) {
                    list_TaiNguyen.remove(0);
                    TaoCauTraLoi();
                } else {
                    btn_TLC.setTextColor(Color.RED);
                }
            }
        });

        btn_TLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLanBam++;
                tiLeDung = Math.round(soCau*100/soLanBam);
                txt_TiLeDung.setText(getString(R.string.ti_le_dung)+" "+tiLeDung +getString(R.string.phan_tram));
                if (dapan == 4) {
                    list_TaiNguyen.remove(0);
                    TaoCauTraLoi();
                } else {
                    btn_TLD.setTextColor(Color.RED);
                }
            }
        });
        img_Noi.setOnClickListener(new View.OnClickListener() {
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
        lv_LoiSai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.size() > 1) {
                    if (position!=0) {
                        speak(list_PhatLai.get(position - 1));
                    }
                }
            }
        });
    }

    private void addConTrols() {
        btn_TLA = (Button) findViewById(R.id.btn_BaiTest_TLA);
        btn_TLB = (Button) findViewById(R.id.btn_BaiTest_TLB);
        btn_TLC = (Button) findViewById(R.id.btn_BaiTest_TLC);
        btn_TLD = (Button) findViewById(R.id.btn_BaiTest_TLD);
        txt_SoCau = (TextView) findViewById(R.id.txt_BaiTest_SoCau);
        txt_CauHoi = (TextView) findViewById(R.id.txt_BaiTest_CauHoi);
        txt_TiLeDung = (TextView) findViewById(R.id.Avt_Bai_Test_txt_TiLe);
        lv_LoiSai = (ListView) findViewById(R.id.BT_lv_LoiSai);
        img_Noi = (ImageButton) findViewById(R.id.BT_img_noi);
        back = (ImageButton) findViewById(R.id.Bai_Test_img_Back);
        img_Noi.setVisibility(View.INVISIBLE);

        bundle = getIntent().getExtras();

        list = new ArrayList<>();
        list_TaiNguyen = new ArrayList<>();
        list_DapAn = new ArrayList<>();
        list_SoSanh = new ArrayList<>();
        list_PhatLai = new ArrayList<>();
        list_TaoDapAn = new ArrayList<>();

        adapter = new ArrayAdapter(Bai_Test.this,android.R.layout.simple_list_item_1,list_SoSanh);
        lv_LoiSai.setAdapter(adapter);
        lv_LoiSai.setVisibility(View.INVISIBLE);

        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);

        textToSpeech = new TextToSpeech(Bai_Test.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(Bai_Test.this, getString(R.string.Loi_Phat_Am), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        textToSpeech.setPitch(sharedPreferences.getFloat("PITCH", 1.0f));
        textToSpeech.setSpeechRate(sharedPreferences.getFloat("SPRATE", 1.0f));

        DichNghia = sharedPreferences.getBoolean("DichNghia", true);
        if (DichNghia) {
            list.add(1);
        }
        BienDich = sharedPreferences.getBoolean("BienDich", true);
        if (BienDich) {
            list.add(2);
        }
        Nghe = sharedPreferences.getBoolean("Nghe", true);
        if (Nghe) {
            list.add(3);
        }
        Noi = sharedPreferences.getBoolean("Noi",true);
        if (Noi){
            list.add(4);
        }

        database = DataSQLite.initDatabase(Bai_Test.this, getString(R.string.fileSQLite));
        cursor = database.rawQuery("SELECT * FROM " + bundle.getString("TenTable"), null);

        for (int i = 0; i < cursor.getCount(); i++) {
            list_TaiNguyen.add(i);
            list_TaoDapAn.add(i);
        }
        tongSoCau = list_TaiNguyen.size() + 1;
        Collections.shuffle(list_TaiNguyen);
        Collections.shuffle(list_TaoDapAn);
        random = new Random();
        soCau = 0;
        chonKieu = 0;
        soLanBam = 0;
        dapan = 0;
        tiLeDung = 100;
        txt_TiLeDung.setText(getString(R.string.ti_le_dung)+" 0%");
    }

    private void TaoCauTraLoi() {
        if (list_TaiNguyen.size() == 0) {
            Toast.makeText(this, getString(R.string.Hoan_Thanh)+" "+tiLeDung+"/100", Toast.LENGTH_SHORT).show();
            Bai_Test.this.finish();
        } else {
            Collections.shuffle(list_TaiNguyen);
            soCau = soCau + 1;
            if (chonKieu == list.size()) {
                chonKieu = 0;
            }
        txt_SoCau.setText(getString(R.string.so_cau)+" "+soCau + "/" + tongSoCau);
            btn_TLA.setTextColor(Color.BLUE);
            btn_TLB.setTextColor(Color.BLUE);
            btn_TLC.setTextColor(Color.BLUE);
            btn_TLD.setTextColor(Color.BLUE);
            TaoDapAn();
        switch (list.get(chonKieu)) {
            case 1: {
                if (btn_TLA.getVisibility() == View.INVISIBLE){
                    btn_TLA.setVisibility(View.VISIBLE);
                    btn_TLB.setVisibility(View.VISIBLE);
                    btn_TLC.setVisibility(View.VISIBLE);
                    btn_TLD.setVisibility(View.VISIBLE);
                    lv_LoiSai.setVisibility(View.INVISIBLE);
                    img_Noi.setVisibility(View.INVISIBLE);
                }
                cursor.moveToPosition(list_TaiNguyen.get(0));
                txt_CauHoi.setText(getString(R.string.Dich_Nghia)+": " + cursor.getString(1));
                final String s = cursor.getString(1);
                txt_CauHoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        speak(s);
                    }
                });
                switch (dapan) {
                    case 1: {
                        btn_TLA.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLB.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLC.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLD.setText(cursor.getString(2));

                        break;
                    }
                    case 2: {
                        btn_TLB.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLA.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLC.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLD.setText(cursor.getString(2));

                        break;
                    }
                    case 3: {
                        btn_TLC.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLA.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLB.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLD.setText(cursor.getString(2));

                        break;
                    }
                    case 4: {
                        btn_TLD.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLA.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLB.setText(cursor.getString(2));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLC.setText(cursor.getString(2));

                        break;
                    }
                }
                break;
            }
            case 2: {
                if (btn_TLA.getVisibility() == View.INVISIBLE){
                    btn_TLA.setVisibility(View.VISIBLE);
                    btn_TLB.setVisibility(View.VISIBLE);
                    btn_TLC.setVisibility(View.VISIBLE);
                    btn_TLD.setVisibility(View.VISIBLE);
                    lv_LoiSai.setVisibility(View.INVISIBLE);
                    img_Noi.setVisibility(View.INVISIBLE);
                }
                cursor.moveToPosition(list_TaiNguyen.get(0));
                txt_CauHoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                txt_CauHoi.setText(getString(R.string.Dich_Nguoc)+": " + cursor.getString(2));
                switch (dapan) {
                    case 1: {
                        btn_TLA.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLB.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLC.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLD.setText(cursor.getString(1));

                        break;
                    }
                    case 2: {
                        btn_TLB.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLA.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLC.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLD.setText(cursor.getString(1));

                        break;
                    }
                    case 3: {
                        btn_TLC.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLA.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLB.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLD.setText(cursor.getString(1));

                        break;
                    }
                    case 4: {
                        btn_TLD.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLA.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLB.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLC.setText(cursor.getString(1));

                        break;
                    }
                }
                break;
            }
            case 3: {
                if (btn_TLA.getVisibility() == View.INVISIBLE){
                    btn_TLA.setVisibility(View.VISIBLE);
                    btn_TLB.setVisibility(View.VISIBLE);
                    btn_TLC.setVisibility(View.VISIBLE);
                    btn_TLD.setVisibility(View.VISIBLE);
                    lv_LoiSai.setVisibility(View.INVISIBLE);
                    img_Noi.setVisibility(View.INVISIBLE);
                }
                cursor.moveToPosition(list_TaiNguyen.get(0));
                txt_CauHoi.setText(getString(R.string.Bam_De_Nghe));
                final String s = cursor.getString(1);
                txt_CauHoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        speak(s);
                    }
                });
                switch (dapan) {
                    case 1: {
                        btn_TLA.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLB.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLC.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLD.setText(cursor.getString(1));

                        break;
                    }
                    case 2: {
                        btn_TLB.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLA.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLC.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLD.setText(cursor.getString(1));

                        break;
                    }
                    case 3: {
                        btn_TLC.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLA.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLB.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLD.setText(cursor.getString(1));

                        break;
                    }
                    case 4: {
                        btn_TLD.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(0));
                        btn_TLA.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(1));
                        btn_TLB.setText(cursor.getString(1));

                        cursor.moveToPosition(list_DapAn.get(2));
                        btn_TLC.setText(cursor.getString(1));

                        break;
                    }
                }
                break;
            }
            case 4:{
                list_SoSanh.clear();
                list_PhatLai.clear();
                cursor.moveToPosition(list_TaiNguyen.get(0));
                btn_TLA.setVisibility(View.INVISIBLE);
                btn_TLB.setVisibility(View.INVISIBLE);
                btn_TLC.setVisibility(View.INVISIBLE);
                btn_TLD.setVisibility(View.INVISIBLE);
                lv_LoiSai.setVisibility(View.VISIBLE);
                img_Noi.setVisibility(View.VISIBLE);
                txt_CauHoi.setText(getString(R.string.Noi_Cau_Sau)+": "+cursor.getString(1));
                txt_CauHoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        speak(cursor.getString(1));
                    }
                });
                break;
            }
        }
            chonKieu = chonKieu + 1;
    }

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

    private void TaoDapAn(){
        dapan = random.nextInt(4);
        while (dapan<=0){
            dapan = random.nextInt(4);
        }
        list_DapAn.clear();
        if (list_TaiNguyen.size()>3){
            list_DapAn.add(list_TaiNguyen.get(1));
            list_DapAn.add(list_TaiNguyen.get(2));
            list_DapAn.add(list_TaiNguyen.get(3));
        } else {
            list_TaoDapAn.clear();
            int d1,d2,d3;
            d1 = random.nextInt(tongSoCau - 1);
            d2 = random.nextInt(tongSoCau - 1);
            d3 = random.nextInt(tongSoCau - 1);
            while ((d1<0)||(d2<0)||(d3<0)||(d1 == d2)||(d2 == d3)||(d3 == d1)||(d1 == dapan)||(d2 == dapan)||(d3 == dapan)){
                d1 = random.nextInt(tongSoCau - 1);
                d2 = random.nextInt(tongSoCau - 1);
                d3 = random.nextInt(tongSoCau - 1);
            }
            list_DapAn.add(d1);
            list_DapAn.add(d2);
            list_DapAn.add(d3);
        }
        list_DapAn.add(dapan);
    }

    private void speak(String s){
        if (textToSpeech.isSpeaking()){textToSpeech.stop();}
        textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:{
                if ((resultCode == RESULT_OK) && (data != null)){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String VanBan = cursor.getString(1);
                    vanban1 = VanBan.split("\\s");
                    VanBan = VanBan.replaceAll("  "," ");
                    VanBan = VanBan.replaceAll("\\.","");
                    VanBan = VanBan.replaceAll("\\,","");
                    VanBan = VanBan.replaceAll("\\?","");
                    VanBan = VanBan.replaceAll("\\!","");
                    VanBan = VanBan.replaceAll("\\'","1");
                    vao = VanBan.split("\\s");
                    soLanBam++;
                    tiLeDung = Math.round(soCau*100/soLanBam);
                    txt_TiLeDung.setText(getString(R.string.ti_le_dung)+" "+tiLeDung +getString(R.string.phan_tram));
                    if (vao.length == 1) {
                        vanban2 = result.get(0).split("\\s");
                        nhan = result.get(0).replaceAll("\\'", "1").split("\\s");
                        if (vao.length == nhan.length) {
                            if (vao[0].equals(nhan[0])) {
                                list_SoSanh.add(getString(R.string.Phat_Am_Dung));
                                list_TaiNguyen.remove(0);
                                TaoCauTraLoi();
                                break;
                            } else {
                                list_SoSanh.add(getString(R.string.Cac_Loi_Sai) + ": ");
                                list_SoSanh.add(vanban1[0] + ": " + vanban2[0]);
                                list_PhatLai.add(vanban1[0]);
                                break;
                            }
                        } else {
                            list_SoSanh.add(getString(R.string.Phat_Am_Thua_Tu));
                            break;
                        }
                    } else {
                        nhan = result.get(0).replaceAll("\\'", "").split("\\s");
                        boolean xndu = false;
                        boolean thua = false;
                        boolean thieu = false;
                        if (vao.length == nhan.length) {
                            xndu = true;
                            vanban2 = nhan;
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
                            list_TaiNguyen.remove(0);
                            TaoCauTraLoi();
                        } else {
                            if ((list_SoSanh.size() == 0) &&(!xndu)&& (thieu) && (!thua)){
                                list_SoSanh.add(getString(R.string.Phat_Am_Thieu_Tu));
                            } else {
                                if ((list_SoSanh.size() == 0) &&(!xndu)&& (thua) && (!thieu)){
                                    list_SoSanh.add(getString(R.string.Phat_Am_Thua_Tu));
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}
