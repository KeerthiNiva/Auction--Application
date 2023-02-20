package com.konguengineering.www.auction;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class cusfeeds extends AppCompatActivity {


    EditText cusmes;

    Button cussave;

    ProgressBar progressBar;

    String cusid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusfeeds);

        cusmes = (EditText) findViewById(R.id.cusfeedmes);

        cussave = (Button) findViewById(R.id.cusfeedsave);


        progressBar = (ProgressBar) findViewById(R.id.progressBar21);

        Bundle b = getIntent().getExtras();

        if (b != null) {

            cusid = b.getString("id");

            Toast.makeText(cusfeeds.this, "" + cusid, Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, ""+auction_id, Toast.LENGTH_SHORT).show();
        }


        cussave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cusmes.getText().toString().length() == 0) {

                    Toast.makeText(cusfeeds.this, "Enter the valid field", Toast.LENGTH_SHORT).show();


                } else {

                    new customerfeedbackdataload().execute();

                }


            }
        });
    }

            public class customerfeedbackdataload extends AsyncTask<String, String, String> {

                @Override
                protected void onPreExecute() {
                    // TODO Auto-generated method stub

                    progressBar.setVisibility(View.VISIBLE);


                }

                @Override
                protected String doInBackground(String... params) {

                    InputStream is = null;
                    StringBuilder sb = null;
                    String result = null;

                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("content", ""+cusmes.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("cid", ""+cusid));



                    try {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/cfeedback.php");
                        httppost.setEntity(new UrlEncodedFormEntity(
                                nameValuePairs));

                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();


                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(is, "iso-8859-1"), 8);
                        sb = new StringBuilder();
                        sb.append(reader.readLine() + "\n");
                        String line = "0";
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        result = sb.toString();

                        System.out.println( "=====/////" + result);
                        Toast.makeText(cusfeeds.this, ""+result, Toast.LENGTH_SHORT).show();


                    } catch (Exception e) {
                        Log.e("log_tag",
                                "Error in http connection" + e.toString());
                    }




                    return result;
                }

                @Override
                protected void onPostExecute(String s) {

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(cusfeeds.this, ""+s, Toast.LENGTH_SHORT).show();


                }
            }

        }