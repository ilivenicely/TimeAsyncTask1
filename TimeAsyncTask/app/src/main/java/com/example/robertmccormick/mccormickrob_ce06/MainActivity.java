package com.example.robertmccormick.mccormickrob_ce06;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText minText ;
    private EditText secText ;

    private Button startbutton;
    private Button stopbutton;

    public  long time=0;
    public int mins =0;
    public int secs =0 ;
    private TimeAsyncTask timer;
    public  int minr;
    public int secr;
    public  boolean started = false;

    AlertDialog.Builder myalert ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minText = findViewById(R.id.mineidttext);
        secText = findViewById(R.id.seceidttext);
        startbutton = findViewById(R.id.startb);
        stopbutton = findViewById(R.id.stopb);

        startbutton.setEnabled(!started);
        stopbutton.setEnabled(started);

        minText.setText("0");
        secText.setText("0");
        myalert = new AlertDialog.Builder(this);
        timer = new TimeAsyncTask(this);
    }

    public void finishTimer()
    {
        minText.setText("0");
        secText.setText("0");
        myalert.setTitle("Timer Finished");
        myalert.setMessage("Timer Finshed");
        myalert.setNegativeButton("CLose",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
            }
        });
        AlertDialog dialog = myalert.create();
        dialog.show();
    }
    public void updateUI(long timeremain)
    {
        minr = (int) (timeremain) / 60;
        secr = (int) (timeremain) % 60;
        startbutton.setEnabled(!started);
        stopbutton.setEnabled(started);

        minText.setText(String.valueOf(minr));
        secText.setText(String.valueOf(secr));
    }


    public void stopCount(){
        started=false;
        startbutton.setEnabled(!started);
        stopbutton.setEnabled(started);

        myalert.setTitle("Timer Stoped");
        myalert.setMessage("Time Elapsed :\n minute(s)"+minr+" second(s)"+secr);
        myalert.setNegativeButton("CLose",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
            }
        });
        AlertDialog dialog = myalert.create();
        dialog.show();
        mins=0;
        secs=0;
        minText.setText("0");
        secText.setText("0");
    }
    public void doWork(View view)
    {
        String minst = minText.getText().toString();
        String secst = secText.getText().toString();
        if(!minst.equals(""))
        {
            mins = Integer.parseInt(minst);
        }
        else{mins=0;}
        if(!secst.equals(""))
        {
            secs = Integer.parseInt(secst);
        }
        else{secs=0;}
        if (mins<=0 && secs<=0)
        {
            Toast.makeText(this, "Error : please enter valid time ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            time = (mins*60)+(secs);
            started = true;
            if(timer.isCancelled())
                timer = new TimeAsyncTask(this);
            timer.execute(time);
        }
    }

    public void stop(View view) {
        timer.cancel(true);
    }
}


