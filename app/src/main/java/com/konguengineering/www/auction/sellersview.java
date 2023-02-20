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

public class sellersview extends AppCompatActivity {

    ListView cuslist;
    ArrayList<Integer> mid = new ArrayList<>();
    ArrayList<String> musername = new ArrayList<>();
    ArrayList<String> muserphone = new ArrayList<>();
    ArrayList<String> mmail = new ArrayList<>();
    ArrayList<String> maddress = new ArrayList<>();
    ArrayList<String> mcity = new ArrayList<>();
    ArrayList<String> mdistrict = new ArrayList<>();

    int preLast_id;
    int Last;
    String id_val = "";
    int preLast;
    ProgressBar progressBar;

    sellersadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellersview);



        progressBar=(ProgressBar)findViewById(R.id.progressBar16);


        cuslist=(ListView)findViewById(R.id.sellerdatalist);

        mid.clear();
        musername.clear();
        muserphone.clear();
        mmail.clear();
        maddress.clear();
        mcity.clear();
        mdistrict.clear();



        new sellerviewsdata_load().execute();

        adapter = new sellersadapter(getApplicationContext(), mid, musername,muserphone,mmail,maddress,mcity,mdistrict);

        cuslist.setAdapter(adapter);



    }



    public class sellerviewsdata_load extends AsyncTask<String, String, String> {

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
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/seller.php");
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
            String kusername,kphone,kmail,kaddress,kcity,kdistrict;

            //
            //[{"id":"2","user_name":"karthi","phone":"9659599191","mail_id":"karthiappsmine@gmail.com",
            // "address":"107 west street","city":"solar","district":"erode","adhar_no":"",
            // "password":"143"}]
            if (s != null) {

                try {


                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    //preLast = preLast + jArray.length();
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        id1 = json_data.getInt("id");
                        kusername = json_data.getString("user_name");
                        kphone = json_data.getString("phone");
                        kmail = json_data.getString("mail_id");
                        kaddress = json_data.getString("address");
                        kcity = json_data.getString("city");
                        kdistrict = json_data.getString("district");



                        id_val = "" + id1;

                        // System.out.println("category==" + category1);
                        mid.add(id1);
                        musername.add(kusername);
                        muserphone.add(kphone);
                        mmail.add(kmail);
                        maddress.add(kaddress);
                        mcity.add(kcity);
                        mdistrict.add(kdistrict);


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
    public class sellersadapter extends BaseAdapter {

        TextView nusername,nphone,nmail,naddress,ncity,ndistrict;

        Context mcontext;
        ArrayList<Integer> mid = new ArrayList<>();
        ArrayList<String> musername = new ArrayList<>();
        ArrayList<String> muserphone = new ArrayList<>();
        ArrayList<String> mmail = new ArrayList<>();
        ArrayList<String> maddress = new ArrayList<>();
        ArrayList<String> mcity = new ArrayList<>();
        ArrayList<String> mdistrict = new ArrayList<>();


        public sellersadapter(Context c,ArrayList<Integer> mid, ArrayList<String> musername,ArrayList<String> muserphone,ArrayList<String> mmail,
                               ArrayList<String> maddress,ArrayList<String> mcity,ArrayList<String> mdistrict){


            mcontext=c;
            this.mid=mid;
            this.musername=musername;
            this.muserphone=muserphone;
            this.mmail=mmail;
            this.maddress=maddress;
            this.mcity=mcity;
            this.mdistrict=mdistrict;


        }

        @Override
        public int getCount() {
            return mid.size();
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
            video_view = inflater.inflate(R.layout.sellerviews, null);

            nusername=(TextView)video_view.findViewById(R.id.gusername01);
            nphone=(TextView)video_view.findViewById(R.id.guserphone01);
            nmail=(TextView)video_view.findViewById(R.id.gauctionname01);
            naddress=(TextView)video_view.findViewById(R.id.gauctiontype01);
            ncity=(TextView)video_view.findViewById(R.id.gauctionprod01);
            ndistrict=(TextView)video_view.findViewById(R.id.gactqty01);




            nusername.setText(""+musername.get(position));
            nphone.setText(""+muserphone.get(position));
            nmail.setText(""+mmail.get(position));
            naddress.setText(""+maddress.get(position));
            ncity.setText(""+mcity.get(position));
            ndistrict.setText(""+mdistrict.get(position));



            return video_view;
        }
    }
}