package com.konguengineering.www.auction;

import android.content.Intent;
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

public class auction_create extends AppCompatActivity {

    EditText aname,atype,aproduct;

    Button auction_save;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_create);

        aname=(EditText)findViewById(R.id.auction_name);

        atype=(EditText)findViewById(R.id.auction_type);

        aproduct =(EditText)findViewById(R.id.product_name);

        progressBar=(ProgressBar)findViewById(R.id.progressBar02);

        auction_save=(Button)findViewById(R.id.auction_save);

        auction_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (aname.getText().toString().length()==0 || atype.getText().toString().length()==0 || aproduct.getText().toString().length()==0){

                    Toast.makeText(auction_create.this,"Enter the all field",Toast.LENGTH_LONG).show();


                }

                else
                {
                    new Auctioncreate().execute();

                }


            }
        });

    }


    public class Auctioncreate extends AsyncTask<String, String, String> {

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
            nameValuePairs.add(new BasicNameValuePair("aname", ""+aname.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("atype", ""+atype.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("product", ""+aproduct.getText().toString()));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com//auction/auction_create.php");
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
                Toast.makeText(auction_create.this, ""+result, Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                Log.e("log_tag",
                        "Error in http connection" + e.toString());
            }




            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            progressBar.setVisibility(View.GONE);
            Toast.makeText(auction_create.this, ""+s, Toast.LENGTH_SHORT).show();

            if(s.contains("Auction Create Successfully")) {

                Intent abcd= new Intent(auction_create.this, adminpage.class);

                startActivity(abcd);
            }



        }
    }








}
