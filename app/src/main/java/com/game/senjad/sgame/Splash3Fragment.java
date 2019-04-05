package com.game.senjad.sgame;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.senjad.base.components.IranSansButton;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class Splash3Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_splash3, container, false);
        IranSansButton button = view.findViewById(R.id.start_botton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SharedPreferenceUtils.getInstance(getContext()).getToken().equals("")) {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }else{
                    startActivity(new Intent(getContext(),MainActivity.class));
                }
            }
        });

        return view;
    }

}
