package com.nith.electiveManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectiveActivity extends AppCompatActivity {

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elective);
        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        if (ApiClient.getSubmit()) {
            startActivity(new Intent(this, FinishActivity.class));
        }

        ArrayList<Elective> electives = new ArrayList<>();
        try {
            String response = new FetchElectiveTask().execute().get();
            int start = response.indexOf("[");
            response = response.substring(start);
            String splits[] = response.split("\\{");
            for (int i = 0; i < splits.length; i++) {
                String elective = "", code = "";
                if (i > 0) {
                    String subjCode[] = splits[i].split(",");
                    code = subjCode[1];
                    elective = subjCode[2];
                    code = code.split(":")[1];
                    code = code.substring(1, code.length() - 1);
                    elective = elective.split(":")[1];
                    elective = elective.substring(1, elective.length() - 1);
                    // Log.d("+++++++++", "Code: "+code+", Elective: "+elective);
                    electives.add(new Elective(elective, code, false));
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = findViewById(R.id.recv);

        findViewById(R.id.reset_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ElectiveActivity.this, ElectiveActivity.class));
            }
        });

        final ElectiveAdapter electiveAdapter = new ElectiveAdapter(electives, ElectiveActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(ElectiveActivity.this));
        recyclerView.setAdapter(electiveAdapter);


        Button button = findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<Elective> electivesAl = electiveAdapter.getElectives();
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(ElectiveActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Are you sure?")
                        .setMessage("Once submitted, you can't resubmit again")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String electivesTotal = "";
                                Log.d("++++++", String.valueOf(electivesAl.size()));
                                for (int k = 0; k < electivesAl.size(); k++) {
                                    if (electivesAl.get(k).isSelected()) {
                                        electivesTotal = electivesTotal + electivesAl.get(k).getElective() + ";";
                                        Log.d("++++++++++", String.valueOf(electivesAl.get(k).isSelected()));
                                    }
                                }

                                Call<String> call = apiInterface.submitElectives(electivesTotal);
                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        ApiClient.setSubmit(true);
                                        finish();
                                        startActivity(new Intent(ElectiveActivity.this, FinishActivity.class));
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        ApiClient.setSubmit(true);
                                        finish();
                                        startActivity(new Intent(ElectiveActivity.this, FinishActivity.class));
                                    }
                                });
                                dialogInterface.dismiss();
                                Toast.makeText(ElectiveActivity.this, "Your electives have been recorded.", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(ElectiveActivity.this, MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.alert_light_frame)
                        .show();
            }
        });
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
