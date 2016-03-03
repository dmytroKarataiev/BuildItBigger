package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.adkdevelopment.jokesactivity.JokesActivity;
import com.example.karataev.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        //Toast.makeText(this, jokesProvider.getJoke(), Toast.LENGTH_SHORT).show();
        new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Manfred is a funny guy!"));

        // Start AndroidLibrary activity

    }

    public static class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private MyApi myApiService = null;
        private Context context;

        // variables for android test
        private GetTaskListener mListener = null;
        private Exception mError = null;


        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if(myApiService == null) {  // Only do this once
//                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                        new AndroidJsonFactory(), null)
//                        // options for running against local devappserver
//                        // - 10.0.2.2 is localhost's IP address in Android emulator
//                        // - turn off compression when running against local devappserver
//                        .setRootUrl("http://192.168.0.6:8080/_ah/api/")
//                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                            @Override
//                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                                abstractGoogleClientRequest.setDisableGZipContent(true);
//                            }
//                        });

                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://builditbigger-1238.appspot.com/_ah/api/");

                // end options for devappserver

                myApiService = builder.build();
            }
            String name = "";

            if (params.length > 0) {
                context = params[0].first;
                name = params[0].second;
            } else {
                name = "test";
            }


            try {
                return myApiService.sayHi(name).execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            Log.v("LOG", result);

            if (this.mListener != null) {
                Log.v("LOG", result);
                this.mListener.onComplete(result, mError);
            }

            if (context != null) {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, JokesActivity.class);
                intent.putExtra("joke", result);
                context.startActivity(intent);
            }
        }

        public static interface GetTaskListener {
            public void onComplete(String result, Exception e);
        }

        public EndpointsAsyncTask setListener(GetTaskListener listener) {
            this.mListener = listener;
            return this;
        }

        @Override
        protected void onCancelled() {
            if (this.mListener != null) {
                mError = new InterruptedException("AsyncTask cancelled");
                this.mListener.onComplete(null, mError);
            }
        }
    }

}
