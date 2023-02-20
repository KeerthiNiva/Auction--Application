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

public class register extends AppCompatActivity {

    EditText name,phone,mail,address,city,district,password,aadharnumber;

    ProgressBar progressBar;

    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.name);
        phone =(EditText)findViewById(R.id.phone);
        mail=(EditText)findViewById(R.id.mail);
        address=(EditText)findViewById(R.id.address);
        city=(EditText)findViewById(R.id.city);
        district=(EditText)findViewById(R.id.district);
        password=(EditText)findViewById(R.id.pass);
        aadharnumber=(EditText)findViewById(R.id.aadhar);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        save=(Button)findViewById(R.id.save);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (name.getText().toString().length()==0 || mail.getText().toString().length()==0  ||
                        address.getText().toString().length()==0 ||
                        city.getText().toString().length()==0 ||
                        district.getText().toString().length()==0 ||aadharnumber.getText().toString().length()==0)
                {

                    Toast.makeText(register.this, "Enter The All Field", Toast.LENGTH_SHORT).show();

                }

                else if (phone.getText().toString().length() ==10){

                    new  UserCheckrequest().execute();


                }

                else {
                    Toast.makeText(register.this,"Enter the Valid Phone Number",Toast.LENGTH_SHORT).show();

                }




            }
        });
    }


    public class UserCheckrequest extends AsyncTask<String, String, String> {

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
            nameValuePairs.add(new BasicNameValuePair("name", ""+name.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("phone", ""+phone.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("mail", ""+mail.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("address", ""+address.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("city", ""+city.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("district", ""+district.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("pass", ""+password.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("aadhar", ""+aadharnumber.getText().toString()));





            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/register.php");
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
                Toast.makeText(register.this, ""+result, Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                Log.e("log_tag",
                        "Error in http connection" + e.toString());
            }




            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            progressBar.setVisibility(View.GONE);
            Toast.makeText(register.this, ""+s, Toast.LENGTH_SHORT).show();

            if(s.contains("Client Register Successfully")) {

                Intent ab = new Intent(register.this, MainActivity.class);

                startActivity(ab);
            }



        }
    }

}
