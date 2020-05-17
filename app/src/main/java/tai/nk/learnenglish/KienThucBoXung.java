package tai.nk.learnenglish;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class KienThucBoXung extends AppCompatActivity {
    SQLiteDatabase database;
    Cursor cursor;
    ImageButton back;
    ImageButton xong;
    TextView ten,noidung;
    ListView lv;
    ArrayList<KTBX> list;
    AdapterKTBX adapterKTBX;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kien_thuc_bo_xung);
        database = DataSQLite.initDatabase(KienThucBoXung.this,getString(R.string.fileSQLite));
        cursor = database.rawQuery("SELECT * FROM TAI_LIEU", null);
        addConTrols();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ten.setText(list.get(position).Ten);
                noidung.setText(list.get(position).NoiDung);
                dialog.show();
            }
        });
    }

    private void addConTrols() {
        back = (ImageButton) findViewById(R.id.KTBX_img_back);
        lv = (ListView) findViewById(R.id.KTBX_lv);
        list = new ArrayList<>();
        for (int i = 0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            list.add(new KTBX(cursor.getString(1),cursor.getString(2)));
        }
        adapterKTBX = new AdapterKTBX(KienThucBoXung.this,list);
        lv.setAdapter(adapterKTBX);
        dialog = new Dialog(KienThucBoXung.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_ktbx);
        ten = (TextView) dialog.findViewById(R.id.dialog_ktbx_ten);
        noidung = (TextView) dialog.findViewById(R.id.dialog_ktbx_noidung);
        xong = (ImageButton) dialog.findViewById(R.id.dialog_ktbx_ok);
        xong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
}
