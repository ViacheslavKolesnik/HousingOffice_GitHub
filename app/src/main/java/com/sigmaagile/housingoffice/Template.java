package com.sigmaagile.housingoffice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Template extends AppCompatActivity {
    EditText cause;
    EditText otherProblem;
    String smsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        cause=(EditText)findViewById(R.id.editTextCause);
        otherProblem=(EditText)findViewById(R.id.editTextOther);
        Log.d("mylog","-----"+ Apartment.sendemail.toString()+"-------");
    }
    public void Next(View view){
        smsText+="Accident: \n";
        if(findViewById(R.id.checkBoxColdW).isEnabled()){
            smsText+="cold water\n";
        }
        if(findViewById(R.id.checkBoxHotW).isEnabled()){
            smsText+="hot water\n";
        }
        if(findViewById(R.id.checkBoxElectricity).isEnabled()){
            smsText+="electricity\n";
        }
        if(findViewById(R.id.checkBoxGas).isEnabled()){
            smsText+="gas\n";
        }
        if(findViewById(R.id.checkBoxHeating).isEnabled()){
            smsText+="heating\n";
        }
        if(findViewById(R.id.checkBoxOther).isEnabled()){
            smsText+=findViewById(R.id.editTextOther).toString()+"\n";
        }
        smsText+="Accident cause:\n"+findViewById(R.id.editTextCause).toString()+"\n";
        Intent intent = new Intent(this, Time.class);
        intent.putExtra("smstext",smsText);
        startActivity(intent);
    }

}
