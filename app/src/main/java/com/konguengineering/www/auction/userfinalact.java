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

public class userfinalact extends AppCompatActivity {

    ListView customerlist;
    ArrayList<Integer> fid = new ArrayList<>();
    ArrayList<String> fusrename = new ArrayList<>();
    ArrayList<String> fuserphne = new ArrayList<>();
    ArrayList<String> fproduct = new ArrayList<>();
    ArrayList<String> fqty = new ArrayList<>();
    ArrayList<String> frates = new ArrayList<>();
    ArrayList<String> fdatej = new ArrayList<>();

    int preLast_id;
    int Last;
    String id_val = "";
    int preLast;
    ProgressBar progressBar;

    cusproductviewadapter adapter;

    String staff_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userfinalact);

        customerlist = (ListView) findViewById(R.id.cusproductsellfinal);
        progressBar = (ProgressBar) findViewById(R.id.progressBar14);

        fid.clear();
        fusrename.clear();
        fuserphne.clear();
        fproduct.clear();
        fqty.clear();
        frates.clear();
        fdatej.clear();

        Bundle b=getIntent().getExtras();

        if(b!=null)
        {

            staff_id=b.getString("id");

            Toast.makeText(userfinalact.this, ""+staff_id, Toast.LENGTH_SHORT).show();


        }


        new finalproductviewdata_load().execute();

        adapter = new cusproductviewadapter(getApplicationContext(),fid,fusrename,fuserphne,fproduct,fqty,frates,fdatej);

        customerlist.setAdapter(adapter);



    }

    public class finalproductviewdata_load extends AsyncTask<String, String, String> {

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
            nameValuePairs.add(new BasicNameValuePair("customerid", "" + staff_id));


            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://appsminekarthi.000webhostapp.com/auction/final_product_view.php");
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
            String hcusid,husernamess,huserphone,hproduct,hspec,hqty,hrate,hdeltype,hdatej;

            //"id":"2","cusid":"4","username":"karthi","user_phone":"9659599191","product":"water melon",
            // "specification":"sample karthi","qty":"150kg","rate":"260000","deltype":"1","datej":"30-05-2019"}

            if (s != null) {

                try {


                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    //preLast = preLast + jArray.length();
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        id1 = json_data.getInt("id");
                        hcusid = json_data.getString("cusid");
                        husernamess = json_data.getString("username");
                        huserphone = json_data.getString("user_phone");
                        hproduct = json_data.getString("product");
                        hspec = json_data.getString("specification");
                        hqty = json_data.getString("qty");
                        hrate = json_data.getString("rate");
                        hdeltype = json_data.getString("deltype");
                        hdatej = json_data.getString("datej");



                        id_val = "" + id1;

                        // System.out.println("category==" + category1);
                        fid.add(id1);
                        fusrename.add(husernamess);
                        fuserphne.add(huserphone);
                        fproduct.add(hproduct);
                        fqty.add(hqty);
                        frates.add(hrate);
                        fdatej.add(hdatej);

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
    public class cusproductviewadapter extends BaseAdapter {

        TextView iusername,iuserphone,iproduct,iqty,irate,idatej;




        Context mcontext;
        ArrayList<Integer> fid = new ArrayList<>();
        ArrayList<String> fusrename = new ArrayList<>();
        ArrayList<String> fuserphne = new ArrayList<>();
        ArrayList<String> fproduct = new ArrayList<>();
        ArrayList<String> fqty = new ArrayList<>();
        ArrayList<String> frates = new ArrayList<>();
        ArrayList<String> fdatej = new ArrayList<>();



        public cusproductviewadapter(Context c,ArrayList<Integer> fid, ArrayList<String> fusrename,ArrayList<String> fuserphne,ArrayList<String> fproduct,ArrayList<String> fqty,ArrayList<String> frates,ArrayList<String> fdatej){


            mcontext=c;
            this.fid=fid;
            this.fusrename=fusrename;
            this.fuserphne=fuserphne;
            this.fproduct=fproduct;
            this.fqty=fqty;
            this.frates=frates;
            this.fdatej=fdatej;


        }

        @Override
        public int getCount() {
            return fid.size();
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
            video_view = inflater.inflate(R.layout.cusfinal, null);

            iusername=(TextView)video_view.findViewById(R.id.gusername01);
            iuserphone=(TextView)video_view.findViewById(R.id.guserphone01);
            iproduct=(TextView)video_view.findViewById(R.id.gauctionname01);
            iqty=(TextView)video_view.findViewById(R.id.gauctiontype01);
            irate=(TextView) video_view.findViewById(R.id.gauctionprod01);
            idatej=(TextView) video_view.findViewById(R.id.gauctiondateja01);

          /*  anupdate.setOnClickListener(new View.OnClickListener() {
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
            */

            iusername.setText(""+fusrename.get(position));
            iuserphone.setText(""+fuserphne.get(position));
            iproduct.setText(""+fproduct.get(position));
            iqty.setText(""+fqty.get(position));
            irate.setText(""+frates.get(position));
            idatej.setText(""+fdatej.get(position));


            return video_view;
        }
    }
}