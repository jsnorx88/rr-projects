package com.rangerrenewable.inspectbasic.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rangerrenewable.inspectbasic.R;
import com.rangerrenewable.inspectbasic.model.Inspection;
import com.rangerrenewable.inspectbasic.model.WTGSystem;

import java.util.ArrayList;

/**
 *
 */
public class InspectionListFragment extends Fragment {

    private OnInspectionListFragmentListener mListener;

    private WTGSystem system;

    private ListView listView;

    public InspectionListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InspectionListFragment.
     */
    public static InspectionListFragment newInstance(WTGSystem system) {
        InspectionListFragment fragment = new InspectionListFragment();
        fragment.system = system;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (system.getInspections() == null) {
            system.setInspections(new ArrayList<Inspection>());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inspection_list, container, false);

        // hook into the views here
        this.listView = (ListView) view.findViewById(R.id.fragment_inspection_list_view);
        InspectionListAdapter adapter = new InspectionListAdapter();
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(listener);

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

    // List adapter

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            // call the delegate with the item clicked
            if (mListener != null) {
                mListener.onInspectionTapped(system.getInspections().get(position));
            }
        }
    };

    public class InspectionListAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public InspectionListAdapter() {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public long getItemId(int position) {
            return position;
        }

        public int getCount() {
            return system.getInspections().size();
        }

        public Object getItem(int position) {
            return system.getInspections().get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Inspection inspection = (Inspection) getItem(position);

            // we only want to inflate the first few times the views are loaded... otherwise reuse them
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fragment_inspection_list_item, null);
            }

            // set the information to the view
            ((TextView)convertView.findViewById(R.id.inspection_list_item_primary_text)).setText((position + 1 + "."));
            ((TextView)convertView.findViewById(R.id.inspection_list_item_secondary_text)).setText(inspection.getTitle());
            if (inspection.hasResponded()) {
                ((ImageView) convertView.findViewById(R.id.inspection_list_item_image_view)).setImageResource(inspection.getResponse() ? R.drawable.image_passed : R.drawable.image_failed);
            }

            return convertView;
        }
    }
}
