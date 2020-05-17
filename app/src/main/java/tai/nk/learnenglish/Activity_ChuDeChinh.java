package tai.nk.learnenglish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_ChuDeChinh extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView lv_ChuDe;
    SQLiteDatabase dataSQLite;
    Cursor doituong;
    TextView txt_ChuDe;
    ArrayList<ChuDe> list_ChuDe;
    ArrayList<Integer> listSTT;
    Intent intent;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__chu_de_chinh);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addConTrols();
        list_ChuDe = new ArrayList<>();
        listSTT = new ArrayList<>();
        list_ChuDe.clear();
        for (int i = 0;i < doituong.getCount();i++){
            doituong.moveToPosition(i);
            list_ChuDe.add(new ChuDe(doituong.getString(0),doituong.getString(1)));
            listSTT.add(i+1);
        }
        final AdapterChuDe adt = new AdapterChuDe(Activity_ChuDeChinh.this,list_ChuDe,listSTT);
        lv_ChuDe.setAdapter(adt);

        lv_ChuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(Activity_ChuDeChinh.this,Activity_ListItem.class);
                editor.putString("Ten",list_ChuDe.get(position).Ten);
                editor.putString("TenTable",list_ChuDe.get(position).TenTable);
                editor.commit();
                startActivity(intent);
            }
        });
    }

    private void addConTrols() {
        txt_ChuDe = (TextView) findViewById(R.id.txt_ChuDe);
        lv_ChuDe = (ListView) findViewById(R.id.lv_ChuDe);
        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dataSQLite = DataSQLite.initDatabase(Activity_ChuDeChinh.this,getString(R.string.fileSQLite));
        doituong = dataSQLite.rawQuery("SELECT * FROM CHU_DE",null);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__chu_de_chinh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Activity_ChuDeChinh.this, Cai_Dat.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.item_menu_NhanDienGiongNoi:{
                startActivity(new Intent(Activity_ChuDeChinh.this,NhanDienGiongNoi.class));
                break;
            }
            case R.id.item_menu_KTBX:{
                startActivity(new Intent(Activity_ChuDeChinh.this, KienThucBoXung.class));
                break;
            }
            case R.id.item_menu_thietlap:{
            startActivity(new Intent(Activity_ChuDeChinh.this,Cai_Dat.class));
            break;}
            case R.id.item_menu_vocabulary: {
                Intent intent = new Intent(Activity_ChuDeChinh.this, Activity_TuVung.class);
                startActivity(intent);
                break;
            }
            case R.id.item_menu_gopY: {
                if (isConnectted()){
                Intent intent = new Intent(Activity_ChuDeChinh.this, Activity_GopY.class);
                startActivity(intent);
                } else {
                    Toast.makeText(this, getString(R.string.Khong_Co_Ket_Noi_Mang), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.item_menu_Thoat: {
                finish();
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean isConnectted(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }
}
