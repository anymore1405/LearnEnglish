package tai.nk.learnenglish;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_GopY extends AppCompatActivity {
    DatabaseReference databaseReference;
    Button btn_NoiDung,btn_TaiLieu,btn_NoiDung_OK,btn_NoiDung_Huy,btn_DongGop_Ok,btn_DongGop_Huy;
    Dialog NoiDung,TaiLieu;
    ImageButton back;
    EditText edt_NoiDung,edt_DongGop_ChuDe,edt_DongGop_TA,edt_DongGop_TV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__gop_y);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        addConTrols();
        addDialog();
        btn_TaiLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoiDung.show();
            }
        });
        btn_NoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaiLieu.show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void addDialog() {
        NoiDung = new Dialog(Activity_GopY.this);
        NoiDung.setTitle("Góp ý về nội dung của ứng dụng");
        NoiDung.setCancelable(false);
        NoiDung.setContentView(R.layout.dialog_noidung);
        edt_NoiDung = (EditText) NoiDung.findViewById(R.id.edt_gopy_NoiDung);
        btn_NoiDung_OK = (Button) NoiDung.findViewById(R.id.btn_gopy_NoiDungoOk);
        btn_NoiDung_Huy = (Button) NoiDung.findViewById(R.id.btn_gopy_NoiDungHuy);
        btn_NoiDung_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoiDung.cancel();
            }
        });
        btn_NoiDung_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Gop_Y_NoiDung").push().setValue(edt_NoiDung.getText().toString(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null){
                            Toast.makeText(Activity_GopY.this, "Cảm ơn bạn đã góp ý", Toast.LENGTH_SHORT).show();
                            NoiDung.cancel();
                        } else {
                            Toast.makeText(Activity_GopY.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        TaiLieu = new Dialog(Activity_GopY.this);
        TaiLieu.setTitle("Đóng góp tài liệu");
        TaiLieu.setCancelable(false);
        TaiLieu.setContentView(R.layout.dialog_tailieu);
        edt_DongGop_ChuDe = (EditText) TaiLieu.findViewById(R.id.edt_DongGop_ChuDe);
        edt_DongGop_TA = (EditText) TaiLieu.findViewById(R.id.edt_DongGop_NoiDungTiengAnh);
        edt_DongGop_TV = (EditText) TaiLieu.findViewById(R.id.edt_DongGop_BanDichTiengViet);
        btn_DongGop_Ok = (Button) TaiLieu.findViewById(R.id.btn_DongGop_Ok);
        btn_DongGop_Huy = (Button) TaiLieu.findViewById(R.id.btn_DongGop_Huy);
        
        btn_DongGop_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaiLieu.cancel();
            }
        });
        btn_DongGop_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaiLieu_FireBase tailieu = new TaiLieu_FireBase();
                tailieu.ChuDe = edt_DongGop_ChuDe.getText().toString();
                tailieu.TA = edt_DongGop_TA.getText().toString();
                tailieu.TV = edt_DongGop_TV.getText().toString();
                databaseReference.child("DongGop_TaiLieu").push().setValue(tailieu, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(Activity_GopY.this, "Cám ơn bạn đã đóng góp tài liệu.", Toast.LENGTH_SHORT).show();
                            TaiLieu.cancel();
                        } else {
                            Toast.makeText(Activity_GopY.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void addConTrols() {
        btn_NoiDung = (Button) findViewById(R.id.btn_gopy_NoiDung);
        btn_TaiLieu = (Button) findViewById(R.id.btn_gop_y_TaiLieu);
        back = (ImageButton) findViewById(R.id.GY_back);
    }
}
