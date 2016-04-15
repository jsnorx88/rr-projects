package com.rangerrenewable.inspectbasic.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rangerrenewable.inspectbasic.R;
import com.rangerrenewable.inspectbasic.model.Inspection;

import java.io.File;

/**
 * Handles the view for a current inspection item
 */
public class InspectionFragment extends Fragment {

    private OnInspectionFragmentListener mListener;

    // current inspection being viewed
    private Inspection inspection;

    private Uri imageUri;

    private ImageView imageView;

    private TextView titleTextView;
    private Button detailsButton;
    private Button photosButton;
    private EditText commentsField;
    private Button passButton;
    private Button failButton;
    private Button nextButton;

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

        this.titleTextView = (TextView) view.findViewById(R.id.title_text_view);
        this.detailsButton = (Button) view.findViewById(R.id.inspection_details_button);
        this.photosButton = (Button) view.findViewById(R.id.inspection_photos_button);
        this.commentsField = (EditText) view.findViewById(R.id.inspection_comments_field);
        this.passButton = (Button) view.findViewById(R.id.inspection_pass_button);
        this.failButton = (Button) view.findViewById(R.id.inspection_fail_button);
        this.nextButton = (Button) view.findViewById(R.id.inspection_next_item_button);

        this.imageView = (ImageView) view.findViewById(R.id.inspection_fragment_image_view);

        this.titleTextView.setText(this.inspection.getTitle());

        this.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), inspection.getDescription(), Toast.LENGTH_LONG).show();
            }
        });

        this.photosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photo));
                imageUri = Uri.fromFile(photo);
                startActivityForResult(intent, 0);
            }
        });

        this.passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this passed.. set the response and move on..
                inspection.setResponse(true);
                onSelection(true);
            }
        });

        this.failButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this failed.. set the response and move on..
                inspection.setResponse(false);
                onSelection(false);
            }
        });

        this.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onNextTapped(inspection);
                }
            }
        });

        if (this.inspection.hasResponded()) {
            // set the currently selected selection
            this.onSelection(this.inspection.getResponse());
        }

        // set the comments
        if (this.inspection.getComments() != null) {
            this.commentsField.setText(this.inspection.getComments());
        }

        // TODO set the photo..

        return view;
    }

    // update the pass/fail buttons to be highlighted
    private void onSelection(boolean isPassed) {
        // highlight the selected buttton
        if (isPassed) {
            this.passButton.setBackgroundResource(R.drawable.button_passed_selected);
            this.failButton.setBackgroundResource(R.drawable.button_failed_not_selected);
        } else {
            this.passButton.setBackgroundResource(R.drawable.button_passed_not_selected);
            this.failButton.setBackgroundResource(R.drawable.button_failed_selected);
        }

        // set that the inspector has responded
        this.inspection.setHasResponded(true);

        // set the comments the inspector may have input
        String comments = this.commentsField.getText().toString();
        if (comments != null && !comments.equals("")) {
            this.inspection.setComments(comments);
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage = imageUri;
            getActivity().getContentResolver().notifyChange(selectedImage, null);
            ContentResolver cr = getActivity().getContentResolver();
            Bitmap bitmap;
            try {
                bitmap = android.provider.MediaStore.Images.Media
                        .getBitmap(cr, selectedImage);

                imageView.setImageBitmap(bitmap);
                Toast.makeText(getActivity(), selectedImage.toString(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                        .show();
                Log.e("Camera", e.toString());
            }
        }
    }

    /**
     *
     */
    public interface OnInspectionFragmentListener {
        void onNextTapped(Inspection currentInspection);
    }
}
