package com.konguengineering.www.auction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class former_main extends AppCompatActivity {

    Button sellproduct,final_auction,sfeedback;
    String staff_id;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_former_main);

        sellproduct=(Button)findViewById(R.id.product_update);
        final_auction=(Button)findViewById(R.id.userauction_final);
        sfeedback=(Button)findViewById(R.id.ffeedback);

        progressBar=(ProgressBar)findViewById(R.id.progressBar25);




        Bundle b=getIntent().getExtras();

        if(b!=null){

            staff_id=b.getString("id");

           // Toast.makeText(this, ""+staff_id, Toast.LENGTH_SHORT).show();

            new formerlogindatas().execute();

        }



        sellproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(former_main.this,productsell.class);

                a.putExtra("id",""+staff_id);
                startActivity(a);


            }
        });

        final_auction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent b = new Intent(former_main.this,userauctionfinal.class);

                b.putExtra("id",""+staff_id);

                startActivity(b);

            }
        });


        sfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent c =new Intent(former_main.this,formerfeed.class);

                c.putExtra("id",""+staff_id);

                startActivity(c);
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You want to logout");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent myIntent = new Intent(former_main.this,
                                MainActivity.class);


                        startActivity(myIntent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public class formerlogindatas extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            //userlist.addFooterView(progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            InputStream is = null;
            StringBuilder sb = null;
            String result = null;

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("action", "view"));
            // nameValuePairs.add(new BasicNameValuePair("id", "" + staff_id));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/alert.php");
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

            } catch (Exception e) {
                Log.e("log_tag",
                        "Error in http connection" + e.toString());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            // TODO Auto-generated method stub
            //userlist.removeFooterView(progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            System.out.println("=====/////post" + s);
            int id1;
            String nids,nfixdate,ndeltype,nauctionname,nauctiontype,nproduct;

            //[{"id":"4","aid":"4","fixdates":"2019-03-27","deltype":"1","aname":"erode turmeric",
            // "atype":"weekly","product":"turmeric","dates":"2019-03-19 14:13:53"}]


            if (s != null) {

                try {
                    //


                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    //preLast = preLast + jArray.length();
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        id1 = json_data.getInt("id");
                        nids = json_data.getString("aid");
                        nfixdate = json_data.getString("fixdates");
                        ndeltype = json_data.getString("deltype");
                        nauctionname = json_data.getString("aname");
                        nauctiontype = json_data.getString("atype");
                        nproduct = json_data.getString("product");


                        AlertDialog.Builder alertDialogBuilders = new AlertDialog.Builder(former_main.this);
                        alertDialogBuilders.setMessage("Newly Create Auction Date "+nfixdate +" product name "+nproduct);


                        AlertDialog alertDialogs= alertDialogBuilders.create();
                        alertDialogs.show();

                      // Toast.makeText(former_main.this, ""+id1, Toast.LENGTH_SHORT).show();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {

            }


        }
    }


}