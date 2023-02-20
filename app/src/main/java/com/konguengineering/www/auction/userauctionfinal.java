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

public class userauctionfinal extends AppCompatActivity {

    ListView userlist;
    ArrayList<Integer> fuaid = new ArrayList<>();
    ArrayList<String> fuproductname = new ArrayList<>();
    ArrayList<String> fuqty = new ArrayList<>();
    ArrayList<String> furates = new ArrayList<>();
    ArrayList<String> fucname = new ArrayList<>();
    ArrayList<String> fuphone = new ArrayList<>();
    ArrayList<String> fdatej= new ArrayList<>();



    int preLast_id;
    int Last;
    String id_val = "";
    int preLast;
    ProgressBar progressBar;

    productqtylistsave adapter;

    String staff_id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userauctionfinal);


        userlist = (ListView) findViewById(R.id.userauctionfinal);
        progressBar = (ProgressBar) findViewById(R.id.progressBar11);

        fuaid.clear();
        fuproductname.clear();
        fuqty.clear();
        furates.clear();
        fucname.clear();
        fuphone.clear();
        fdatej.clear();


        Bundle b=getIntent().getExtras();

        if(b!=null)
        {

            staff_id=b.getString("id");

            Toast.makeText(userauctionfinal.this, ""+staff_id, Toast.LENGTH_SHORT).show();


        }


        new finalqtyupdatedata_load().execute();

        adapter = new productqtylistsave(getApplicationContext(), fuaid,fuproductname,fuqty,furates, fucname,fuphone,fdatej);

        userlist.setAdapter(adapter);



    }

    public class finalqtyupdatedata_load extends AsyncTask<String, String, String> {

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
            nameValuePairs.add(new BasicNameValuePair("userids", "" + staff_id));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/userfinal.php");
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

          //[{"id":"4","cusid":"4","username":"karthi","user_phone":"9659599191","product":"water melon",
            // "specification":"sample karthi","qty":"150kg","rate":"260000","deltype":"1","datej":"30-05-2019",
            // "company_name":"appsmine infotech","cus_name":"karthi","phone":"8973276379","mail_id":"karthiece.t@gmail.com",
            // "pan_no":"1253698000","gst_no":"12548sdd250","address":"107 west street","pass":"143","datetimes":"2019-03-11 04:46:01"}
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
                        fuaid.add(id1);
                        fuproductname.add(ffproduct);
                        fuqty.add(ffqty);
                        furates.add(ffrate);
                        fucname.add(ffcompanyname);
                        fuphone.add(ffphone);
                        fdatej.add(ffdatej);


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
    public class productqtylistsave extends BaseAdapter {

        TextView ycname,yphone,yproduct,yrate,yqry,ydates;

        Context mcontext;
        ArrayList<Integer> fuaid = new ArrayList<>();
        ArrayList<String> fuproductname = new ArrayList<>();
        ArrayList<String> fuqty = new ArrayList<>();
        ArrayList<String> furates = new ArrayList<>();
        ArrayList<String> fucname = new ArrayList<>();
        ArrayList<String> fuphone = new ArrayList<>();
        ArrayList<String> fdatej = new ArrayList<>();



        public productqtylistsave(Context c,ArrayList<Integer> fuaid,ArrayList<String> fuproductname,ArrayList<String> fuqty,ArrayList<String> furates, ArrayList<String> fucname,ArrayList<String> fuphone,ArrayList<String> fdatej){


            mcontext=c;
            this.fuaid=fuaid;
            this.fuproductname=fuproductname;
            this.fuqty=fuqty;
            this.furates=furates;
            this.fucname=fucname;
            this.fuphone=fuphone;
            this.fdatej=fdatej;



        }

        @Override
        public int getCount() {
            return fuaid.size();
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
            video_view = inflater.inflate(R.layout.userproduct, null);

            ycname=(TextView)video_view.findViewById(R.id.gusername01);
            yphone=(TextView)video_view.findViewById(R.id.guserphone01);
            yproduct=(TextView)video_view.findViewById(R.id.gauctionname01);
            yqry=(TextView)video_view.findViewById(R.id.gauctiontype01);
            yrate=(TextView)video_view.findViewById(R.id.gauctionprod01);
            ydates=(TextView)video_view.findViewById(R.id.gauctiondatej01);



            ycname.setText(""+fucname.get(position));
            yphone.setText(""+fuphone.get(position));
            yproduct.setText(""+fuproductname.get(position));
            yqry.setText(""+fuqty.get(position));
            yrate.setText(""+furates.get(position));
            ydates.setText(""+fdatej.get(position));


            return video_view;
        }
    }
}
