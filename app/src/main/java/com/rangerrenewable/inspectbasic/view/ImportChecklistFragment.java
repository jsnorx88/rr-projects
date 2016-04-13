package com.rangerrenewable.inspectbasic.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rangerrenewable.inspectbasic.R;
import com.rangerrenewable.inspectbasic.model.WTGSystem;
import com.rangerrenewable.inspectbasic.model.WTGSystemParser;

/**
 * ImportChecklistFragment
 */
public class ImportChecklistFragment extends Fragment {

    private OnChecklistImportedListener mListener;

    // Views
    private EditText windFarmInput;
    private EditText wtgInput;
    private EditText inspectorName;
    private EditText inspectionDate;
    private Button loadButton;

    public ImportChecklistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ImportChecklistFragment.
     */
    public static ImportChecklistFragment newInstance() {
        ImportChecklistFragment fragment = new ImportChecklistFragment();
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
        View view = inflater.inflate(R.layout.fragment_import_checklist, container, false);

        // hook into the various views
        this.windFarmInput = (EditText) view.findViewById(R.id.import_checklist_wind_farm_input);
        this.wtgInput = (EditText) view.findViewById(R.id.import_checklist_wtg_number_input);
        this.inspectorName = (EditText) view.findViewById(R.id.import_checklist_inspector_name_input);
        this.inspectionDate = (EditText) view.findViewById(R.id.import_checklist_inspection_date_input);
        this.loadButton = (Button) view.findViewById(R.id.import_checklist_load_button);

        this.loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle File Intent
                handleFileIntent();
            }
        });

        return view;
    }

    private void handleFileIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // special intent for Samsung file manager
        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        sIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Intent chooserIntent;
        if (getActivity().getPackageManager().resolveActivity(sIntent, 0) != null){
            // it is device with samsung file manager
            chooserIntent = Intent.createChooser(sIntent, "Open file");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent});
        }
        else {
            chooserIntent = Intent.createChooser(intent, "Open file");
        }

        try {
            startActivityForResult(chooserIntent, 0);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChecklistImportedListener) {
            mListener = (OnChecklistImportedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String Fpath = "gamesa_nacelle_system.xlsx";
        if (data != null) {
            Fpath = data.getDataString();
        }
        Toast.makeText(getActivity(), "Selected file: " + Fpath, Toast.LENGTH_SHORT).show();
        if (mListener != null) {
            mListener.onChecklistImported(WTGSystemParser.importNacelleSystem());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Callback when user completes the import
     */
    public interface OnChecklistImportedListener {
        void onChecklistImported(WTGSystem system);
    }
}
