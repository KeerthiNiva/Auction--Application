package com.konguengineering.www.auction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminpage extends AppCompatActivity {


    Button auction_create,auction_fix,auction_final,customer,seller,feednackview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);

        auction_create=(Button)findViewById(R.id.acreate);

        auction_fix=(Button)findViewById(R.id.adates);

        auction_final=(Button)findViewById(R.id.auctfinal);

        customer=(Button)findViewById(R.id.customers);

        seller=(Button)findViewById(R.id.sellers);

        feednackview=(Button)findViewById(R.id.feedbackview);






        auction_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a= new Intent(adminpage.this,auction_create.class);

                startActivity(a);

            }
        });


        auction_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent fix = new Intent(adminpage.this,auction_fix_date.class);

                startActivity(fix);
            }
        });


        auction_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent finals = new Intent(adminpage.this,auction_final.class);

                startActivity(finals);
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent c = new Intent(adminpage.this,customerview.class);

                startActivity(c);

            }
        });


        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent s = new Intent(adminpage.this,sellersview.class);
                startActivity(s);
            }
        });

        feednackview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent t = new Intent(adminpage.this,feedbackview.class);

                startActivity(t);

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
                        Intent myIntent = new Intent(adminpage.this,
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
