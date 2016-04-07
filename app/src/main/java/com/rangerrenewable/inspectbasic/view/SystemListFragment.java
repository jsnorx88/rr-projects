package com.rangerrenewable.inspectbasic.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rangerrenewable.inspectbasic.R;
import com.rangerrenewable.inspectbasic.model.Inspection;
import com.rangerrenewable.inspectbasic.model.WTGSystem;

import java.util.List;

/**
 *
 */
public class SystemListFragment extends Fragment {

    private OnSystemListFragmentListener mListener;

    private List<WTGSystem> systems;

    public SystemListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SystemListFragment.
     */
    public static SystemListFragment newInstance() {
        SystemListFragment fragment = new SystemListFragment();
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
        View view = inflater.inflate(R.layout.fragment_system_list, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSystemListFragmentListener) {
            mListener = (OnSystemListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSystemListFragmentListener");
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
    public interface OnSystemListFragmentListener {
        void onSystemSelected(WTGSystem system);
    }

    // List adapter

    public class SystemListAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public SystemListAdapter() {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public long getItemId(int position) {
            return position;
        }

        public int getCount() {
            return systems.size();
        }

        public Object getItem(int position) {
            return systems.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            WTGSystem system = (WTGSystem) getItem(position);

            // we only want to inflate the first few times the views are loaded... otherwise reuse them
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fragment_system_list_item, null);
            }

            // set the information to the view
            ((TextView)convertView.findViewById(R.id.system_list_item_primary_text)).setText(system.getName());

            return convertView;
        }
    }
}
