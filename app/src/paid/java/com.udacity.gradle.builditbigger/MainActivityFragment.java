package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ProgressBar progressBar;

    private final static String LOG_TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Button button = (Button) root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke(getActivity());
            }
        });

        return root;
    }

    public void tellJoke(Activity activity) {
        progressBar.setVisibility(View.VISIBLE);
        FetchJokesEndpoint endpointsAsyncTask = new FetchJokesEndpoint();
        endpointsAsyncTask.execute(activity);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Hide the progressbar on move to another activity/fragment
        progressBar.setVisibility(View.GONE);
    }

}
