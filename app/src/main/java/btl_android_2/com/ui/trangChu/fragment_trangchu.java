package btl_android_2.com.ui.trangChu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import btl_android_2.com.R;


public class fragment_trangchu extends Fragment {


    public fragment_trangchu() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_trangchu, container, false);
        return view;
    }
}