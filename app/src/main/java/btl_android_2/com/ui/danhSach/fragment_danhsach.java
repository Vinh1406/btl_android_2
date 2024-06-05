package btl_android_2.com.ui.danhSach;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import btl_android_2.com.R;
import btl_android_2.com.ui.DBSQLite.DatabaseHelper;


public class fragment_danhsach extends Fragment {
    private RecyclerView recyclerView;
    private TaiLieuAdapter adapter;
    private List<TaiLieu> taiLieuList;
    private DatabaseHelper databaseHelper;
    private Spinner spinnerFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach, container, false);

        spinnerFilter = view.findViewById(R.id.spinnerFilter);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taiLieuList = new ArrayList<>();
        adapter = new TaiLieuAdapter(getContext(), taiLieuList, this::showDetails);
        recyclerView.setAdapter(adapter);

        databaseHelper = DatabaseHelper.getInstance(getContext());

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.filter_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(spinnerAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadTaiLieu(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        loadTaiLieu(0);

        return view;
    }

    private void loadTaiLieu(int filterType) {
        taiLieuList.clear();
        Cursor cursor;
        if (filterType == 0) {
            cursor = databaseHelper.getAllDocuments();
        } else {
            cursor = databaseHelper.getDocumentsByType(filterType == 1);
        }

        if (cursor != null && cursor.moveToFirst()) {

                int idIndex = cursor.getColumnIndex("id");
                int tieuDeIndex = cursor.getColumnIndex("tieuDe");
//                int tacGiaIndex = cursor.getColumnIndex("tacGia");
                int moTaIndex = cursor.getColumnIndex("moTa");
                int noiDungIndex = cursor.getColumnIndex("noiDung");
                int trangThaiIndex = cursor.getColumnIndex("trangThai");
//                int soDienThoaiIndex = cursor.getColumnIndex("soDienThoai");
                int isFreeIndex = cursor.getColumnIndex("isFree");
                int giaIndex = cursor.getColumnIndex("gia");
                int idAccountIndex=cursor.getColumnIndex("idAccount");
                int idLoaiTaiLieuIndex=cursor.getColumnIndex("idLoaiTaiLieu");

            do {
                if (tieuDeIndex != -1  && moTaIndex != -1 && noiDungIndex != -1 && giaIndex != -1 && idAccountIndex!=-1 && idLoaiTaiLieuIndex!=-1) {
                    int id = cursor.getInt(idIndex);
                    String tieuDe = cursor.getString(tieuDeIndex);
//                    String tacGia = cursor.getString(tacGiaIndex);
                    String moTa = cursor.getString(moTaIndex);
                    String noiDung = cursor.getString(noiDungIndex);
                    int trangThai = cursor.getInt(trangThaiIndex);
//                    String soDienThoai = cursor.getString(soDienThoaiIndex);
                    boolean isFree = cursor.getInt(isFreeIndex) == 1;
                    int gia = cursor.getInt(giaIndex);
                    int idAccount=cursor.getInt(idAccountIndex);
                    int idLoaiTaiLieu=cursor.getInt(idLoaiTaiLieuIndex);
                    TaiLieu taiLieu = new TaiLieu(id, tieuDe, moTa, noiDung, trangThai, isFree, gia,idAccount,idLoaiTaiLieu);
                    taiLieuList.add(taiLieu);
                }else{

                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }

    private void showDetails(TaiLieu taiLieu) {
        ChiTietTaiLieuActivity.startActivity(getContext(), taiLieu);
//        activity_tailieu_free.start
    }
}