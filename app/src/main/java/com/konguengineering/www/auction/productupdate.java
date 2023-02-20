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

public class productupdate extends AppCompatActivity {

    EditText pupname,puptype,pupproduct,pupdate,pupqty,pupspeci;

    Button pupdates;

    ProgressBar progressBar;

    String auction_id,staff_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productupdate);


        pupname=(EditText)findViewById(R.id.uauction_name);

       puptype=(EditText)findViewById(R.id.uauction_type);

        pupproduct=(EditText)findViewById(R.id.uproduct_name);

        pupdate=(EditText)findViewById(R.id.uauction_date);

        pupqty=(EditText)findViewById(R.id.aauction_qty);

        pupspeci=(EditText)findViewById(R.id.aauctionspecification);


        progressBar=(ProgressBar)findViewById(R.id.progressBar02);


        pupdates=(Button)findViewById(R.id.auction_save);

        Bundle b=getIntent().getExtras();

        if(b!=null)
        {



            pupname.setText(""+b.getString("aname"));
            puptype.setText(""+b.getString("atype"));
            pupproduct.setText(""+b.getString("aproduct"));
            pupdate.setText(""+b.getString("adates"));

            auction_id=b.getString("aid");
            staff_ids=b.getString("staff_id");

           Toast.makeText(productupdate.this, ""+auction_id, Toast.LENGTH_SHORT).show();
           Toast.makeText(productupdate.this, ""+staff_ids, Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, ""+auction_id, Toast.LENGTH_SHORT).show();
        }


        pupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable()){

                    new productupdatedateupdate().execute();
                }
                else
                {

                    Toast.makeText(productupdate.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public class productupdatedateupdate extends AsyncTask<String, String, String> {

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
            nameValuePairs.add(new BasicNameValuePair("aid", "" + auction_id));
            nameValuePairs.add(new BasicNameValuePair("uid", "" + staff_ids));
            nameValuePairs.add(new BasicNameValuePair("qty", "" + pupqty.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("specification", "" + pupspeci.getText().toString()));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/display.php");
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
                Toast.makeText(productupdate.this, "" + result, Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                Log.e("log_tag",
                        "Error in http connection" + e.toString());
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            progressBar.setVisibility(View.GONE);
            Toast.makeText(productupdate.this, "" + s, Toast.LENGTH_SHORT).show();



        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}