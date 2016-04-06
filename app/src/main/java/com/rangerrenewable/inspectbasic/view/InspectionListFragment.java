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
 *
 */
public class InspectionListFragment extends Fragment {

    private OnInspectionListFragmentListener mListener;

    public InspectionListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InspectionListFragment.
     */
    public static InspectionListFragment newInstance() {
        InspectionListFragment fragment = new InspectionListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_inspection_list, container, false);

        // hook into the views here

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInspectionListFragmentListener) {
            mListener = (OnInspectionListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInspectionListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Fragment communication
     */
    public interface OnInspectionListFragmentListener {
        void onInspectionTapped(Inspection inspection);
    }
}
