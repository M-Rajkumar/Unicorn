package com.unicornwings.unicornwings.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicornwings.unicornwings.R;
import com.unicornwings.unicornwings.Utill.Constants;
import com.unicornwings.unicornwings.Utill.FontChangeCrawler;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    FragmentActivity mActivity;


    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_second, null);
        // Inflate the layout for this fragment
        FontChangeCrawler fontChanger = new FontChangeCrawler(mActivity, Constants.Helvetica_Neu_Bold);
        fontChanger.replaceFonts((ViewGroup) rootView );
        return rootView;
    }

}
