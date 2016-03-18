package com.sigmaagile.housingoffice;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {
    HttpClient httpclient = new DefaultHttpClient();
    ResponseHandler response = new BasicResponseHandler();
    HttpGet httpget ;


    public static ArrayList<Account> list;
    public boolean isinlist;
    public boolean correctdata;
    public int ID;


    String line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        line="";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAdressee(View view){
        correctdata=false;
        isinlist=false;

        final String llogin=((EditText)findViewById(R.id.editText)).getText().toString();
        final String ppassword=((EditText)findViewById(R.id.editText2)).getText().toString();


        new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        httpget = new HttpGet("http://house-utilities-api.azurewebsites.net/api/login/getkey/log="+llogin+",pas="+ppassword);
                        Log.d("MyTag", "запрос отправлен");
                        line = httpclient.execute(httpget, response).toString();
                    }
                    catch (ClientProtocolException e) {
                        Log.d("MyTag", "ошибочка");
                    }
                    catch (IOException e) {
                        Log.d("MyTag", "запрос не отправлен");
                    }
            }
        }).start();
        if(line.equals("\"314\"")) {
            Intent intent = new Intent(this, Streets.class);
            //Log.d("my Logs",ID+"");
            //intent.putExtra("ID", ID);
            startActivity(intent);
            ((TextView) findViewById(R.id.textView2)).setText("");
        }
        else{
            ((TextView) findViewById(R.id.textView2)).setText("Incorrect login or password");
        }
    }
}

class Account{
    public String login;
    public String password;
    public int id;
    Account(String s, String p, int i){
        login=s;
        password=p;
        id=i;
    }
}