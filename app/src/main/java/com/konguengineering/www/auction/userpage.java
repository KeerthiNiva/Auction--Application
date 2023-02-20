package com.konguengineering.www.auction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class userpage extends AppCompatActivity {

    Button cusbits,cusauctfinal,cusfeed;

    String cusid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);


        cusbits=(Button)findViewById(R.id.cusbitauction);

        cusauctfinal=(Button)findViewById(R.id.cusbitfinal);

        cusfeed=(Button)findViewById(R.id.cusfeedback);




        Bundle b=getIntent().getExtras();

        if(b!=null){

            cusid=b.getString("id");

            Toast.makeText(this, ""+cusid, Toast.LENGTH_SHORT).show();

        }

        cusbits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(userpage.this,cusbit.class);

                a.putExtra("id",""+cusid);

                startActivity(a);
            }
        });



        cusauctfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent b = new Intent(userpage.this,userfinalact.class);

                b.putExtra("id",""+cusid);
                startActivity(b);


            }
        });

        cusfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent c = new Intent(userpage.this,cusfeeds.class);

                c.putExtra("id",""+cusid);
                
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
                        Intent myIntent = new Intent(userpage.this,
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
}
