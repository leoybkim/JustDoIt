package com.leoybkim.justdoit.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leoybkim.justdoit.R;

/**
 * Created by leo on 09/03/17.
 */

public class EditTaskFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Uri currentTaskUri = getArguments().getParcelable("uri");
        View view = inflater.inflate(R.layout.activity_edit_item, container, false);
        return view;
    }

    // MainActivity passes new URI every click
    public static EditTaskFragment newInstance(Uri currentTaskUri) {
        EditTaskFragment frag = new EditTaskFragment();
        Bundle args = new Bundle();
        args.putParcelable("uri", currentTaskUri);
        frag.setArguments(args);
        return frag;
    }
}
