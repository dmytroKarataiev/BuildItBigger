package com.adkdevelopment.jokesactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class JokesActivityFragment extends Fragment {

    public JokesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_jokes, container, false);
        tellJoke(root);

        return root;
    }

    public void tellJoke(View view) {
        String joke = getActivity().getIntent().getStringExtra(JokesActivity.JOKE);
        TextView textView = (TextView) view.findViewById(R.id.joke_textview);
        textView.setText(joke);
    }
}
