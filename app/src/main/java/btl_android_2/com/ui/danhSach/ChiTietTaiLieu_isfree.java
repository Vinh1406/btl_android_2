package btl_android_2.com.ui.danhSach;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import btl_android_2.com.R;
import btl_android_2.com.ui.DBSQLite.DatabaseHelper;

public class ChiTietTaiLieu_isfree extends AppCompatActivity {

    private TextView txtTieuDe, txtTacGia, txtMoTa, txtNoiDung, txtGia;
    ImageButton btn;
    //Tên file được tạo
    private String filename = "internalStorage.txt";
    private DatabaseHelper databaseHelper;

    //Thư mục do mình đặt
    private String filepath = "ThuMucCuaToi";
    File myInternalFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tai_lieu_isfree);

        txtTieuDe = findViewById(R.id.tieuDe1);
        txtTacGia = findViewById(R.id.tacGia1);
        txtMoTa = findViewById(R.id.moTa1);
        txtNoiDung = findViewById(R.id.noiDung1);
        txtGia = findViewById(R.id.gia1);
        btn=findViewById(R.id.btn_call);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            TaiLieu taiLieu = intent.getParcelableExtra("taiLieu");
            if (taiLieu != null) {
                txtTieuDe.setText(taiLieu.getTieuDe());
//                txtTacGia.setText();
                String tenTacGia = databaseHelper.getTacGiaByIdAccount(taiLieu.getIdAccount());
                if (tenTacGia != null) {
                    txtTacGia.setText(tenTacGia);
                } else {
                    txtTacGia.setText("Không có tác giả"); // Xử lý trường hợp không tìm thấy tác giả
                }
                String noidung_html=taiLieu.getNoiDung();
                txtNoiDung.setText(Html.fromHtml(
                        noidung_html,Html.FROM_HTML_MODE_COMPACT
                ));
                txtMoTa.setText(taiLieu.getMoTa());
                txtGia.setText("Miễn phí" );

            } else {
                Toast.makeText(this, "Tài liệu không tồn tại", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Lỗi khi nhận dữ liệu", Toast.LENGTH_SHORT).show();
        }
        getView();
    }

    public void getView(){
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());
        //Tạo (Hoặc là mở file nếu nó đã tồn tại) Trong bộ nhớ trong có thư mục là ThuMucCuaToi.
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, filename);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Mở file
                    FileOutputStream fos = new FileOutputStream(myInternalFile);
                    //Ghi dữ liệu vào file
                    fos.write(txtNoiDung.getText().toString().getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ChiTietTaiLieu_isfree.this,"đã lưu vào bộ nhớ trong",Toast.LENGTH_SHORT).show();
//                txtNoiDung.setText("");
//                txtNoiDung
//                        .setText("Đã được lưu vào bộ nhớ trong");
            }
        });

    }
//    public static void startActivity(Context context, TaiLieu taiLieu) {
//        Intent intent = new Intent(context, ChiTietTaiLieuActivity.class);
//        intent.putExtra("taiLieu", taiLieu);
//        context.startActivity(intent);
//    }
}


//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    Button btnSave, btnDisplay;
//    EditText myInputText;
//    TextView responseText;
//    //Tên file được tạo
//    private String filename = "internalStorage.txt";
//
//    //Thư mục do mình đặt
//    private String filepath = "ThuMucCuaToi";
//    File myInternalFile;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initView();
//        ContextWrapper contextWrapper = new ContextWrapper(
//                getApplicationContext());
//        //Tạo (Hoặc là mở file nếu nó đã tồn tại) Trong bộ nhớ trong có thư mục là ThuMucCuaToi.
//        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
//        myInternalFile = new File(directory, filename);
//        //Gọi hàm initView
//
//    }
//
//    private void initView() {
//        myInputText = (EditText) findViewById(R.id.myInputText);
//        responseText = (TextView) findViewById(R.id.responseText);
//        // Các sự kiện
//        btnSave = (Button) findViewById(R.id.btnSave);
//        btnSave.setOnClickListener(this);
//        btnDisplay = (Button) findViewById(R.id.btnDisplay);
//        btnDisplay.setOnClickListener(this);
//    }
//
//    public void onClick(View v) {
//
//        String myData = "";
//        switch (v.getId()) {
//            case R.id.btnSave:
//                try {
//                    //Mở file
//                    FileOutputStream fos = new FileOutputStream(myInternalFile);
//                    //Ghi dữ liệu vào file
//                    fos.write(myInputText.getText().toString().getBytes());
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                myInputText.setText("");
//                responseText
//                        .setText("Đã được lưu vào bộ nhớ trong");
//                break;
//
//            case R.id.btnDisplay:
//                try {
//                    //Đọc file
//                    FileInputStream fis = new FileInputStream(myInternalFile);
//                    DataInputStream in = new DataInputStream(fis);
//                    BufferedReader br = new BufferedReader(
//                            new InputStreamReader(in));
//                    String strLine;
//                    //Đọc từng dòng
//                    while ((strLine = br.readLine()) != null) {
//                        myData = myData + strLine;
//                    }
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                myInputText.setText(myData);
//                responseText
//                        .setText("Lấy dữ liệu từ bộ nhớ trong");
//                break;
//        }
//    }
//}
