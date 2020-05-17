package tai.nk.learnenglish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

public class ThietLap_BaiTest extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean biendich, dichnghia, nghe, noi;
    SQLiteDatabase database;
    Cursor cursor;
    Bundle bundle;
    Button btn_BatDau;
    ImageButton btn_back;
    CheckBox cb_DichNghia,cb_BienDich,cb_Nghe,cb_Noi;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        if (!dichnghia&&!biendich&&!nghe&&!noi){
            dichnghia = true;
        }
        editor.putBoolean("DichNghia", dichnghia);
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap__bai_test);
        addConTrols();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_BatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThietLap_BaiTest.this,Bai_Test.class);
                intent.putExtra("Ten",bundle.getString("Ten"));
                intent.putExtra("TenTable",bundle.getString("TenTable"));
                startActivity(intent);
                ThietLap_BaiTest.this.finish();
            }
        });
        cb_DichNghia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dichnghia = isChecked;
                editor.putBoolean("DichNghia", dichnghia);
                editor.commit();
            }
        });
        cb_BienDich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                biendich = isChecked;
                editor.putBoolean("BienDich", biendich);
                editor.commit();
            }
        });
        cb_Nghe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nghe = isChecked;
                editor.putBoolean("Nghe", nghe);
                editor.commit();
            }
        });
        cb_Noi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                noi = isChecked;
                editor.putBoolean("Noi",noi);
                editor.commit();
            }
        });
    }

    private void addConTrols() {
        btn_BatDau = (Button) findViewById(R.id.Avt_Thiet_Lap_btn_BatDau);
        btn_back = (ImageButton) findViewById(R.id.TLBT_img_btn_Back);
        cb_DichNghia = (CheckBox) findViewById(R.id.TLBT_cb_DichNghia);
        cb_BienDich = (CheckBox) findViewById(R.id.TLBT_cb_BienDich);
        cb_Nghe = (CheckBox) findViewById(R.id.TLBT_cb_Nghe);
        cb_Noi = (CheckBox) findViewById(R.id.TLBT_cb_Noi);

        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dichnghia = sharedPreferences.getBoolean("DichNghia", true);
        biendich = sharedPreferences.getBoolean("BienDich", true);
        nghe = sharedPreferences.getBoolean("Nghe", true);
        noi = sharedPreferences.getBoolean("Noi",true);
        cb_BienDich.setChecked(biendich);
        cb_DichNghia.setChecked(dichnghia);
        cb_Nghe.setChecked(nghe);
        cb_Noi.setChecked(noi);
        bundle = getIntent().getExtras();
        database = DataSQLite.initDatabase(ThietLap_BaiTest.this, "New.sqlite");
        cursor = database.rawQuery("SELECT * FROM "+bundle.getString("TenTable"), null);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ThietLap_BaiTest.this.finish();
    }
}
