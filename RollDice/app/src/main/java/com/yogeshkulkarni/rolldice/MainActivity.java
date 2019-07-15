package com.yogeshkulkarni.rolldice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText eT1;
    TextView tV1;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eT1 = findViewById(R.id.eT1);
        tV1 = findViewById(R.id.tV1);
        btn = findViewById(R.id.btn);
        tV1.setVisibility(View.GONE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                int nr = Integer.parseInt(eT1.getText().toString().trim());

                new ProcessDiceInBackground().execute(nr);

            }
        });

    }

    public class ProcessDiceInBackground extends AsyncTask<Integer, Integer , String>{


            ProgressDialog dialog ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax( Integer.parseInt(eT1.getText().toString().trim()));
            dialog.show();

        }



        @Override
        protected String doInBackground(Integer... params) {
            int ones =0 ,twos = 0 , threes =0 , fours =0,fives =0 ,sixes=0,rn;




            Random random = new Random();

            String results;
            double currenprogress = 0;
            double previousprogress =0;


            for(int i =0;i<params[0];i++)
            {

                currenprogress = (double) i/params[0];
                if((currenprogress-previousprogress) >=0.03)
                {

                    publishProgress(i);
                    previousprogress = currenprogress;

                }

                rn = random.nextInt(6) + 1;

                switch (rn)
                {
                    case 1:
                        ones++;
                        break;
                    case 2:
                        twos++;
                        break;
                    case 3:
                        threes++;
                        break;
                    case 4:
                        fours++;
                        break;
                    case 5:
                        fives++;
                        break;

                    default:sixes++;
                }

            }

            results = "Results: \n1:  " + ones + "\n2: " + twos + "\n3: " + threes + "\n4: " + fours + "\n5: " + fives + "\n6: " + sixes;





            return results;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);



            dialog.setProgress(values[0]);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();
            tV1.setText(s);
            tV1.setVisibility(View.VISIBLE);



            Toast.makeText(MainActivity.this,"Done",Toast.LENGTH_LONG).show();
        }
    }


}
