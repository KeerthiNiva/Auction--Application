package com.konguengineering.www.auction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class cusactbitupdate extends AppCompatActivity {

    EditText ggrate;

    Button ggupdate;

    ProgressBar progressBar;

    String auction_id,cus_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusactbitupdate);

        ggrate=(EditText)findViewById(R.id.finalrate);

        ggupdate=(Button) findViewById(R.id.auctionfinal);

        progressBar=(ProgressBar)findViewById(R.id.progressBar13);


        Bundle b=getIntent().getExtras();

        if(b!=null)
        {

            auction_id=b.getString("ccids");
            cus_id=b.getString("cus_ids");

            Toast.makeText(cusactbitupdate.this, ""+auction_id, Toast.LENGTH_SHORT).show();

            Toast.makeText(cusactbitupdate.this, ""+cus_id, Toast.LENGTH_SHORT).show();


        }


        ggupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable()){

                    new bitupdatecistomer().execute();
                }
                else
                {

                    Toast.makeText(cusactbitupdate.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public class bitupdatecistomer extends AsyncTask<String, String, String> {

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

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            //nameValuePairs.add(new BasicNameValuePair("action", "update"));
            nameValuePairs.add(new BasicNameValuePair("pid", "" + auction_id));
            nameValuePairs.add(new BasicNameValuePair("cid", "" + cus_id));
            nameValuePairs.add(new BasicNameValuePair("rate", "" + ggrate.getText().toString()));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/abit.php");
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

                System.out.println("=====/////" + result);
                Toast.makeText(cusactbitupdate.this, "" + result, Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                Log.e("log_tag",
                        "Error in http connection" + e.toString());
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            progressBar.setVisibility(View.GONE);
            Toast.makeText(cusactbitupdate.this, "" + s, Toast.LENGTH_SHORT).show();



        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}