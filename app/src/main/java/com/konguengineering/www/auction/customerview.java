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

public class customerview extends AppCompatActivity {

    ListView cuslist;
    ArrayList<Integer> jid = new ArrayList<>();
    ArrayList<String> jcmpnyname = new ArrayList<>();
    ArrayList<String> jownername = new ArrayList<>();
    ArrayList<String> jphone = new ArrayList<>();
    ArrayList<String> jmailid = new ArrayList<>();
    ArrayList<String> jpan = new ArrayList<>();
    ArrayList<String> jgst = new ArrayList<>();

    int preLast_id;
    int Last;
    String id_val = "";
    int preLast;
    ProgressBar progressBar;

    customeradapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerview);



        progressBar=(ProgressBar)findViewById(R.id.progressBar17);


        cuslist=(ListView)findViewById(R.id.customerlistdata);

        jid.clear();
        jcmpnyname.clear();
        jownername.clear();
        jphone.clear();
        jmailid.clear();
        jpan.clear();
        jgst.clear();



        new customerdatasdata_load().execute();

        adapter = new customeradapter(getApplicationContext(), jid, jcmpnyname,jownername,jphone,jmailid,jpan,jgst);

        cuslist.setAdapter(adapter);



    }



    public class customerdatasdata_load extends AsyncTask<String, String, String> {

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
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/customer.php");
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
            String kcompany,kowner,kphone,kmail,kpan,kgstno;

            //[{"id":"4","company_name":"appsmine infotech","cus_name":"karthi","phone":"8973276379",
            // "mail_id":"karthiece.t@gmail.com","pan_no":"1253698000","gst_no":"12548sdd250",
            // "address":"107 west street","pass":"143","datetimes":"2019-03-11 04:46:01","deltype":"1"}]
            if (s != null) {

                try {


                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    //preLast = preLast + jArray.length();
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        id1 = json_data.getInt("id");
                        kcompany = json_data.getString("company_name");
                        kowner = json_data.getString("cus_name");
                        kphone = json_data.getString("phone");
                        kmail = json_data.getString("mail_id");
                        kpan = json_data.getString("pan_no");
                        kgstno = json_data.getString("gst_no");



                        id_val = "" + id1;

                        // System.out.println("category==" + category1);
                        jid.add(id1);
                        jcmpnyname.add(kcompany);
                        jownername.add(kowner);
                        jphone.add(kphone);
                        jmailid.add(kmail);
                        jpan.add(kpan);
                        jgst.add(kgstno);


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
    public class customeradapter extends BaseAdapter {

        TextView lcompany,lname,lphone,lmail,lpan,lgst;

        Context mcontext;
        ArrayList<Integer> jid = new ArrayList<>();
        ArrayList<String> jcmpnyname = new ArrayList<>();
        ArrayList<String> jownername = new ArrayList<>();
        ArrayList<String> jphone = new ArrayList<>();
        ArrayList<String> jmailid = new ArrayList<>();
        ArrayList<String> jpan = new ArrayList<>();
        ArrayList<String> jgst = new ArrayList<>();



        public customeradapter(Context c,ArrayList<Integer> jid, ArrayList<String> jcmpnyname,ArrayList<String> jownername,ArrayList<String> jphone,
                               ArrayList<String> jmailid,ArrayList<String> jpan,ArrayList<String> jgst){


            mcontext=c;
            this.jid=jid;
            this.jcmpnyname=jcmpnyname;
            this.jownername=jownername;
            this.jphone=jphone;
            this.jmailid=jmailid;
            this.jpan=jpan;
            this.jgst=jgst;


        }

        @Override
        public int getCount() {
            return jid.size();
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
            video_view = inflater.inflate(R.layout.cmpnyview, null);

            lcompany=(TextView)video_view.findViewById(R.id.gusername01);
            lname=(TextView)video_view.findViewById(R.id.guserphone01);
            lphone=(TextView)video_view.findViewById(R.id.gauctionname01);
            lmail=(TextView)video_view.findViewById(R.id.gauctiontype01);
            lpan=(TextView)video_view.findViewById(R.id.gauctionprod01);
            lgst=(TextView)video_view.findViewById(R.id.gactqty01);




            lcompany.setText(""+jcmpnyname.get(position));
            lname.setText(""+jownername.get(position));
            lphone.setText(""+jphone.get(position));
            lmail.setText(""+jmailid.get(position));
            lpan.setText(""+jpan.get(position));
            lgst.setText(""+jgst.get(position));



            return video_view;
        }
    }
}