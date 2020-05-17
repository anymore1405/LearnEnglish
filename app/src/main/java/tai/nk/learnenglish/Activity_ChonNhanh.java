package tai.nk.learnenglish;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Activity_ChonNhanh extends AppCompatActivity {
    TextView txt_TenChuDe;
    ListView lv_ChonNhanh;
    String tenChuDe,tenTable;
    ArrayList<ChonNhanh> list_ChonNhanh;
    int tc;
    SQLiteDatabase dataSQLite;
    Cursor doituong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__chon_nhanh);
        addConTrols();
        Bundle bd = getIntent().getExtras();
        tenChuDe = bd.getString("ten");
        tc = bd.getInt("TC");
        tenTable = bd.getString("TenTable");
        txt_TenChuDe.setText(tenChuDe);
        dataSQLite = DataSQLite.initDatabase(Activity_ChonNhanh.this,"New.sqlite");
        doituong = dataSQLite.rawQuery("SELECT * FROM "+tenTable,null);
        list_ChonNhanh = new ArrayList<>();
        for (int i = 0; i < doituong.getCount();i++){
            doituong.moveToPosition(i);
            list_ChonNhanh.add(new ChonNhanh(doituong.getString(0), doituong.getString(1)));
        }
        AdapterChonNhanh adt = new AdapterChonNhanh(list_ChonNhanh,Activity_ChonNhanh.this);
        lv_ChonNhanh.setAdapter(adt);
        lv_ChonNhanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_ChonNhanh.this,Activity_ListItem.class);
                intent.putExtra("ten",list_ChonNhanh.get(position).Ten);
                intent.putExtra("TenTable",list_ChonNhanh.get(position).TenTable);
                startActivity(intent);
            }
        });
        txt_TenChuDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_ChonNhanh.this,Cai_Dat.class);
                startActivity(intent);
            }
        });
    }

    private void addConTrols() {
        txt_TenChuDe = (TextView) findViewById(R.id.txt_TenChuDe);
        lv_ChonNhanh = (ListView) findViewById(R.id.lv_ChonNhanh);
    }
}
