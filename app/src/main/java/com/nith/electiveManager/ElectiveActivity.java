package com.nith.electiveManager;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elective);
        try {
            String response = new FetchElectiveTask().execute().get();
            int start = response.indexOf("[");
            response = response.substring(start);
            String splits[] = response.split("\\{");
            for (int i=0; i<splits.length; i++) {
                String elective="", code="";
                if (i>0) {
                    String subjCode[] = splits[i].split(",");
                    code = subjCode[1];
                    elective = subjCode[2];
                    code = code.split(":")[1];
                    code = code.substring(1, code.length()-1);
                    elective = elective.split(":")[1];
                    elective = elective.substring(1, elective.length()-1);
                    //Log.d("+++++++++", "Code: "+code+", Elective: "+elective);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public class FetchElectiveTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            URL url;
            HttpURLConnection urlConnection = null;
            String response = "";
            try {
                url = new URL("http://192.168.43.34:4000/api/get_electives");

                urlConnection = (HttpURLConnection) url
                        .openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    response = response + String.valueOf(current);
                    //System.out.print(current);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return response;
        }
    }
}
