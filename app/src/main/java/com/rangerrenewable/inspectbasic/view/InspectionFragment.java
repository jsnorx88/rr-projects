package com.rangerrenewable.inspectbasic.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rangerrenewable.inspectbasic.R;
import com.rangerrenewable.inspectbasic.model.Inspection;

/**
 *  Handles the view for a current inspection item
 */
public class InspectionFragment extends Fragment {

    private OnInspectionFragmentListener mListener;

    // current inspection being viewed
    private Inspection inspection;

    public InspectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InspectionFragment.
     */
    public static InspectionFragment newInstance(Inspection inspection) {
        InspectionFragment fragment = new InspectionFragment();
        fragment.inspection = inspection;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inspection, container, false);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInspectionFragmentListener) {
            mListener = (OnInspectionFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInspectionFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     *
     */
    public interface OnInspectionFragmentListener {
        void onNextTapped();
    }
}
