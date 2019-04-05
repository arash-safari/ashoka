package com.game.senjad.sgame.results_view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.senjad.sgame.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesResultsFragment extends Fragment {


    private View view;

    public CitiesResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cities_results, container, false);
        view.setRotationY(180);
        return view;
    }

}
