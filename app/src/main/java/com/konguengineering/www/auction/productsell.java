package com.konguengineering.www.auction;

import android.content.Context;
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
import android.widget.ListView;
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

public class productsell extends AppCompatActivity {

    ListView userlist;
    ArrayList<Integer> aid = new ArrayList<>();
    ArrayList<String> aname = new ArrayList<>();
    ArrayList<String> atype = new ArrayList<>();
    ArrayList<String> aproduct = new ArrayList<>();
    ArrayList<String> adates = new ArrayList<>();

    int preLast_id;
    int Last;
    String id_val = "";
    int preLast;
    ProgressBar progressBar;

    productqtylist adapter;

    String staff_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productsell);

        userlist = (ListView) findViewById(R.id.productselllist);
        progressBar = (ProgressBar) findViewById(R.id.progressBar10);

        aid.clear();
        aname.clear();
        atype.clear();
        aproduct.clear();
        adates.clear();


        new productqtyupdatedata_load().execute();

        adapter = new productqtylist(getApplicationContext(), aid, aname,atype,aproduct,adates);

        userlist.setAdapter(adapter);


        Bundle b=getIntent().getExtras();

        if(b!=null)
        {

            staff_id=b.getString("id");

            Toast.makeText(productsell.this, ""+staff_id, Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, ""+auction_id, Toast.LENGTH_SHORT).show();
        }
    }

    public class productqtyupdatedata_load extends AsyncTask<String, String, String> {

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
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/auction_view.php");
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
            String auname, autype, auproduct,auids,audates,audelete;

            //"id":"3","aid":"3","fixdates":"30-03-2019","deltype":"1","aname":"weekly auction","atype":"nature product","product":"coconut"

            if (s != null) {

                try {


                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    //preLast = preLast + jArray.length();
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        id1 = json_data.getInt("id");
                        auids = json_data.getString("aid");
                        audates = json_data.getString("fixdates");
                        audelete = json_data.getString("deltype");
                        auname = json_data.getString("aname");
                        autype = json_data.getString("atype");
                        auproduct = json_data.getString("product");



                        id_val = "" + id1;

                        // System.out.println("category==" + category1);
                        aid.add(id1);
                        aname.add(auname);
                        atype.add(autype);
                        aproduct.add(auproduct);
                        adates.add(audates);
                        preLast_id = id1;

                    }

                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {

            }


        }
    }
    public class productqtylist extends BaseAdapter {

        TextView anname,antype,anproduct,andates;

        Button anupdate;


        Context mcontext;
        ArrayList<Integer> aid = new ArrayList<>();
        ArrayList<String> aname = new ArrayList<>();
        ArrayList<String> atype = new ArrayList<>();
        ArrayList<String> aproduct = new ArrayList<>();
        ArrayList<String> adates = new ArrayList<>();



        public productqtylist(Context c,ArrayList<Integer> aid, ArrayList<String> aname,ArrayList<String> atype,ArrayList<String> aproduct,ArrayList<String> adates){


            mcontext=c;
            this.aid=aid;
            this.aname=aname;
            this.atype=atype;
            this.aproduct=aproduct;
            this.adates=adates;


        }

        @Override
        public int getCount() {
            return aid.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final View video_view;
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            video_view = inflater.inflate(R.layout.sellproduct, null);

            anname=(TextView)video_view.findViewById(R.id.name_txt);
            antype=(TextView)video_view.findViewById(R.id.phno_txt);
            anproduct=(TextView)video_view.findViewById(R.id.ratesorder);
            andates=(TextView)video_view.findViewById(R.id.odatesj);
            anupdate=(Button) video_view.findViewById(R.id.sellproducts);

            anupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(productsell.this,productupdate.class);

                    i.putExtra("aid",""+aid.get(position));
                    i.putExtra("aname",""+aname.get(position));
                    i.putExtra("atype",""+atype.get(position));
                    i.putExtra("aproduct",""+aproduct.get(position));
                    i.putExtra("adates",""+adates.get(position));
                    i.putExtra("staff_id",""+staff_id);


                    finish();
                    startActivity(i);

                }
            });



            anname.setText(""+aname.get(position));
            antype.setText(""+atype.get(position));
            anproduct.setText(""+aproduct.get(position));
            andates.setText(""+adates.get(position));


            return video_view;
        }
    }
}