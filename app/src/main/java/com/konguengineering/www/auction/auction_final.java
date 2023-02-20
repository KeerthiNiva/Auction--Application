package com.konguengineering.www.auction;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class auction_final extends AppCompatActivity {

    ListView userlist;
    ArrayList<Integer> iuaid = new ArrayList<>();
    ArrayList<String> icusid = new ArrayList<>();
    ArrayList<String> iusername = new ArrayList<>();
    ArrayList<String> iuserphone = new ArrayList<>();
    ArrayList<String> iproduct = new ArrayList<>();
    ArrayList<String> iqty = new ArrayList<>();
    ArrayList<String> irate = new ArrayList<>();
    ArrayList<String> icompany = new ArrayList<>();
    ArrayList<String> icphone = new ArrayList<>();
    ArrayList<String> icdatej = new ArrayList<>();


    int preLast_id;
    int Last;
    String id_val = "";
    int preLast;


    adminauctionfinaladapter adapter;

    String staff_id;

    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_final);

        userlist = (ListView) findViewById(R.id.adminauctionfinal);
        progressBar = (ProgressBar) findViewById(R.id.progressBar15);

        iuaid.clear();
        icusid.clear();
        iusername.clear();
        iuserphone.clear();
        iproduct.clear();
        iqty.clear();
        irate.clear();
        icompany.clear();
        icphone.clear();
        icdatej.clear();




        new adminauctiontedata_load().execute();

        adapter = new adminauctionfinaladapter(getApplicationContext(), iuaid,icusid,iusername,iuserphone, iproduct,iqty,irate,icompany,icphone,icdatej);

        userlist.setAdapter(adapter);



    }

    public class adminauctiontedata_load extends AsyncTask<String, String, String> {

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
           // nameValuePairs.add(new BasicNameValuePair("userids", "" + staff_id));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/final_view.php");
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
            String ffcusid, ffusername, ffuserphone,ffproduct,ffspec,ffqty,ffrate,ffdeltype,ffdatej,ffcompanyname,ffcusname,ffphone;


            if (s != null) {

                try {


                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    //preLast = preLast + jArray.length();
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        id1 = json_data.getInt("id");
                        ffcusid = json_data.getString("cusid");
                        ffusername = json_data.getString("username");
                        ffuserphone = json_data.getString("user_phone");
                        ffproduct = json_data.getString("product");
                        ffspec = json_data.getString("specification");
                        ffqty = json_data.getString("qty");
                        ffrate = json_data.getString("rate");
                        ffdeltype = json_data.getString("deltype");
                        ffdatej = json_data.getString("datej");
                        ffcompanyname = json_data.getString("company_name");
                        ffcusname = json_data.getString("cus_name");
                        ffphone = json_data.getString("phone");

                        id_val = "" + id1;

                        // System.out.println("category==" + category1);
                        iuaid.add(id1);
                        icusid.add(ffcusid);
                        iusername.add(ffusername);
                        iuserphone.add(ffuserphone);
                        iproduct.add(ffproduct);
                        iqty.add(ffqty);
                        irate.add(ffrate);
                        icompany.add(ffcompanyname);
                        icphone.add(ffphone);
                        icdatej.add(ffdatej);


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
    public class adminauctionfinaladapter extends BaseAdapter {

        TextView ycname,yphone,yproduct,yrate,yqry,yuser,ycphne,ycdatej;

        Context mcontext;
        ArrayList<Integer> iuaid = new ArrayList<>();
        ArrayList<String> icusid = new ArrayList<>();
        ArrayList<String> iusername = new ArrayList<>();
        ArrayList<String> iuserphone = new ArrayList<>();
        ArrayList<String> iproduct = new ArrayList<>();
        ArrayList<String> iqty = new ArrayList<>();
        ArrayList<String> irate = new ArrayList<>();
        ArrayList<String> icompany = new ArrayList<>();
        ArrayList<String> icphone = new ArrayList<>();
        ArrayList<String> icdatej = new ArrayList<>();



        public adminauctionfinaladapter(Context c,ArrayList<Integer> iuaid,ArrayList<String> icusid,ArrayList<String> iusername,ArrayList<String> iuserphone, ArrayList<String> iproduct,ArrayList<String> iqty
        ,ArrayList<String> irate, ArrayList<String> icompany,ArrayList<String> icphone,ArrayList<String> icdatej){


            mcontext=c;
            this.iuaid=iuaid;
            this.icusid=icusid;
            this.iusername=iusername;
            this.iuserphone=iuserphone;
            this.iproduct=iproduct;
            this.iqty=iqty;
            this.irate=irate;
            this.icompany=icompany;
            this.icphone=icphone;
            this.icdatej=icdatej;



        }

        @Override
        public int getCount() {
            return iuaid.size();
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
            video_view = inflater.inflate(R.layout.adminview, null);

            yuser=(TextView)video_view.findViewById(R.id.gusername01);
            yphone=(TextView)video_view.findViewById(R.id.guserphone01);
            yproduct=(TextView)video_view.findViewById(R.id.gauctionname01);
            yqry=(TextView)video_view.findViewById(R.id.gauctiontype01);
            yrate=(TextView)video_view.findViewById(R.id.gauctionprod01);
            ycname=(TextView)video_view.findViewById(R.id.gcusname01);
            ycphne=(TextView)video_view.findViewById(R.id.gphone01);
            ycdatej=(TextView)video_view.findViewById(R.id.gdatejaa01);




            yuser.setText(""+iusername.get(position));
            yphone.setText(""+iuserphone.get(position));
            yproduct.setText(""+iproduct.get(position));
            yqry.setText(""+iqty.get(position));
            yrate.setText(""+irate.get(position));
            ycname.setText(""+icompany.get(position));
            ycphne.setText(""+icphone.get(position));
            ycdatej.setText(""+icdatej.get(position));



            return video_view;
        }
    }
}

