package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private final static String LOG_TAG = MainActivityFragment.class.getSimpleName();

    // add interstitial add
    PublisherInterstitialAd mPublisherInterstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("611F2A146CD0E2D8437BE2B61D07C6D8")
                .build();
        mAdView.loadAd(adRequest);

        Button button = (Button) root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPublisherInterstitialAd.isLoaded()) {
                    mPublisherInterstitialAd.show();
                } else {
                    tellJoke(getActivity());
                }
            }
        });

        mPublisherInterstitialAd = new PublisherInterstitialAd(getActivity());
        mPublisherInterstitialAd.setAdUnitId("/6499/example/interstitial");

        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                tellJoke(getActivity());
            }
        });

        requestNewInterstitial();

        return root;
    }

    public void tellJoke(Activity activity) {
        FetchJokesEndpoint endpointsAsyncTask = new FetchJokesEndpoint();
        endpointsAsyncTask.execute(activity);
    }

    private void requestNewInterstitial() {
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .addTestDevice("611F2A146CD0E2D8437BE2B61D07C6D8")
                .build();

        mPublisherInterstitialAd.loadAd(adRequest);
    }

}
