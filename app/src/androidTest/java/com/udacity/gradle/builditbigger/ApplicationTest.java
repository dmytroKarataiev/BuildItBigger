package com.udacity.gradle.builditbigger;

import android.test.ApplicationTestCase;
import android.text.TextUtils;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<MyApplication> {

    private final String LOG_TAG = ApplicationTest.class.getSimpleName();

    Exception mError = null;
    CountDownLatch signal = null;
    String result = null;

    public ApplicationTest() {
        super(MyApplication.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testAlbumGetTask() throws InterruptedException {

        FetchJokesEndpoint task = new FetchJokesEndpoint();
        task.setListener(new FetchJokesEndpoint.GetTaskListener() {
            @Override
            public void onComplete(String jsonString, Exception e) {
                result = jsonString;
                mError = e;
                signal.countDown();
            }
        }).execute();
        signal.await();

        String output = null;

        try {
            output = task.get();
        } catch (ExecutionException e) {
            Log.e(LOG_TAG, "Error " + e);
        }

        assertNull(mError);
        assertNotNull(output);
        assertFalse(TextUtils.isEmpty(result));
        assertTrue(result, result.length() > 0);
        assertTrue(output, output.length() > 0);
    }

    public void testJoke() {
        FetchJokesEndpoint fetchJokesEndpoint = new FetchJokesEndpoint();
        String result = null;

        try {
            result = fetchJokesEndpoint.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(LOG_TAG, "Error in testJoke() " + e);
        }

        assertNotNull(result);
        assertTrue(result, result.length() > 0);
    }
}