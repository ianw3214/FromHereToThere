package com.getfhtt.fhtt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getfhtt.fhtt.models.NavigateCard;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultsFragment newInstance(String param1, String param2) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_results, container, false);

        String origin = getArguments().getString("origin");
        String destination = getArguments().getString("destination");

        NavigateCard cWalking = (NavigateCard) myView.findViewById(R.id.cWalking);
        NavigateCard cBiking = (NavigateCard) myView.findViewById(R.id.cBiking);
        NavigateCard cTransit = (NavigateCard) myView.findViewById(R.id.cTransit);
        NavigateCard cDriving = (NavigateCard) myView.findViewById(R.id.cDriving);

        TextView tvInfo = (TextView) myView.findViewById(R.id.tvInfo);

        Route myTravel = new Route("Queen's University, Kingston, Ontario", "Cateraqui Center, Kingston, Ontario", "transit");

        if(myTravel.isLoaded()){
            cWalking.setText(myTravel.getTravelTime() + "minutes total/\n" + myTravel.getTravelTime()+ "minutes physical activity/\n"+ "Some amount of calories");
        }
        return myView;
    }

    public void userStats(String distance) {
        int metres = Integer.parseInt(distance);

        //Constants for Calculations
        double costPerKm = 0.106;
        int emissionsPerKm = 255;
        int caloriesPerKm = 112;

        //Calculation for money saved
        double moneySaved = costPerKm * metres;

        //Calculation for emissions saved
        int emissionsSaved = emissionsPerKm * metres;

        //Calculation for caloriesBurned
        int caloriesSaved = caloriesPerKm * metres;

    }
}
