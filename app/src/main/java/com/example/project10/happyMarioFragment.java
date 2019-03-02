package com.example.project10;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class happyMarioFragment extends Fragment {

@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.happymario, container, false);
        return view;
}

}
