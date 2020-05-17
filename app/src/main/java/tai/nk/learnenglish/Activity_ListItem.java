package tai.nk.learnenglish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Activity_ListItem extends AppCompatActivity {
    TextView txt_Nhanh;
    ListView lv_Item;
    String ten, tenTable;
    SQLiteDatabase data;
    Cursor dt;
    AdapterListiTem adapter;
    ArrayList<ListiTem> list;
    SharedPreferences save;
    Button btn_BaiTest;
    ImageButton back;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__list_item);
        addConTrols();
        list = new ArrayList<>();
        for (int i = 0;i < dt.getCount();i++){
            dt.moveToPosition(i);
            list.add(new ListiTem(dt.getInt(0),dt.getString(1),dt.getString(2),dt.getString(3)));
        }
        adapter = new AdapterListiTem(Activity_ListItem.this,list);
        lv_Item.setAdapter(adapter);
        lv_Item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_ListItem.this, Activity_Final_Target.class);
                intent.putExtra("ID", list.get(position).ID);
                intent.putExtra("TenTable",tenTable);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        });
        btn_BaiTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_ListItem.this, ThietLap_BaiTest.class);
                intent.putExtra("TenTable",tenTable);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        for (int i = 0;i < dt.getCount();i++){
            dt.moveToPosition(i);
            list.add(new ListiTem(dt.getInt(0),dt.getString(1),dt.getString(2),dt.getString(3)));
        }
        adapter = new AdapterListiTem(Activity_ListItem.this,list);
        lv_Item.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
        for (int i = 0;i < dt.getCount();i++){
            dt.moveToPosition(i);
            list.add(new ListiTem(dt.getInt(0),dt.getString(1),dt.getString(2),dt.getString(3)));
        }
        adapter = new AdapterListiTem(Activity_ListItem.this,list);
        lv_Item.setAdapter(adapter);
    }

    private void addConTrols() {
        txt_Nhanh = (TextView) findViewById(R.id.Atv_txt_TenChuDe);
        lv_Item = (ListView) findViewById(R.id.lv_ChonITem);
        btn_BaiTest = (Button) findViewById(R.id.Avt_btn_BaiTest);
        back = (ImageButton) findViewById(R.id.ACLI_img_Back);
        save = getSharedPreferences("DATA",MODE_PRIVATE);
        ten = save.getString("Ten","");
        txt_Nhanh.setText(getString(R.string.Chu_De)+": "+ten);
        tenTable = save.getString("TenTable","");
        data = DataSQLite.initDatabase(Activity_ListItem.this,getString(R.string.fileSQLite));
        dt = data.rawQuery("SELECT * FROM "+tenTable,null);
    }

}
