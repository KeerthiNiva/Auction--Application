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

public class cusregister extends AppCompatActivity {


    EditText cname,clientname,cphone,cmail,cpan,cgstno,caddress,cuspassword;

    Button csave;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusregister);

        cname=(EditText)findViewById(R.id.companyname);

        clientname=(EditText)findViewById(R.id.name);

        cphone=(EditText)findViewById(R.id.phone);

        cmail =(EditText)findViewById(R.id.mail);

        cpan =(EditText)findViewById(R.id.pan);

        cgstno =(EditText)findViewById(R.id.gst);

        caddress =(EditText)findViewById(R.id.address);

        cuspassword=(EditText)findViewById(R.id.cuspass);



        csave =(Button)findViewById(R.id.save);

        progressBar = (ProgressBar)findViewById(R.id.progressBar01);


        csave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cname.getText().toString().length()==0 || clientname.getText().toString().length()==0 ||
                        cmail.getText().toString().length()==0 || cpan.getText().toString().length()==0 || cgstno.getText().toString().length()==0 ||
                        caddress.getText().toString().length()==0 )
                {

                    Toast.makeText(cusregister.this, "Enter the valid field", Toast.LENGTH_SHORT).show();


                }

                else if (cphone.getText().toString().length() ==10){

                    new ClientCheckrequest().execute();


                }

                else {
                    Toast.makeText(cusregister.this,"Enter the Valid Phone Number",Toast.LENGTH_SHORT).show();

                }



            }
        });

    }

    public class ClientCheckrequest extends AsyncTask<String, String, String> {

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
            nameValuePairs.add(new BasicNameValuePair("cname", ""+cname.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("clientname", ""+clientname.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("phone", ""+cphone.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("mail", ""+cmail.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("pan", ""+cpan.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("gst", ""+cgstno.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("address", ""+caddress.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("pass", ""+cuspassword.getText().toString()));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/cusreg.php");
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
                Toast.makeText(cusregister.this, ""+result, Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                Log.e("log_tag",
                        "Error in http connection" + e.toString());
            }




            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            progressBar.setVisibility(View.GONE);
            Toast.makeText(cusregister.this, ""+s, Toast.LENGTH_SHORT).show();

            if(s.contains("Customer Register Successfully")) {

                Intent abc = new Intent(cusregister.this, MainActivity.class);

                startActivity(abc);
            }



        }
    }






}
