package com.konguengineering.www.auction;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button login,creg,cusreg;

    EditText username,password;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       login = (Button)findViewById(R.id.login);

       creg =(Button)findViewById(R.id.register);

       cusreg = (Button)findViewById(R.id.cusreg);

       username =(EditText)findViewById(R.id.name);

       password = (EditText)findViewById(R.id.password);

       progressBar = (ProgressBar)findViewById(R.id.progressBar);


       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (isNetworkAvailable()){

                   new UserChecks().execute();
               }
                else {

                   Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
               }


           }
       });

       creg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent a = new Intent(MainActivity.this,register.class);
               startActivity(a);

           }
       });

       cusreg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent b = new Intent(MainActivity.this,cusregister.class);

               startActivity(b);
           }
       });



    }


    public class UserChecks extends AsyncTask<String, String, String> {


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
            nameValuePairs.add(new BasicNameValuePair("action", "update"));
            nameValuePairs.add(new BasicNameValuePair("name", ""+username.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("pass", ""+password.getText().toString()));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/login.php");
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



            } catch (Exception e) {
                Log.e("log_tag",
                        "Error in http connection" + e.toString());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            if(s.contains("admin")){

                Toast.makeText(MainActivity.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,adminpage.class);
                startActivity(i);
            }
            else if((s.contains("fail"))) {

                Toast.makeText(MainActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
            }


            //Customer login methods
            else if ((s.contains("cus"))){

                int id1 = 0;
                int cate1=0;

                String name1 = null;
                progressBar.setVisibility(View.GONE);


                if(s!=null){

                    try {
                        JSONArray jArray = new JSONArray(s);
                        JSONObject json_data;
                        //preLast = preLast + jArray.length();
                        for(int i=0;i<jArray.length();i++){
                            json_data = jArray.getJSONObject(i);
                            id1=json_data.getInt("id");
                           // name1=json_data.getString("company_name");
                            //cate1=json_data.getInt("phone");
                            System.out.println("login"+id1);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("json error");
                    }
                }


                if(id1!=0){

                    Toast.makeText(MainActivity.this, "User Login Successful", Toast.LENGTH_SHORT).show();

                   /* Intent i=new Intent(MainActivity.this,AllView.class);
                    i.putExtra("url","http://www.appsmineinfotech.com/apps/clientview.php?id="+id1);
                    startActivity(i);*/

                    Intent i=new Intent(MainActivity.this,userpage.class);
                    i.putExtra("id",""+id1);
                  //  i.putExtra("company_name",""+name1);
                  //  i.putExtra("phone",""+cate1);
                    startActivity(i);

                }

                else {

                    Toast.makeText(MainActivity.this, "Please try Again", Toast.LENGTH_SHORT).show();
                }

            }


            //Former loginpages
            else if ((s.contains("former"))){

                int idf1 = 0;
                int fphone1=0;

                String fname1 = null;
                progressBar.setVisibility(View.GONE);


                if(s!=null){

                    try {
                        JSONArray jArray = new JSONArray(s);
                        JSONObject json_data;
                        //preLast = preLast + jArray.length();
                        for(int i=0;i<jArray.length();i++){
                            json_data = jArray.getJSONObject(i);
                            idf1=json_data.getInt("id");
                          //  fname1=json_data.getString("company_name");
                          //  fphone1=json_data.getInt("phone");
                            System.out.println("login"+idf1);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("json error");
                    }
                }


                if(idf1!=0){

                    Toast.makeText(MainActivity.this, "Former  Login Successful", Toast.LENGTH_SHORT).show();

                   /* Intent i=new Intent(MainActivity.this,AllView.class);
                    i.putExtra("url","http://www.appsmineinfotech.com/apps/clientview.php?id="+id1);
                    startActivity(i);*/

                    Intent j=new Intent(MainActivity.this,former_main.class);
                    j.putExtra("id",""+idf1);
                   // j.putExtra("user_name",""+fname1);
                   // j.putExtra("phone",""+fphone1);
                    startActivity(j);

                }

                else {

                    Toast.makeText(MainActivity.this, "Please try Again", Toast.LENGTH_SHORT).show();
                }

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
