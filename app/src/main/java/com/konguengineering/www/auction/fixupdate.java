package com.konguengineering.www.auction;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;

public class fixupdate extends AppCompatActivity {

    EditText upname,uptype,upproduct,update;

    Button updates;

    ProgressBar progressBar;

    String auction_id;

    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixupdate);


        upname=(EditText)findViewById(R.id.uauction_name);

        uptype=(EditText)findViewById(R.id.uauction_type);

        upproduct=(EditText)findViewById(R.id.uproduct_name);

        update=(EditText)findViewById(R.id.uauction_date);

        progressBar=(ProgressBar)findViewById(R.id.progressBar02);


       updates=(Button)findViewById(R.id.auction_save);

        Bundle b=getIntent().getExtras();

        if(b!=null)
        {


            upname.setText(""+b.getString("aname"));
            uptype.setText(""+b.getString("atype"));
            upproduct.setText(""+b.getString("aproduct"));

            auction_id=b.getString("aid");

            Toast.makeText(fixupdate.this, ""+auction_id, Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, ""+auction_id, Toast.LENGTH_SHORT).show();
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar= Calendar.getInstance();

                int year=calendar.get(Calendar.YEAR);

                int month=calendar.get(Calendar.MONTH);

                final int day=calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog= new DatePickerDialog(fixupdate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        update.setText(year+"-"+(month+1)+"-"+day);



                    }
                },year,month,day);

                datePickerDialog.show();


            }
        });



        updates.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(isNetworkAvailable()){

                   new fixeddateupdate().execute();
               }
               else
               {

                   Toast.makeText(fixupdate.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
               }

           }
       });

    }
    public class fixeddateupdate extends AsyncTask<String, String, String> {

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
            nameValuePairs.add(new BasicNameValuePair("dates", "" + update.getText().toString()));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/auction_fix.php");
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
                Toast.makeText(fixupdate.this, "" + result, Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                Log.e("log_tag",
                        "Error in http connection" + e.toString());
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            progressBar.setVisibility(View.GONE);
            Toast.makeText(fixupdate.this, "" + s, Toast.LENGTH_SHORT).show();

            if(s.contains("Auction Date Fix Successfully")) {

                Intent ab = new Intent(fixupdate.this, adminpage.class);

                startActivity(ab);
            }


        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
