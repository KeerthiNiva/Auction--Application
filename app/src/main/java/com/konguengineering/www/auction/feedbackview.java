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

public class feedbackview extends AppCompatActivity {

    ListView cuslist;
    ArrayList<Integer> feedid = new ArrayList<>();
    ArrayList<String> feedname = new ArrayList<>();
    ArrayList<String> feedphone = new ArrayList<>();
    ArrayList<String> feedmes = new ArrayList<>();
    ArrayList<String> feeddates = new ArrayList<>();


    int preLast_id;
    int Last;
    String id_val = "";
    int preLast;
    ProgressBar progressBar;

    feedbackadapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackview);


        progressBar=(ProgressBar)findViewById(R.id.progressBar18);


        cuslist=(ListView)findViewById(R.id.feedbacklistdata);


        feedid.clear();
        feedname.clear();
        feedphone.clear();
        feedmes.clear();
        feeddates.clear();


        new userfeeddata_load().execute();

        adapter = new feedbackadapter(getApplicationContext(), feedid, feedname,feedphone,feedmes,feeddates);

        cuslist.setAdapter(adapter);



    }



    public class userfeeddata_load extends AsyncTask<String, String, String> {

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
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/feedback.php");
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
            String mname,mphone,mdates,mmes;


            if (s != null) {

                try {
                    //


                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    //preLast = preLast + jArray.length();
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        id1 = json_data.getInt("id");
                        mname = json_data.getString("uname");
                        mphone = json_data.getString("uphone");
                        mdates = json_data.getString("mes");
                        mmes = json_data.getString("datej");




                        id_val = "" + id1;



                        // System.out.println("category==" + category1);
                        feedid.add(id1);
                        feedname.add(mname);
                        feedphone.add(mphone);
                        feedmes.add(mdates);
                        feeddates.add(mmes);


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
    public class feedbackadapter extends BaseAdapter {

        TextView fdname,fdphne,fdmes,fddatetime;

        Context mcontext;
        ArrayList<Integer> feedid = new ArrayList<>();
        ArrayList<String> feedname = new ArrayList<>();
        ArrayList<String> feedphone = new ArrayList<>();
        ArrayList<String> feedmes = new ArrayList<>();
        ArrayList<String> feeddates = new ArrayList<>();




        public feedbackadapter(Context c,ArrayList<Integer> feedid, ArrayList<String> feedname,ArrayList<String> feedphone,ArrayList<String> feedmes,
                               ArrayList<String> feeddates){


            mcontext=c;
            this.feedid=feedid;
            this.feedname=feedname;
            this.feedphone=feedphone;
            this.feedmes=feedmes;
            this.feeddates=feeddates;



        }

        @Override
        public int getCount() {
            return feedid.size();
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
            video_view = inflater.inflate(R.layout.feedviewss, null);

            fdname=(TextView)video_view.findViewById(R.id.gusername01);
            fdphne=(TextView)video_view.findViewById(R.id.guserphone01);
            fdmes=(TextView)video_view.findViewById(R.id.gauctionname01);
            fddatetime=(TextView)video_view.findViewById(R.id.gauctiontype01);




            fdname.setText(""+feedname.get(position));
            fdphne.setText(""+feedphone.get(position));
            fdmes.setText(""+feedmes.get(position));
            fddatetime.setText(""+feeddates.get(position));




            return video_view;
        }
    }
}