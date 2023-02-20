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

public class cusbit extends AppCompatActivity {

    ListView cuslist;
    ArrayList<Integer> ccids = new ArrayList<>();
    ArrayList<String> ccusername = new ArrayList<>();
    ArrayList<String> ccuserphone = new ArrayList<>();
    ArrayList<String> ccactname = new ArrayList<>();
    ArrayList<String> ccacttype = new ArrayList<>();
    ArrayList<String> ccprodname = new ArrayList<>();
    ArrayList<String> ccpqty = new ArrayList<>();
    ArrayList<String> ccspec = new ArrayList<>();
    ArrayList<String> ccdatej = new ArrayList<>();

    int preLast_id;
    int Last;
    String id_val = "";
    int preLast;
    ProgressBar progressBar;

    cusproductqproductlist adapter;

    String cus_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusbit);

        progressBar=(ProgressBar)findViewById(R.id.progressBar12);


        cuslist=(ListView)findViewById(R.id.cusbitlist);

        ccids.clear();
        ccusername.clear();
        ccuserphone.clear();
        ccactname.clear();
        ccacttype.clear();
        ccprodname.clear();
        ccpqty.clear();
        ccspec.clear();
        ccdatej.clear();


        new cusproductbitsdata_load().execute();

        adapter = new cusproductqproductlist(getApplicationContext(), ccids, ccusername,ccuserphone,ccactname,ccacttype,ccprodname,ccpqty,ccspec,ccdatej);

        cuslist.setAdapter(adapter);


        Bundle b=getIntent().getExtras();

        if(b!=null)
        {

            cus_ids=b.getString("id");

            Toast.makeText(cusbit.this, ""+cus_ids, Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, ""+auction_id, Toast.LENGTH_SHORT).show();
        }
    }



    public class cusproductbitsdata_load extends AsyncTask<String, String, String> {

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
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/cusview.php");
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
            String dausername,dauserphone,daactname,daacttype,daactpname,daactqty,daactspcification,daactdates;

            //[{"id":"5","username":"karthi","userphone":"9659599191","auction_name":"sample","auction_type":"juice",
            // "pname":"water melon","qty":"150kg","specification":"sample karthi","datej":"30-05-2019"},

            if (s != null) {

                try {


                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    //preLast = preLast + jArray.length();
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        id1 = json_data.getInt("id");
                        dausername = json_data.getString("username");
                        dauserphone = json_data.getString("userphone");
                        daactname = json_data.getString("auction_name");
                        daacttype = json_data.getString("auction_type");
                        daactpname = json_data.getString("pname");
                        daactqty = json_data.getString("qty");
                        daactspcification = json_data.getString("specification");
                        daactdates = json_data.getString("datej");




                        id_val = "" + id1;

                        // System.out.println("category==" + category1);
                        ccids.add(id1);
                        ccusername.add(dausername);
                        ccuserphone.add(dauserphone);
                        ccactname.add(daactname);
                        ccacttype.add(daacttype);
                        ccprodname.add(daactpname);
                        ccpqty.add(daactqty);
                        ccspec.add(daactspcification);
                        ccdatej.add(daactdates);




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
    public class cusproductqproductlist extends BaseAdapter {

        TextView eusername,euserphone,eauctionname,eauctiontype,eprodname,eqty,espec,edatej;

        Button eupdate;


        Context mcontext;
        ArrayList<Integer> ccids = new ArrayList<>();
        ArrayList<String> ccusername = new ArrayList<>();
        ArrayList<String> ccuserphone = new ArrayList<>();
        ArrayList<String> ccactname = new ArrayList<>();
        ArrayList<String> ccacttype = new ArrayList<>();
        ArrayList<String> ccprodname = new ArrayList<>();
        ArrayList<String> ccpqty = new ArrayList<>();
        ArrayList<String> ccspec = new ArrayList<>();
        ArrayList<String> ccdatej = new ArrayList<>();



        public cusproductqproductlist(Context c,ArrayList<Integer> ccids, ArrayList<String> ccusername,ArrayList<String> ccuserphone,ArrayList<String> ccactname,ArrayList<String> ccacttype,ArrayList<String> ccprodname,ArrayList<String> ccpqty,ArrayList<String> ccspec,ArrayList<String> ccdatej){


            mcontext=c;
            this.ccids=ccids;
            this.ccusername=ccusername;
            this.ccuserphone=ccuserphone;
            this.ccactname=ccactname;
            this.ccacttype=ccacttype;
            this.ccprodname=ccprodname;
            this.ccpqty=ccpqty;
            this.ccspec=ccspec;
            this.ccdatej=ccdatej;



        }

        @Override
        public int getCount() {
            return ccids.size();
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
            video_view = inflater.inflate(R.layout.bitview, null);

            eusername=(TextView)video_view.findViewById(R.id.gusername01);
            euserphone=(TextView)video_view.findViewById(R.id.guserphone01);
            eauctionname=(TextView)video_view.findViewById(R.id.gauctionname01);
            eauctiontype=(TextView)video_view.findViewById(R.id.gauctiontype01);

            eprodname=(TextView)video_view.findViewById(R.id.gauctionprod01);
            eqty=(TextView)video_view.findViewById(R.id.gactqty01);
            espec=(TextView)video_view.findViewById(R.id.gspc01);
            edatej=(TextView)video_view.findViewById(R.id.gdatej01);


            eupdate=(Button) video_view.findViewById(R.id.btnupdate);

            eupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(cusbit.this,cusactbitupdate.class);

                    i.putExtra("ccids",""+ccids.get(position));
                    i.putExtra("ccusername",""+ccusername.get(position));
                    i.putExtra("ccuserphone",""+ccuserphone.get(position));
                    i.putExtra("ccactname",""+ccactname.get(position));
                    i.putExtra("ccacttype",""+ccacttype.get(position));
                    i.putExtra("ccprodname",""+ccprodname.get(position));
                    i.putExtra("ccpqty",""+ccpqty.get(position));
                    i.putExtra("ccspec",""+ccspec.get(position));
                    i.putExtra("cus_ids",""+cus_ids);


                    finish();
                    startActivity(i);

                }
            });





            eusername.setText(""+ccusername.get(position));
            euserphone.setText(""+ccuserphone.get(position));
            eauctionname.setText(""+ccactname.get(position));
            eauctiontype.setText(""+ccacttype.get(position));
            eprodname.setText(""+ccprodname.get(position));
            eqty.setText(""+ccpqty.get(position));
            espec.setText(""+ccspec.get(position));
            edatej.setText(""+ccdatej.get(position));


            return video_view;
        }
    }
}